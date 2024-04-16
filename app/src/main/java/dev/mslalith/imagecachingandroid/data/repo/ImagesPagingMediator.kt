package dev.mslalith.imagecachingandroid.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.mslalith.imagecachingandroid.data.api.ImagesApi
import dev.mslalith.imagecachingandroid.data.database.AppDatabase
import dev.mslalith.imagecachingandroid.data.dto.toImagesResponseEntity
import dev.mslalith.imagecachingandroid.data.model.local.ImagesResponseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class ImagesPagingMediator(
    private val appDatabase: AppDatabase,
    private val imagesApi: ImagesApi
) : RemoteMediator<Int, ImagesResponseEntity>() {

    companion object {
        private const val INITIAL_PAGE = 1
    }

    private var searchQuery = ""

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImagesResponseEntity>
    ): MediatorResult {
        return try {
            val reachedEndResult = MediatorResult.Success(endOfPaginationReached = true)
            val nextPage = when (loadType) {
                LoadType.REFRESH -> INITIAL_PAGE
                LoadType.PREPEND -> return reachedEndResult
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return reachedEndResult
                    lastItem.page + 1
                }
            }

            val response = withContext(Dispatchers.IO) {
                imagesApi.fetchImages(
                    query = searchQuery,
                    page = nextPage,
                    perPage = state.config.pageSize
                )
            }
            response.fold(
                onSuccess = { imagesResponse ->
                    withContext(Dispatchers.IO) {
                        appDatabase.withTransaction {
                            if (loadType == LoadType.REFRESH) {
                                appDatabase.imagesDao().clearAll()
                            }

                            val images = imagesResponse.toImagesResponseEntity()
                            appDatabase.imagesDao().insertAll(images = listOf(images))
                        }
                    }

                    MediatorResult.Success(endOfPaginationReached = imagesResponse.photos.isEmpty())
                },
                onFailure = {
                    MediatorResult.Error(it)
                }
            )
        } catch (ex: Exception) {
            MediatorResult.Error(ex)
        }
    }
}
