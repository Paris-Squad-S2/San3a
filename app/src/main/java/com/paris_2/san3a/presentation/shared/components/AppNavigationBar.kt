package com.paris_2.san3a.presentation.shared.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import com.paris_2.san3a.presentation.utill.myClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.presentation.LocalAccountType
import com.paris_2.san3a.presentation.navigation.Destination
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme


@Composable
fun AppNavigationBar(
    selectedItem: AppNavBarItem?,
    modifier: Modifier = Modifier,
    destinations: List<AppNavBarItem> = AppNavBarItem.destinations(),
    onItemClick: (Destination) -> Unit = {}
) {
    val strokeColor = Theme.colors.stroke.primary
    NavigationBar(
        containerColor = Theme.colors.background.card,
        modifier = modifier.drawWithContent {
            drawContent()
            drawLine(
                color = strokeColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        }
    ) {
        destinations.forEachIndexed { index, item ->
            AppNavBarItem(
                isSelected = item == selectedItem,
                currentItem = item,
                onItemClick = { onItemClick(item.destination) }
            )
        }
    }

}

@Composable
private fun RowScope.AppNavBarItem(
    isSelected: Boolean,
    currentItem: AppNavBarItem,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .myClickable(
                enabled = !isSelected,
                onClick = onItemClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AppNavBarIcon(
                currentItem = currentItem,
                selected = isSelected
            )
            Text(
                text = stringResource(currentItem.label),
                style = if (isSelected) Theme.textStyle.label.medium.semibold else Theme.textStyle.label.medium.medium,
                color = animateColorAsState(
                    targetValue = if (isSelected) Theme.colors.brand.primary else Theme.colors.shade.tertiary,
                    label = "NavBarItemTextColor",
                    animationSpec = tween(durationMillis = 400, easing = LinearEasing)
                ).value,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth()
            )
        }

    }
}

@Composable
private fun AppNavBarIcon(
    currentItem: AppNavBarItem,
    selected: Boolean = false
) {
    Box(contentAlignment = Alignment.Center) {
        val iconRes = if (selected) currentItem.selectedIcon else currentItem.unSelectedIcon
        val tint = if (selected) Theme.colors.brand.primary else Theme.colors.shade.tertiary

        AnimatedContent(
            targetState = iconRes,
            label = "NavBarIcon",
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 400)).togetherWith(fadeOut(animationSpec = tween(durationMillis = 400)))
            }
        ) { icon ->
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = tint
            )
        }
    }
}

sealed class AppNavBarItem(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int,
    val destination: Destination,
    val label: Int
) {
    data object Home : AppNavBarItem(
        selectedIcon = R.drawable.ic_home_angle_bold,
        unSelectedIcon = R.drawable.ic_home_angle_outline,
        destination = Destinations.Home,
        label = R.string.home
    )

    data class MyRequest(val accountType: AccountType) : AppNavBarItem(
        selectedIcon = R.drawable.ic_clipboard_bold,
        unSelectedIcon = R.drawable.ic_clipboard_outline,
        destination = Destinations.Requests,
        label = if (accountType == AccountType.CUSTOMER) R.string.my_request else R.string.my_jobs
    )

    data object Messages : AppNavBarItem(
        selectedIcon = R.drawable.ic_dialog_bold,
        unSelectedIcon = R.drawable.ic_dialog_outline,
        destination = Destinations.Messages,
        label = R.string.messages
    )

    data object More : AppNavBarItem(
        selectedIcon = R.drawable.ic_user_bold,
        unSelectedIcon = R.drawable.ic_user_circle_outline,
        destination = Destinations.More,
        label = R.string.more
    )

    companion object {
        fun destinations(accountType: AccountType = LocalAccountType.value): List<AppNavBarItem> {
            return listOf(
                Home,
                MyRequest(accountType),
                Messages,
                More
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun AppNavigationHomeSelectedBarPreview() {
    var selectedItem by remember { mutableStateOf(AppNavBarItem.destinations()[0]) }

    San3aTheme {
        AppNavigationBar(
            selectedItem = selectedItem,
            onItemClick = { },
            destinations = AppNavBarItem.destinations()
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun AppNavigationMyRequestSelectedBarPreview() {
    var selectedItem by remember { mutableStateOf(AppNavBarItem.destinations()[1]) }

    San3aTheme {
        AppNavigationBar(
            selectedItem = selectedItem,
            onItemClick = { },
            destinations = AppNavBarItem.destinations()
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun AppNavigationMessagesSelectedBarPreview() {
    var selectedItem by remember { mutableStateOf(AppNavBarItem.destinations()[2]) }

    San3aTheme {
        AppNavigationBar(
            selectedItem = selectedItem,
            onItemClick = { },
            destinations = AppNavBarItem.destinations()
        )
    }
}


@PreviewLightDark
@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun AppNavigationMoreSelectedBarPreview() {
    var selectedItem by remember { mutableStateOf(AppNavBarItem.destinations()[3]) }

    San3aTheme {
        AppNavigationBar(
            selectedItem = selectedItem,
            onItemClick = { },
            destinations = AppNavBarItem.destinations()
        )
    }
}