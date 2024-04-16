package dev.mslalith.imagecachingandroid.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mslalith.imagecachingandroid.data.model.local.ImagesResponseEntity
import dev.mslalith.imagecachingandroid.data.model.local.converters.ImageEntityConverter

@Database(
    entities = [
        ImagesResponseEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ImageEntityConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}
