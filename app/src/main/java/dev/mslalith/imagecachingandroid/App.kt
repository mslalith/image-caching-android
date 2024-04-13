package dev.mslalith.imagecachingandroid

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dev.mslalith.imagecachingandroid.imageloader.ImageLoader

@HiltAndroidApp
class App : Application() {

    // as this is the single source for caching
    // there needs to be a single instance
    val appImageLoader: ImageLoader by lazy { ImageLoader(context = this) }
}

val Context.imageLoader: ImageLoader
    get() = (applicationContext as App).appImageLoader
