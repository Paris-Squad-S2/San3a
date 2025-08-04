package com.paris_2.san3a.presentation.screen.home.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

fun getResource(id: String): Int {
    return when(id){
        "00FoToEQx8OAVK8fprck" -> R.drawable.ic_paint_roller_bold
        "0AyHLfbkAS6aFnHeZL6Z" -> R.drawable.ic_plug_circle_bold
        "N1XbpJwF27ByQxGJzGcW" -> R.drawable.ic_washing_machine_bold
        "VMuPvjaMBy7xZQB2om6l" -> R.drawable.ic_conditioner_bold
        "ig1I47LLgLUHg96d8ZwB" -> R.drawable.ic_leaf_bold
        "keLsnqeGp8RKpZwWm7J0" -> R.drawable.ic_waterdrops_bold
        else -> R.drawable.ic_sledgehammer_bold
    }
}

@Composable
fun getResourceColors(id: String): Color {
    return when(id){
        "00FoToEQx8OAVK8fprck" -> Theme.colors.additional.secondary.yellow  //painting
        "0AyHLfbkAS6aFnHeZL6Z" -> Theme.colors.additional.secondary.turquoise // Electrical
        "N1XbpJwF27ByQxGJzGcW" -> Theme.colors.additional.secondary.purple // Washing
        "VMuPvjaMBy7xZQB2om6l" -> Theme.colors.additional.secondary.red // Conditioner
        "ig1I47LLgLUHg96d8ZwB" -> Theme.colors.additional.secondary.green // Gardening
        "keLsnqeGp8RKpZwWm7J0" -> Theme.colors.additional.secondary.blue // Plumbing
        else -> Theme.colors.additional.secondary.blue
    }
}

@Composable
fun getResourceTint(id: String): Color {
    return when(id){
        "00FoToEQx8OAVK8fprck" -> Theme.colors.additional.primary.yellow
        "0AyHLfbkAS6aFnHeZL6Z" -> Theme.colors.additional.primary.turquoise
        "N1XbpJwF27ByQxGJzGcW" -> Theme.colors.additional.primary.purple
        "VMuPvjaMBy7xZQB2om6l" -> Theme.colors.additional.primary.red
        "ig1I47LLgLUHg96d8ZwB" -> Theme.colors.additional.primary.green
        "keLsnqeGp8RKpZwWm7J0" -> Theme.colors.additional.primary.blue
        else -> Theme.colors.additional.secondary.blue
    }
}
