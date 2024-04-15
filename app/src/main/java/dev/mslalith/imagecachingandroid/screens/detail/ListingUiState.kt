package dev.mslalith.imagecachingandroid.screens.detail

import dev.mslalith.imagecachingandroid.data.dto.Image

sealed interface ListingUiState {
    data object Loading : ListingUiState
    data class Loaded(
        val images: List<Image>
    ) : ListingUiState
}
