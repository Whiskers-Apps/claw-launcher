package com.whiskersapps.clawlauncher.shared.view.theme

import androidx.compose.ui.graphics.Color

//data class CustomTheme(
//    val id: String,
//    val name: String,
//    val background: Color,
//    val secondaryBackground: Color,
//    val text: Color,
//    val accent: Color,
//    val onAccent: Color
//)
//
//val CUSTOM_THEMES = listOf(
//    getWhiskersTigerTheme("banana"),
//    getWhiskersTigerTheme("blueberry"),
//    getWhiskersTigerTheme("cherry"),
//    getWhiskersTigerTheme("grape"),
//    getWhiskersTigerTheme("kiwi"),
//    getWhiskersTigerTheme("tangerine"),
//    getWhiskersPantherTheme("banana"),
//    getWhiskersPantherTheme("blueberry"),
//    getWhiskersPantherTheme("cherry"),
//    getWhiskersPantherTheme("grape"),
//    getWhiskersPantherTheme("kiwi"),
//    getWhiskersPantherTheme("tangerine")
//)
//
//fun getWhiskersTigerTheme(accentId: String): CustomTheme {
//    return CustomTheme(
//        id = "tiger-$accentId",
//        name = "Tiger ${accentId.replaceFirstChar { it.uppercaseChar() }}",
//        background = Color(getColor(WhiskersColor.TigerNeutralTwo).asLong()),
//        secondaryBackground = Color(getColor(WhiskersColor.TigerNeutralFour).asLong()),
//        text = Color(getColor(WhiskersColor.TigerText).asLong()),
//        accent = when (accentId) {
//            "banana" -> Color(getColor(WhiskersColor.TigerBanana).asLong())
//            "blueberry" -> Color(getColor(WhiskersColor.TigerBlueberry).asLong())
//            "cherry" -> Color(getColor(WhiskersColor.TigerCherry).asLong())
//            "grape" -> Color(getColor(WhiskersColor.TigerGrape).asLong())
//            "kiwi" -> Color(getColor(WhiskersColor.TigerKiwi).asLong())
//            else -> Color(getColor(WhiskersColor.TigerTangerine).asLong())
//        },
//        onAccent = Color(getColor(WhiskersColor.TigerNeutral).asLong())
//    )
//}
//
//fun getWhiskersPantherTheme(accentId: String): CustomTheme {
//    return CustomTheme(
//        id = "panther-$accentId",
//        name = "Panther ${accentId.replaceFirstChar { it.uppercaseChar() }}",
//        background = Color(getColor(WhiskersColor.PantherNeutralTwo).asLong()),
//        secondaryBackground = Color(getColor(WhiskersColor.PantherNeutralFour).asLong()),
//        text = Color(getColor(WhiskersColor.PantherText).asLong()),
//        accent = when (accentId) {
//            "banana" -> Color(getColor(WhiskersColor.PantherBanana).asLong())
//            "blueberry" -> Color(getColor(WhiskersColor.PantherBlueberry).asLong())
//            "cherry" -> Color(getColor(WhiskersColor.PantherCherry).asLong())
//            "grape" -> Color(getColor(WhiskersColor.PantherGrape).asLong())
//            "kiwi" -> Color(getColor(WhiskersColor.PantherKiwi).asLong())
//            else -> Color(getColor(WhiskersColor.PantherTangerine).asLong())
//        },
//        onAccent = Color(getColor(WhiskersColor.PantherNeutral).asLong())
//    )
//}
//
//val TIGER_THEMES = listOf(
//    getWhiskersTigerTheme("banana"),
//    getWhiskersTigerTheme("blueberry"),
//    getWhiskersTigerTheme("cherry"),
//    getWhiskersTigerTheme("grape"),
//    getWhiskersTigerTheme("kiwi"),
//    getWhiskersTigerTheme("tangerine"),
//)
//
//val PANTHER_THEMES = listOf(
//    getWhiskersPantherTheme("banana"),
//    getWhiskersPantherTheme("blueberry"),
//    getWhiskersPantherTheme("cherry"),
//    getWhiskersPantherTheme("grape"),
//    getWhiskersPantherTheme("kiwi"),
//    getWhiskersPantherTheme("tangerine"),
//)