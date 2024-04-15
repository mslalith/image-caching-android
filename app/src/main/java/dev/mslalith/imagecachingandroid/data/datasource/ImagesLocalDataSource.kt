package dev.mslalith.imagecachingandroid.data.datasource

import dev.mslalith.imagecachingandroid.data.database.ImagesDao
import dev.mslalith.imagecachingandroid.data.model.local.ImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImagesLocalDataSource @Inject constructor(
    private val imagesDao: ImagesDao
) {

    val imagesFlow: Flow<List<ImageEntity>> = imagesDao.getAllImages()

    fun insertAll(images: List<ImageEntity>) {
        imagesDao.insertAll(images)
    }
}
