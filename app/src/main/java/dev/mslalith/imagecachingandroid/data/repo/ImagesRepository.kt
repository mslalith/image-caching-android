package dev.mslalith.imagecachingandroid.data.repo

import dev.mslalith.imagecachingandroid.data.model.remote.ImagesResponse

interface ImagesRepository {
    suspend fun searchImages(
        query: String,
        page: Int
    ): ImagesResponse?
}
