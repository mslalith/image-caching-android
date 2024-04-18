package dev.mslalith.imagecachingandroid.screens.listing

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import dev.mslalith.imagecachingandroid.R
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.imageloader.ImageRequest
import dev.mslalith.imagecachingandroid.imageloader.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListingScreen(
    pagingItems: LazyPagingItems<Image>,
    onItemClick: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyStaggeredGridState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        gridState.animateScrollBy(2000f)
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 12.dp),
        ) {
            when (pagingItems.loadState.refresh) {
                is LoadState.Error -> Unit
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(count = 2),
                state = gridState,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalItemSpacing = 8.dp
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id }
                ) { index ->
                    pagingItems[index]?.let { image ->
                        ImageItem(
                            image = image,
                            onItemClick = onItemClick,
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }

                item {
                    if (pagingItems.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
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
            url = image.imageUrl,
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
            modifier = Modifier.fillMaxWidth(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}
