package dev.mslalith.imagecachingandroid.data.dto

import dev.mslalith.imagecachingandroid.data.model.local.ImageEntity
import dev.mslalith.imagecachingandroid.data.model.remote.ImageResponse

data class Image(
    val id: Long,
    val imageURL: String,
    val views: Int,
    val downloads: Int,
    val collections: Int,
    val likes: Int,
    val comments: Int,
    val userId: Int,
    val userName: String,
    val userImageURL: String
)

fun ImageResponse.toImage() = Image(
    id = id,
    imageURL = imageURL,
    views = views,
    downloads = downloads,
    collections = collections,
    likes = likes,
    comments = comments,
    userId = userId,
    userName = userName,
    userImageURL = userImageURL
)

fun ImageResponse.toImageEntity() = ImageEntity(
    id = id,
    imageURL = imageURL,
    views = views,
    downloads = downloads,
    collections = collections,
    likes = likes,
    comments = comments,
    userId = userId,
    userName = userName,
    userImageURL = userImageURL
)

fun ImageEntity.toImage() = Image(
    id = id,
    imageURL = imageURL,
    views = views,
    downloads = downloads,
    collections = collections,
    likes = likes,
    comments = comments,
    userId = userId,
    userName = userName,
    userImageURL = userImageURL
)
