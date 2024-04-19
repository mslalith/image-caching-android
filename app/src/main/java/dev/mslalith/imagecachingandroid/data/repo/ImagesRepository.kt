package dev.mslalith.imagecachingandroid.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.data.dto.toImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    private val imagesPagingSource: ImagesPagingSource
) {

    private val pager by lazy {
        Pager(
            config = PagingConfig(
                pageSize = ImagesPagingSource.PER_PAGE,
                initialLoadSize = ImagesPagingSource.PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { imagesPagingSource }
        )
    }

    val imagesFlow: Flow<PagingData<Image>> = pager.flow.map { pagingData ->
        pagingData.map { it.toImage() }
    }
}
