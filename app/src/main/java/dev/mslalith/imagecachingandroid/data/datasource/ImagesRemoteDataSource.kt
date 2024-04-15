package dev.mslalith.imagecachingandroid.data.datasource

import dev.mslalith.imagecachingandroid.data.api.ImagesApi
import dev.mslalith.imagecachingandroid.data.model.remote.ImagesResponse
import javax.inject.Inject

class ImagesRemoteDataSource @Inject constructor(
    private val imagesApi: ImagesApi
) {
    suspend fun fetchImages(
        query: String,
        page: Int
    ): Result<ImagesResponse?> = imagesApi.fetchImages(
        query = query,
        page = page
    )
}
