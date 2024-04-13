package dev.mslalith.imagecachingandroid.data.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<ImageResponse>
)