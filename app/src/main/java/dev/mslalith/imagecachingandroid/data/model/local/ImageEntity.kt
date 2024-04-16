package dev.mslalith.imagecachingandroid.data.model.local

import kotlinx.serialization.Serializable

@Serializable
data class ImageEntity(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographerName: String,
    val photographerUrl: String,
    val photographerId: Int,
    val imageUrl: ImageUrlEntity,
    val liked: Boolean,
    val alt: String
)
