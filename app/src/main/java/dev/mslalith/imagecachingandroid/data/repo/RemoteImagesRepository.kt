package dev.mslalith.imagecachingandroid.data.repo

import dev.mslalith.imagecachingandroid.BuildConfig
import dev.mslalith.imagecachingandroid.data.model.remote.ImagesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class RemoteImagesRepository @Inject constructor(
    private val httpClient: HttpClient
) : ImagesRepository {

    companion object {
        private const val PER_PAGE = 20
    }

    override suspend fun searchImages(
        query: String,
        page: Int
    ): ImagesResponse? {
        return try {
            httpClient.get("https://pixabay.com/api/") {
                parameter("key", BuildConfig.PIXABAY_API_KEY)
                parameter("q", query)
                parameter("image_type", "photo")
                parameter("page", page)
                parameter("per_page", PER_PAGE)
            }.body()
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}