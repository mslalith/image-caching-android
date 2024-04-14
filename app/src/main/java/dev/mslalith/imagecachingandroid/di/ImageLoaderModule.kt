package dev.mslalith.imagecachingandroid.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.mslalith.imagecachingandroid.imageloader.ImageLoader
import dev.mslalith.imagecachingandroid.imageloader.cache.disk.DiskCache
import dev.mslalith.imagecachingandroid.imageloader.cache.disk.FileSystemDiskCache
import dev.mslalith.imagecachingandroid.imageloader.cache.memory.InMemoryCache
import dev.mslalith.imagecachingandroid.imageloader.cache.memory.LruInMemoryCache
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderProvidesModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        inMemoryCache: InMemoryCache,
        diskCache: DiskCache
    ): ImageLoader {
        return ImageLoader(
            context = context,
            inMemoryCache = inMemoryCache,
            diskCache = diskCache
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageLoaderBindsModule {

    @Binds
    @Singleton
    abstract fun bindLruInMemoryCache(
        lruInMemoryCache: LruInMemoryCache
    ): InMemoryCache

    @Binds
    @Singleton
    abstract fun bindFileSystemDiskCache(
        fileSystemDiskCache: FileSystemDiskCache
    ): DiskCache
}
