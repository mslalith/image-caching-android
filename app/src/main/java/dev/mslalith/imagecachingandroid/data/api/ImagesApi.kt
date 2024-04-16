package dev.mslalith.imagecachingandroid.data.api

import dev.mslalith.imagecachingandroid.BuildConfig
import dev.mslalith.imagecachingandroid.data.model.remote.ImagesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import javax.inject.Inject

interface ImagesApi {
    suspend fun fetchImages(
        query: String,
        page: Int,
        perPage: Int
    ): Result<ImagesResponse>
}

class ImagesApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : ImagesApi {

    override suspend fun fetchImages(
        query: String,
        page: Int,
        perPage: Int
    ): Result<ImagesResponse> = try {
        val response = httpClient.get("https://api.pexels.com/v1/curated/") {
            header("Authorization", BuildConfig.PEXELS_API_KEY)
            parameter("page", page)
            parameter("per_page", perPage)
        }.body<ImagesResponse>()
        Result.success(response)
    } catch (ex: Exception) {
        ex.printStackTrace()
        Result.failure(exception = ex)
    }
}
