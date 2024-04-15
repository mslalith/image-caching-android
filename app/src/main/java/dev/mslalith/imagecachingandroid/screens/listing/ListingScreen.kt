package dev.mslalith.imagecachingandroid.screens.listing

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.mslalith.imagecachingandroid.R
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.imageloader.ImageRequest
import dev.mslalith.imagecachingandroid.imageloader.rememberAsyncImagePainter
import dev.mslalith.imagecachingandroid.screens.detail.ListingUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListingScreen(
    uiState: ListingUiState,
    searchQuery: String,
    onItemClick: (Image) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        val gridState = rememberLazyStaggeredGridState()

        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 12.dp),
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                onSearch = { onSearchQueryChange(searchQuery) },
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                        )
                    }
                },
                content = {}
            )

            when (uiState) {
                is ListingUiState.Loaded -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(count = 2),
                        state = gridState,
                        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                        verticalItemSpacing = 8.dp
                    ) {
                        items(
                            items = uiState.images,
                            key = { it.id }
                        ) { image ->
                            ImageItem(
                                image = image,
                                onItemClick = onItemClick,
                                modifier = Modifier.animateItemPlacement()
                            )
//                            CoilImageItem(
//                                image = image,
//                                onItemClick = onItemClick,
//                                modifier = Modifier.animateItemPlacement()
//                            )
                        }
                    }
                }
                ListingUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun ImageItem(
    image: Image,
    onItemClick: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        imageRequest = ImageRequest.Network(
            url = image.imageURL,
            errorId = R.drawable.ic_image_broken
        ),
        placeholder = R.drawable.ic_image_placeholder
    )
    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { onItemClick(image) }
    ) {
        Image(
            painter = painter,
            contentDescription = null
        )
    }
}

@Composable
private fun CoilImageItem(
    image: Image,
    onItemClick: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val request = remember(context) {
        coil.request.ImageRequest.Builder(context)
            .data(image.imageURL)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_broken)
            .build()
    }
    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { onItemClick(image) }
    ) {
        AsyncImage(
            model = request,
            contentDescription = null
        )
    }
}
