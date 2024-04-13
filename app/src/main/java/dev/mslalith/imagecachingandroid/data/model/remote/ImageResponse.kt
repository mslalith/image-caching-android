package dev.mslalith.imagecachingandroid.data.model.remote

import dev.mslalith.imagecachingandroid.data.dto.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val id: Long,
    @SerialName("largeImageURL")
    val imageURL: String
)

fun ImageResponse.toImage() = Image(
    id = id,
    imageURL = imageURL
)
