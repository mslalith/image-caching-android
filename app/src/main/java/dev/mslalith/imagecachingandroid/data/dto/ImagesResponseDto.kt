package dev.mslalith.imagecachingandroid.data.dto

import dev.mslalith.imagecachingandroid.data.model.local.ImagesResponseEntity
import dev.mslalith.imagecachingandroid.data.model.remote.ImagesResponse

fun ImagesResponse.toImagesResponseEntity() = ImagesResponseEntity(
    page = page,
    perPage = perPage,
    photos = photos.map { it.toImageEntity() },
    nextPage = nextPage
)
