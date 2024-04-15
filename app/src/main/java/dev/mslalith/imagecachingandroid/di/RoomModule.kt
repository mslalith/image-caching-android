package dev.mslalith.imagecachingandroid.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mslalith.imagecachingandroid.data.database.AppDatabase
import dev.mslalith.imagecachingandroid.data.database.ImagesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context = context.applicationContext,
        klass = AppDatabase::class.java,
        name = "image_caching"
    ).build()

    @Provides
    @Singleton
    fun provideImagesDao(
        appDatabase: AppDatabase
    ): ImagesDao = appDatabase.imagesDao()
}
