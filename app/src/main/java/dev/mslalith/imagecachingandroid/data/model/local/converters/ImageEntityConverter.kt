package dev.mslalith.imagecachingandroid.data.model.local.converters

import androidx.room.TypeConverter
import dev.mslalith.imagecachingandroid.data.model.local.ImageEntity
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class ImageEntityConverter {

    @TypeConverter
    fun fromImageEntity(entities: List<ImageEntity>): String {
        return Json.encodeToString(ListSerializer(ImageEntity.serializer()), entities)
    }

    @TypeConverter
    fun fromString(json: String): List<ImageEntity> {
        return Json.decodeFromString(json)
    }
}
