package dev.mslalith.imagecachingandroid.imageloader.cache.memory

import android.graphics.Bitmap
import androidx.collection.LruCache

class LruInMemoryCache : InMemoryCache {

    // TODO IF TIME PERMITS: configure size from available memory
    private val cacheMap = LruCache<String, Bitmap>(maxSize = 200)

    override fun get(key: String): Bitmap? = cacheMap[key]

    override fun put(key: String, bitmap: Bitmap) {
        cacheMap.put(key, bitmap)
    }
}
