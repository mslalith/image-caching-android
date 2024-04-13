package dev.mslalith.imagecachingandroid.screens.listing

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.mslalith.imagecachingandroid.R
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.imageloader.ImageRequest
import dev.mslalith.imagecachingandroid.imageloader.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListingScreen(
    images: List<Image>,
    onItemClick: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        val gridState = rememberLazyStaggeredGridState()
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 12.dp),
            columns = StaggeredGridCells.Fixed(count = 2),
            state = gridState,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            verticalItemSpacing = 8.dp
        ) {
            items(
                items = images,
                key = { it.id }
            ) { image ->
                ImageItem(
                    image = image,
                    onItemClick = onItemClick,
                    modifier = Modifier.animateItemPlacement()
                )
//                CoilImageItem(
//                    image = image,
//                    onItemClick = onItemClick,
//                    modifier = Modifier.animateItemPlacement()
//                )
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
