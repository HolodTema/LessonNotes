package com.terabyte.lessonnotes.util

import androidx.compose.ui.graphics.Color

object ColorHelper {
    private val colors = listOf(
        Color.Red,
        Color(0xfff77f00L),
        Color.Yellow,
        Color.Green,
        Color.Cyan,
        Color.Blue,
        Color(0xFF9C27B0),
        Color.Gray,
        Color(0xFF009688),
    )

    fun getColorByIndex(index: Int): Color {
        return colors[index]
    }

    fun getColors(): List<Color> {
        return colors
    }
}