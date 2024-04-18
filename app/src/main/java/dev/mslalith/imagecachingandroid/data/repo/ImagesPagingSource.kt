package dev.mslalith.imagecachingandroid.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.mslalith.imagecachingandroid.data.api.ImagesApi
import dev.mslalith.imagecachingandroid.data.model.remote.ImageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesPagingSource @Inject constructor(
     private val imagesApi: ImagesApi
) : PagingSource<Int, ImageResponse>() {

    companion object {
        private const val INITIAL_PAGE = 1
        const val PER_PAGE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, ImageResponse>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponse> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val result = withContext(Dispatchers.IO) {
                println("#test: calling api for page: $page")
                imagesApi.fetchImages(query = "", page = page, perPage = PER_PAGE)
            }

            result.fold(
                onSuccess = {
                    println("#test: images loaded for page: $page: ${it.photos.size}")
                    val imagesResponse = it.copy(
                        photos = it.photos.map { imageResponse ->
                            imageResponse.copy(id = imageResponse.id + System.currentTimeMillis())
                        }
                    )
                    LoadResult.Page(
                        data = imagesResponse.photos,
                        prevKey = if (page == INITIAL_PAGE) null else page - 1,
                        nextKey = if (imagesResponse.photos.isEmpty()) null else page + 1
                    )
                },
                onFailure = {
                    LoadResult.Error(it)
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
