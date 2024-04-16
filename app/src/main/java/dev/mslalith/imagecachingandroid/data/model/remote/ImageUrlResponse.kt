package dev.mslalith.imagecachingandroid.data.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class ImageUrlResponse(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
