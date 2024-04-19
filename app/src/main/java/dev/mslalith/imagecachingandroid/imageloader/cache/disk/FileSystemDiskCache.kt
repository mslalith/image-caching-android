package dev.mslalith.imagecachingandroid.imageloader.cache.disk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileSystemDiskCache @Inject constructor(
    @ApplicationContext private val context: Context
) : DiskCache {

    private val cacheDir = File(context.cacheDir, "images")
    private val writeLock = Semaphore(permits = 60)

    init {
        if (!cacheDir.exists()) cacheDir.mkdirs()
    }

    override fun get(key: String): Bitmap? {
        val file = File(cacheDir, key)
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else null
    }

    override suspend fun put(key: String, bitmap: Bitmap) {
        writeLock.withPermit {
            val file = File(cacheDir, key)
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, it)
            }
        }
    }
}
