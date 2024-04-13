package dev.mslalith.imagecachingandroid.imageloader

import androidx.annotation.DrawableRes

sealed interface ImageRequest {

    data class Network(
        val url: String,
        @DrawableRes val errorId: Int
    ) : ImageRequest

    data class Drawable(
        @DrawableRes val id: Int
    ) : ImageRequest
}
