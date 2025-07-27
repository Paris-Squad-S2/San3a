package com.paris_2.san3a.presentation.shared.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTabBar(
    tabItems: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    PrimaryTabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Theme.colors.background.card,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        divider = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Theme.colors.stroke.primary)
            )
        },
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedIndex, true),
                height = 2.dp,
                width = 101.dp,
                color = Theme.colors.brand.primary,
                shape = RoundedCornerShape(topStart = Theme.radius.full, topEnd = Theme.radius.full)
            )
        }) {
        tabItems.forEachIndexed { index, title ->
            Tab(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                interactionSource = remember { MutableInteractionSource() },
                modifier = Modifier.indication(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ),
                text = { TextTabBar(selectedIndex, index, title) })
        }
    }
}

@Composable
private fun TextTabBar(
    selectedIndex: Int, index: Int, title: String, modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        targetValue = if (selectedIndex == index) Theme.colors.brand.primary else Theme.colors.shade.secondary,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "TabTextColorAnimation"
    )
    Text(
        text = title,
        color = animatedColor,
        style = if (selectedIndex == index) Theme.textStyle.label.medium.medium else Theme.textStyle.label.medium.regular,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    )
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun AppTabBarWithFirstSelectedPreview() {
    San3aTheme {
        AppTabBar(
            tabItems = listOf("first", "second", "third"),
            selectedIndex = 0,
            onTabSelected = {},
        )
    }

}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun AppTabBarWithSecondSelectedPreview() {
    San3aTheme {
        AppTabBar(
            tabItems = listOf("first", "second", "third"),
            selectedIndex = 1,
            onTabSelected = {},
        )
    }

}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun AppTabBarWithThirdSelectedPreview() {
    San3aTheme {
        AppTabBar(
            tabItems = listOf("first", "second", "third"),
            selectedIndex = 2,
            onTabSelected = {},
        )
    }

}