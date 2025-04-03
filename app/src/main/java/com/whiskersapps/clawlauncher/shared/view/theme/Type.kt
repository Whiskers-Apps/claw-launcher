package com.whiskersapps.clawlauncher.shared.view.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val REGULAR_LABEL_STYLE = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

val SMALL_LABEL_STYLE = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp
)

val TINY_LABEL_STYLE = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )
)