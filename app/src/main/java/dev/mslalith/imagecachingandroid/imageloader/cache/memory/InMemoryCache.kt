package dev.mslalith.imagecachingandroid.imageloader.cache.memory

import android.graphics.Bitmap

interface InMemoryCache {
    fun get(key: String): Bitmap?
    fun put(key: String, bitmap: Bitmap)
}
