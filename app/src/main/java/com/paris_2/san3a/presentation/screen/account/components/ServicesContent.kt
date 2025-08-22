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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
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
    Column(
        modifier = modifier.background(Theme.colors.background.screen)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            services.forEach { service ->
                AppChip(
                    onClick = { onChipClick(service.id) },
                    label = service.serviceTitle,
                    isSelected = service.isSelected,
                    hasBackgroundColor = true,
                    unSelectedColor = Theme.colors.background.card,
                    selectedColor = service.colorCode.takeIf { it.isNotBlank() }?.let { Color(it.toColorInt()) } ?: Theme.colors.additional.primary.blue,
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
