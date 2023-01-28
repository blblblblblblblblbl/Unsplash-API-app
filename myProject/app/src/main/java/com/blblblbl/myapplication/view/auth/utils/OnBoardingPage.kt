package com.blblblbl.myapplication.view.auth.utils

import androidx.annotation.DrawableRes
import com.blblblbl.myapplication.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.onboarding1,
        title = "Feed",
        description = "Look through photos feed, like them, see detailed information by tapping"
    )

    object Second : OnBoardingPage(
        image = R.drawable.onboarding2,
        title = "Collections",
        description = "Browse collections with a selection of photos by subject"
    )

    object Third : OnBoardingPage(
        image = R.drawable.onboarding4,
        title = "Search",
        description = "Search photos by tags"
    )
}
