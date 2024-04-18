package dev.mslalith.imagecachingandroid.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import dev.mslalith.imagecachingandroid.imageloader.cache.disk.DiskCache
import dev.mslalith.imagecachingandroid.imageloader.cache.memory.InMemoryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import java.net.URL
import java.security.MessageDigest

class ImageLoader(
    private val context: Context,
    private val inMemoryCache: InMemoryCache,
    private val diskCache: DiskCache
) {
    private val messageDigest = MessageDigest.getInstance("MD5")
    private val imageDownloadLock = Semaphore(permits = 10)

    fun getCachedBitmap(imageRequest: ImageRequest): Bitmap? {
        val key = imageRequest.key()

        // check in-memory
        var bitmap = inMemoryCache.get(key)
        if (bitmap != null) return bitmap

        // check disk
        bitmap = diskCache.get(key)
        if (bitmap != null) {
            inMemoryCache.put(key, bitmap)
            return bitmap
        }

        return null
    }

    suspend fun execute(imageRequest: ImageRequest): Bitmap {
        return when (imageRequest) {
            is ImageRequest.Network -> imageRequest.toBitmap() ?: kotlin.run {
                ImageRequest.Drawable(id = imageRequest.errorId).toBitmap()
            }
            is ImageRequest.Drawable -> imageRequest.toBitmap()
        }.also {
            val key = imageRequest.key()
            inMemoryCache.put(key, it)
            diskCache.put(key, it)
        }
    }

    private fun ImageRequest.key(): String = when (this) {
        is ImageRequest.Network -> url.md5Hash()
        is ImageRequest.Drawable -> id.toString()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun String.md5Hash(): String {
        val digest = messageDigest.digest(this.toByteArray())
        return digest.toHexString()
    }

    private suspend fun ImageRequest.Network.toBitmap(): Bitmap? = withContext(Dispatchers.IO) {
        try {
            imageDownloadLock.withPermit {
                URL(url).openStream().use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    private suspend fun ImageRequest.Drawable.toBitmap(): Bitmap = withContext(Dispatchers.IO) {
        val drawable = ContextCompat.getDrawable(context, id) ?: ColorDrawable(Color.TRANSPARENT)
        drawable.toBitmap()
    }
}
