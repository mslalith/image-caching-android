package dev.mslalith.imagecachingandroid.data.repo

import dev.mslalith.imagecachingandroid.data.dto.Image
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    val imagesFlow: Flow<List<Image>>

    suspend fun fetchImages(
        query: String,
        page: Int
    )
}
