package dev.mslalith.imagecachingandroid.data.repo

import dev.mslalith.imagecachingandroid.data.datasource.ImagesLocalDataSource
import dev.mslalith.imagecachingandroid.data.datasource.ImagesRemoteDataSource
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.data.dto.toImage
import dev.mslalith.imagecachingandroid.data.dto.toImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) : ImagesRepository {

    override val imagesFlow: Flow<List<Image>> = imagesLocalDataSource.imagesFlow.map { images ->
        images.map { it.toImage() }
    }

    override suspend fun fetchImages(
        query: String,
        page: Int
    ) {
        val response = imagesRemoteDataSource.fetchImages(
            query = query,
            page = page
        ).getOrNull()

        if (response != null) {
            val images = response.hits.map { it.toImageEntity() }
            imagesLocalDataSource.insertAll(images = images)
        }
    }
}
