package dev.mslalith.imagecachingandroid.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val id: Long,
    @SerialName("largeImageURL")
    val imageURL: String,
    val pageURL: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("user")
    val userName: String,
    @SerialName("userImageURL")
    val userImageURL: String
)
