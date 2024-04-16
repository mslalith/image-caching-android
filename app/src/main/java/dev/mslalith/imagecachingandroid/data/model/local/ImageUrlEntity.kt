package dev.mslalith.imagecachingandroid.data.model.local

import kotlinx.serialization.Serializable

@Serializable
data class ImageUrlEntity(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
