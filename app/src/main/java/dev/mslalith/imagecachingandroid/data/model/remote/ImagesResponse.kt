package dev.mslalith.imagecachingandroid.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    val photos: List<ImageResponse>,
    @SerialName("next_page")
    val nextPage: String
)