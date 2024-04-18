package dev.mslalith.imagecachingandroid.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.flatMap
import androidx.paging.map
import dev.mslalith.imagecachingandroid.data.api.ImagesApi
import dev.mslalith.imagecachingandroid.data.database.AppDatabase
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.data.dto.toImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.roundToInt

@OptIn(ExperimentalPagingApi::class)
class ImagesRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val imagesApi: ImagesApi
) {

    companion object {
        private const val PER_PAGE = 20
    }

    private val imagesPagingMediator by lazy {
        ImagesPagingMediator(
            appDatabase = appDatabase,
            imagesApi = imagesApi
        )
    }

    private val pager by lazy {
        Pager(
            config = PagingConfig(
                pageSize = PER_PAGE,
//                prefetchDistance = PER_PAGE / 2,
                initialLoadSize = PER_PAGE,
                enablePlaceholders = true
            ),
//            remoteMediator = imagesPagingMediator,
//            pagingSourceFactory = { appDatabase.imagesDao().pagingSource() }
            pagingSourceFactory = {
                ImagesPagingSource(imagesApi)
            }
        )
    }

    val imagesFlow: Flow<PagingData<Image>> = pager.flow.map { pagingData ->
//        pagingData.flatMap { imagesResponseEntity ->
//            imagesResponseEntity.photos.map { it.toImage() }
//        }
//        pagingData.flatMap { imagesResponse ->
//            imagesResponse.photos.map { it.toImage() }
//        }
        pagingData.map { it.toImage() }
    }

    fun updateSearchQuery(
        query: String
    ) {
        imagesPagingMediator.updateSearchQuery(query = query)
    }
}
