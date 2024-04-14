package dev.mslalith.imagecachingandroid.imageloader.cache.disk

import android.graphics.Bitmap

interface DiskCache {
    fun get(key: String): Bitmap?
    suspend fun put(key: String, bitmap: Bitmap)
}
