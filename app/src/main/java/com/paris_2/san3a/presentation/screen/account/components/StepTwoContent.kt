package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.paris_2.san3a.presentation.shared.components.AppChip
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun StepTwoContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(Theme.colors.background.screen)
    ) {
        FlowRow(
            mainAxisSpacing = 12.dp,
            crossAxisSpacing = 12.dp
        ) {
            AppChip(
                onClick = {},
                label = "Plumbing",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                selectedColor = Theme.colors.additional.primary.purple,
            )
            AppChip(
                onClick = {},
                label = "Electrical",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                selectedColor = Theme.colors.additional.primary.turquoise,
            )
            AppChip(
                onClick = {},
                label = "Cleaning",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                hasBorder = true,
            )
            AppChip(
                onClick = {},
                label = "AC Repair",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                hasBorder = true
            )
            AppChip(
                onClick = {},
                label = "Furniture",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                selectedColor = Theme.colors.additional.primary.yellow,
            )
            AppChip(
                onClick = {},
                label = "Landscaping",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                hasBorder = true
            )
            AppChip(
                onClick = {},
                label = "Roofing",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                hasBorder = true
            )
            AppChip(
                onClick = {},
                label = "Pest Control",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                hasBorder = true
            )
            AppChip(
                onClick = {},
                label = "Carpentry",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                hasBorder = true
            )
            AppChip(
                onClick = {},
                label = "Appliance Repair",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                hasBorder = true
            )
            AppChip(
                onClick = {},
                label = "Painting",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                selectedColor = Theme.colors.additional.primary.blue,
            )
            AppChip(
                onClick = {},
                label = "Masonry",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                selectedColor = Theme.colors.additional.primary.red,
            )
            AppChip(
                onClick = {},
                label = "HVAC Maintenance",
                hasBackgroundColor = true,
                unSelectedColor = Theme.colors.background.card,
                selectedColor = Theme.colors.additional.primary.green,
            )
            AppChip(
                onClick = {},
                label = "Pool Maintenance",
                hasBackgroundColor = true,
                selectedColor = Theme.colors.background.card,
                unSelectedColor = Theme.colors.background.card,
                hasBorder = true
            )
        }
    }
}

@Preview
@Composable
private fun AccountChipsPreview() {
    San3aTheme {
        StepTwoContent()
    }
}
