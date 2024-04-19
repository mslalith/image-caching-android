package dev.mslalith.imagecachingandroid.imageloader.cache.memory

import android.graphics.Bitmap
import androidx.collection.LruCache
import javax.inject.Inject

class LruInMemoryCache @Inject constructor() : InMemoryCache {

    private val cacheMap by lazy {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8

        object : LruCache<String, Bitmap>(maxSize = cacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int = value.byteCount / 1024
        }
    }

    override fun get(key: String): Bitmap? = cacheMap[key]

    override fun put(key: String, bitmap: Bitmap) {
        cacheMap.put(key, bitmap)
    }
}
