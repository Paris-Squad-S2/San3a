package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.screen.account.ServiceUiState
import com.paris_2.san3a.presentation.shared.components.AppChip
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun ServicesContent(
    services: List<ServiceUiState>,
    onChipClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedColors =
        remember { mutableStateMapOf<String, androidx.compose.ui.graphics.Color>() }

    val primaryColors = listOf(
        Theme.colors.additional.primary.error,
        Theme.colors.additional.primary.success,
        Theme.colors.additional.primary.warning,
        Theme.colors.additional.primary.purple,
        Theme.colors.additional.primary.red,
        Theme.colors.additional.primary.blue,
        Theme.colors.additional.primary.turquoise,
        Theme.colors.additional.primary.yellow,
        Theme.colors.additional.primary.green
    )

    val usedColorIndices = remember { mutableSetOf<Int>() }

    fun getUniqueRandomColor(): androidx.compose.ui.graphics.Color {

        if (usedColorIndices.size == primaryColors.size) {
            usedColorIndices.clear()
        }

        var colorIndex: Int
        do {
            colorIndex = primaryColors.indices.random()
        } while (colorIndex in usedColorIndices)
        usedColorIndices.add(colorIndex)
        return primaryColors[colorIndex]
    }

    Column(
        modifier = modifier.background(Theme.colors.background.screen)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            services.forEach { service ->
                val chipColor = selectedColors.getOrPut(service.id) {
                    getUniqueRandomColor()
                }
                AppChip(
                    onClick = { onChipClick(service.id) },
                    label = service.serviceTitle,
                    isSelected = service.isSelected,
                    hasBackgroundColor = true,
                    unSelectedColor = Theme.colors.background.card,
                    selectedColor = chipColor,
                )
            }
        }
    }
}

@Preview
@Composable
private fun StepTwoContentPreview() {
    val dummyServices = listOf(
        ServiceUiState(id = "1", serviceTitle = "Plumbing", isSelected = false),
        ServiceUiState(id = "2", serviceTitle = "Electrical", isSelected = true),
        ServiceUiState(id = "3", serviceTitle = "Cleaning", isSelected = false),
        ServiceUiState(id = "1", serviceTitle = "AC Repair", isSelected = false),
        ServiceUiState(id = "2", serviceTitle = "Furniture", isSelected = true),
        ServiceUiState(id = "3", serviceTitle = "Landscaping", isSelected = false),
        ServiceUiState(id = "1", serviceTitle = "Roofing", isSelected = false),
        ServiceUiState(id = "2", serviceTitle = "Pest Control", isSelected = true),
        ServiceUiState(id = "3", serviceTitle = "Carpentry", isSelected = false),
        ServiceUiState(id = "3", serviceTitle = "Appliance Repair", isSelected = false),
        ServiceUiState(id = "3", serviceTitle = "Painting", isSelected = false),
        ServiceUiState(id = "3", serviceTitle = "Masonry", isSelected = false),
        ServiceUiState(id = "3", serviceTitle = "HVAC Maintenance", isSelected = false),
        ServiceUiState(id = "3", serviceTitle = "Pool Maintenance", isSelected = false),

        )
    San3aTheme {
        ServicesContent(
            services = dummyServices,
            onChipClick = {},
            modifier = Modifier.padding(vertical = 32.dp)
        )
    }
}
