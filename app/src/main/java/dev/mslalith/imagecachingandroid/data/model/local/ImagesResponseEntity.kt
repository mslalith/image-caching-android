package dev.mslalith.imagecachingandroid.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_response")
data class ImagesResponseEntity(
    @PrimaryKey
    val page: Int,
    val perPage: Int,
    val photos: List<ImageEntity>,
    val nextPage: String
)
