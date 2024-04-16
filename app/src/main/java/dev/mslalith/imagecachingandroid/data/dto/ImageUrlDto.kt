package dev.mslalith.imagecachingandroid.data.dto

import dev.mslalith.imagecachingandroid.data.model.local.ImageUrlEntity
import dev.mslalith.imagecachingandroid.data.model.remote.ImageUrlResponse

fun ImageUrlResponse.toImageUrlEntity() = ImageUrlEntity(
    original = original,
    large2x = large2x,
    large = large,
    medium = medium,
    small = small,
    portrait = portrait,
    landscape = landscape,
    tiny = tiny
)
