package dev.mslalith.imagecachingandroid.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import dev.mslalith.imagecachingandroid.R
import dev.mslalith.imagecachingandroid.data.dto.Image
import dev.mslalith.imagecachingandroid.imageloader.ImageRequest
import dev.mslalith.imagecachingandroid.imageloader.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    image: Image,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back Icon"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
        ) {
            ImageItem(image = image)
        }
    }
}

@Composable
private fun ImageItem(
    image: Image,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        imageRequest = ImageRequest.Network(
            url = image.imageUrl,
            errorId = R.drawable.ic_image_broken
        ),
        placeholder = R.drawable.ic_image_placeholder
    )
    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painter,
        contentScale = ContentScale.FillWidth,
        contentDescription = null
    )
}
