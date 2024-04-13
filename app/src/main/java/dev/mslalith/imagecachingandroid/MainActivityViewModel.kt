package dev.mslalith.imagecachingandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.domain.SearchImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val searchImagesUseCase: SearchImagesUseCase
) : ViewModel() {

    private val dummyImages = listOf(
        "https://images.pexels.com/photos/1722183/pexels-photo-1722183.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1920",
        "https://images.pexels.com/photos/1667580/pexels-photo-1667580.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1920", // not a valid one, to test -ve case
        "https://images.pexels.com/photos/1470405/pexels-photo-1470405.jpeg?dl&fit=crop&crop=entropy&w=1280&h=853",
        "https://images.pexels.com/photos/1005417/pexels-photo-1005417.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1600",
        "https://images.pexels.com/photos/1294671/pexels-photo-1294671.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1920",
        "https://images.pexels.com/photos/1040893/pexels-photo-1040893.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1919",
        "https://images.pexels.com/photos/1956974/pexels-photo-1956974.jpeg?dl&fit=crop&crop=entropy&w=1280&h=853",
        "https://images.pexels.com/photos/1374064/pexels-photo-1374064.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1706",
        "https://images.pexels.com/photos/1931142/pexels-photo-1931142.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1919",
        "https://images.pexels.com/photos/1295036/pexels-photo-1295036.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1707",
        "https://images.pexels.com/photos/1320684/pexels-photo-1320684.jpeg?dl&fit=crop&crop=entropy&w=1920&h=1440",
        "https://images.pexels.com/photos/1908677/pexels-photo-1908677.jpeg?dl&fit=crop&crop=entropy&w=1280&h=1706"
    ).mapIndexed { index, url ->
        Image(
            id = index.toLong(),
            imageURL = url
        )
    }

    private val _imagesList = MutableStateFlow<List<Image>>(value = emptyList())
    val imagesList = _imagesList.asStateFlow()

    private val _selectedImage = MutableStateFlow<Image?>(value = null)
    val selectedImage = _selectedImage.asStateFlow()

    private var lastSeenPage = 1

    init {
        viewModelScope.launch {
            // initially search for the first page
            val images = searchImagesUseCase(query = "", page = 1)
            _imagesList.update { images }
        }
    }

    fun onImageClick(image: Image) {
        _selectedImage.value = image
    }

    fun resetSelectedImage() {
        _selectedImage.value = null
    }
}
