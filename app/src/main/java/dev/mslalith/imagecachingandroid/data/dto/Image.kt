package dev.mslalith.imagecachingandroid.data.dto

import dev.mslalith.imagecachingandroid.data.model.local.ImageEntity

data class Image(
    val id: Long,
    val width: Int,
    val height: Int,
    val photographerName: String,
    val photographerUrl: String,
    val photographerId: Int,
    val imageUrl: String,
    val liked: Boolean,
    val alt: String
)

fun ImageEntity.toImage() = Image(
    id = id,
    width = width,
    height = height,
    photographerName = photographerName,
    photographerUrl = photographerUrl,
    photographerId = photographerId,
    imageUrl = imageUrl.large,
    liked = liked,
    alt = alt
)
