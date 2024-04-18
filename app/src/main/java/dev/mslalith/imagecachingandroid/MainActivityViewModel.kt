package dev.mslalith.imagecachingandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.data.repo.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository
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
            width = 8686,
            height = 5342,
            photographerName = "Opal Ryan",
            photographerUrl = "https://duckduckgo.com/?q=viverra",
            photographerId = 2807,
            imageUrl = url,
            liked = false,
            alt = ""
        )
    }

    val imagesPagingData = imagesRepository.imagesFlow
        .flowOn(context = Dispatchers.IO)
        .cachedIn(scope = viewModelScope)

    private val _selectedImage = MutableStateFlow<Image?>(value = null)
    val selectedImage = _selectedImage.asStateFlow()

    private val _searchQuery = MutableStateFlow(value = "")
    val searchQuery = _searchQuery.asStateFlow()

    private var lastSeenPage = 1

    init {
        _searchQuery
            .debounce(timeoutMillis = 800)
            .onEach { imagesRepository.updateSearchQuery(query = it) }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onImageClick(image: Image) {
        _selectedImage.value = image
    }

    fun resetSelectedImage() {
        _selectedImage.value = null
    }
}
