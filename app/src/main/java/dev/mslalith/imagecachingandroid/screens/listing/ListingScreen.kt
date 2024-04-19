package dev.mslalith.imagecachingandroid.screens.listing

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

@Composable
fun ListingScreen(
    pagingItems: LazyPagingItems<Image>,
    onItemClick: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyStaggeredGridState()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 12.dp),
        ) {

            if (pagingItems.loadState.refresh == LoadState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (pagingItems.itemCount == 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No images to show")
                }
            } else {
                ImagesList(
                    gridState = gridState,
                    pagingItems = pagingItems,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImagesList(
    gridState: LazyStaggeredGridState,
    pagingItems: LazyPagingItems<Image>,
    onItemClick: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.weight(weight = 1f),
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

            if (pagingItems.loadState.append == LoadState.Loading) {
                item(
                    span = StaggeredGridItemSpan.FullLine
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            val scrollBy = 1500f

            SimpleButton(
                modifier = Modifier.weight(weight = 1f),
                text = "Start",
                onClick = {
                    scope.launch {
                        gridState.animateScrollToItem(index = 0)
                    }
                }
            )
            SimpleButton(
                modifier = Modifier.weight(weight = 1f),
                text = "Up",
                onClick = {
                    scope.launch {
                        gridState.animateScrollBy(value = -scrollBy)
                    }
                }
            )
            SimpleButton(
                modifier = Modifier.weight(weight = 1f),
                text = "Down",
                onClick = {
                    scope.launch {
                        gridState.animateScrollBy(value = scrollBy)
                    }
                }
            )
            SimpleButton(
                modifier = Modifier.weight(weight = 1f),
                text = "End",
                onClick = {
                    scope.launch {
                        gridState.animateScrollToItem(index = pagingItems.itemCount - 1)
                    }
                }
            )
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

@Composable
private fun SimpleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(text = text)
    }
}
