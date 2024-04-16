package dev.mslalith.imagecachingandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import dev.mslalith.imagecachingandroid.screens.Screen
import dev.mslalith.imagecachingandroid.screens.detail.DetailScreen
import dev.mslalith.imagecachingandroid.screens.listing.ListingScreen
import dev.mslalith.imagecachingandroid.ui.theme.ImageCachingAndroidTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageCachingAndroidTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Listing.route
                ) {
                    composable(route = Screen.Listing.route) {
                        val pagingItems = viewModel.imagesPagingData.collectAsLazyPagingItems()
                        val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

                        ListingScreen(
                            pagingItems = pagingItems,
                            searchQuery = searchQuery,
                            onItemClick = {
                                viewModel.onImageClick(image = it)
                                navController.navigate(Screen.Detail.route)
                            },
                            onSearchQueryChange = viewModel::onSearchQueryChange
                        )
                    }
                    composable(
                        route = Screen.Detail.route,
                    ) {
                        val selectedImage by viewModel.selectedImage.collectAsStateWithLifecycle()

                        selectedImage?.let {
                            DetailScreen(
                                image = it,
                                onBack = {
                                    viewModel.resetSelectedImage()
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
