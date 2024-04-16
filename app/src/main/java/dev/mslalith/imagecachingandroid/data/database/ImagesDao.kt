package dev.mslalith.imagecachingandroid.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mslalith.imagecachingandroid.data.model.local.ImagesResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    @Query("SELECT * FROM images_response")
    fun getAllImages(): Flow<List<ImagesResponseEntity>>

    @Query("SELECT * FROM images_response")
    fun pagingSource(): PagingSource<Int, ImagesResponseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(images: List<ImagesResponseEntity>)

    @Query("DELETE FROM images_response")
    fun clearAll()
}
