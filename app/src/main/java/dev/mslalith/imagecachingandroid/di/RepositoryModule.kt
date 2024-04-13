package dev.mslalith.imagecachingandroid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mslalith.imagecachingandroid.data.repo.ImagesRepository
import dev.mslalith.imagecachingandroid.data.repo.RemoteImagesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRemoteImagesRepository(
        remoteImagesRepository: RemoteImagesRepository
    ): ImagesRepository
}
