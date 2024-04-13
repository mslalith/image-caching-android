package dev.mslalith.imagecachingandroid.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.collection.LruCache
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import java.security.MessageDigest

class ImageLoader(
    private val context: Context
) {
    // TODO IF TIME PERMITS: configure size from available memory
    private val inMemoryCacheMap = LruCache<String, Bitmap>(maxSize = 20)
    private val messageDigest = MessageDigest.getInstance("MD5")

    fun getCachedBitmap(imageRequest: ImageRequest): Bitmap? = inMemoryCacheMap[imageRequest.key()]

    suspend fun execute(imageRequest: ImageRequest): Bitmap {
        return when (imageRequest) {
            is ImageRequest.Network -> imageRequest.toBitmap() ?: kotlin.run {
                ImageRequest.Drawable(id = imageRequest.errorId).toBitmap()
            }
            is ImageRequest.Drawable -> imageRequest.toBitmap()
        }.also {
            inMemoryCacheMap.put(imageRequest.key(), it)
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
            URL(url).openStream().use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    private suspend fun ImageRequest.Drawable.toBitmap(): Bitmap = withContext(Dispatchers.IO) {
        val drawable = ContextCompat.getDrawable(context, id) ?: ColorDrawable(Color.TRANSPARENT)
        drawable.toBitmap()
    }
}
