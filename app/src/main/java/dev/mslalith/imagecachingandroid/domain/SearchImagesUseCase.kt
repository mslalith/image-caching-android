package dev.mslalith.imagecachingandroid.domain

import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.data.model.remote.ImageResponse
import dev.mslalith.imagecachingandroid.data.model.remote.toImage
import dev.mslalith.imagecachingandroid.data.repo.ImagesRepository
import javax.inject.Inject

class SearchImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int
    ): List<Image> {
        val response = imagesRepository.searchImages(query = query, page = page)
        return response?.hits?.map(ImageResponse::toImage) ?: emptyList()
    }
}
