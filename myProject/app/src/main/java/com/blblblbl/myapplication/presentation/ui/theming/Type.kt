package com.blblblbl.myapplication.presentation.ui.theming

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.blblblbl.myapplication.R


/**
 * https://fonts.google.com/specimen/Montserrat
 */
private val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_medium, FontWeight.W500),
    Font(R.font.montserrat_semibold, FontWeight.W600)
)

/**
 * https://fonts.google.com/specimen/Domine
 */
private val Domine = FontFamily(
    Font(R.font.domine_regular),
    Font(R.font.domine_bold, FontWeight.Bold)
)

val UnsplashTypography = Typography(

    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontSize = 10.sp
    )
)