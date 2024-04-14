package dev.mslalith.imagecachingandroid

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dev.mslalith.imagecachingandroid.imageloader.ImageLoader
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appImageLoader: dagger.Lazy<ImageLoader>
}

val Context.imageLoader: ImageLoader
    get() = (applicationContext as App).appImageLoader.get()
