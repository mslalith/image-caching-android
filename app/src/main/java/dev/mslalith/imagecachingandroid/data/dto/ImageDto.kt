package dev.mslalith.imagecachingandroid.data.dto

import dev.mslalith.imagecachingandroid.data.model.local.ImageEntity
import dev.mslalith.imagecachingandroid.data.model.remote.ImageResponse

fun ImageResponse.toImageEntity() = ImageEntity(
    id = id,
    width = width,
    height = height,
    url = url,
    photographerName = photographerName,
    photographerUrl = photographerUrl,
    photographerId = photographerId,
    imageUrl = imageUrlResponse.toImageUrlEntity(),
    liked = liked,
    alt = alt
)
