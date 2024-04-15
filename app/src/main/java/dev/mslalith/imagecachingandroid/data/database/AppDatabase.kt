package dev.mslalith.imagecachingandroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.mslalith.imagecachingandroid.data.model.local.ImageEntity

@Database(
    entities = [ImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}
