package dev.mslalith.imagecachingandroid.screens

sealed interface Screen {

    data object Listing : Screen {
        const val route: String = "listing"
    }

    data object Detail : Screen {
        const val route: String = "detail"
    }
}
