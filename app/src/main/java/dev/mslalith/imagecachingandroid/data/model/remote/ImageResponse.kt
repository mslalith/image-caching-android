package dev.mslalith.imagecachingandroid.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    @SerialName("photographer")
    val photographerName: String,
    @SerialName("photographer_url")
    val photographerUrl: String,
    @SerialName("photographer_id")
    val photographerId: Int,
    @SerialName("src")
    val imageUrlResponse: ImageUrlResponse,
    val liked: Boolean,
    val alt: String
)
