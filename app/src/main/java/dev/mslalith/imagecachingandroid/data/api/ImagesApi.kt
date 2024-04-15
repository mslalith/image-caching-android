package dev.mslalith.imagecachingandroid.data.api

import dev.mslalith.imagecachingandroid.BuildConfig
import dev.mslalith.imagecachingandroid.data.model.remote.ImagesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

interface ImagesApi {
    suspend fun fetchImages(
        query: String,
        page: Int
    ): Result<ImagesResponse?>
}

class ImagesApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : ImagesApi {

    companion object {
        private const val PER_PAGE = 20
    }

    override suspend fun fetchImages(
        query: String,
        page: Int
    ): Result<ImagesResponse?> = try {
        val response = httpClient.get("https://pixabay.com/api/") {
            parameter("key", BuildConfig.PIXABAY_API_KEY)
            parameter("q", query)
            parameter("image_type", "photo")
            parameter("page", page)
            parameter("per_page", PER_PAGE)
        }.body<ImagesResponse>()
        Result.success(response)
    } catch (ex: Exception) {
        ex.printStackTrace()
        Result.failure(exception = ex)
    }
}
