package dev.mslalith.imagecachingandroid.imageloader

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import dev.mslalith.imagecachingandroid.imageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AsyncImagePainter(
    private val imageLoader: ImageLoader,
    private val imageRequest: ImageRequest,
    @DrawableRes private val placeholder: Int
) : Painter(), RememberObserver {

    private var scope: CoroutineScope? = null
    private var painter by mutableStateOf<Painter?>(value = null)

    override val intrinsicSize: Size
        get() = painter?.intrinsicSize ?: Size.Unspecified

    override fun DrawScope.onDraw() {
        painter?.apply { draw(size) }
    }

    override fun onRemembered() {
        // already remembered this
        if (scope != null) return

        val localScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        scope = localScope

        localScope.launch {
            imageLoader.getCachedBitmap(imageRequest = imageRequest)
                ?.let { painter = BitmapPainter(it.asImageBitmap()) }
                ?: kotlin.run {
                    painter = imageLoader.execute(imageRequest = ImageRequest.Drawable(id = placeholder)).toPainter()

                    val loadedBitmap = withContext(Dispatchers.IO) {
                        imageLoader.execute(imageRequest = imageRequest)
                    }

                    painter = loadedBitmap.toPainter()
                }
        }
    }

    override fun onForgotten() {
        scope?.cancel()
        scope = null
    }

    override fun onAbandoned() {}
}

private fun Bitmap.toPainter(): Painter = BitmapPainter(asImageBitmap())

@Composable
fun rememberAsyncImagePainter(
    imageRequest: ImageRequest,
    @DrawableRes placeholder: Int,
): AsyncImagePainter {
    val context = LocalContext.current
    val imageLoader = context.imageLoader

    return remember(
        imageLoader,
        imageRequest,
        placeholder
    ) {
        AsyncImagePainter(
            imageLoader = imageLoader,
            imageRequest = imageRequest,
            placeholder = placeholder,
        )
    }
}
