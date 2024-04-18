package dev.mslalith.imagecachingandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.data.repo.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    imagesRepository: ImagesRepository
) : ViewModel() {

    val imagesPagingData = imagesRepository.imagesFlow
        .flowOn(context = Dispatchers.IO)
        .cachedIn(scope = viewModelScope)

    private val _selectedImage = MutableStateFlow<Image?>(value = null)
    val selectedImage = _selectedImage.asStateFlow()

    fun onImageClick(image: Image) {
        _selectedImage.value = image
    }

    fun resetSelectedImage() {
        _selectedImage.value = null
    }
}
