package com.paris_2.san3a.presentation.screen.requestDetails.components.addOfferSection

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    timePickerState: TimePickerState,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(LocalTime(timePickerState.hour, timePickerState.minute))
                onDismiss()
            }) {
                Text(stringResource(R.string.ok), color = Theme.colors.brand.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel), color = Theme.colors.brand.primary)
            }
        },
        text = {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = Theme.colors.background.bottomSheetCard,
                    selectorColor = Theme.colors.brand.primary,
                    containerColor = Theme.colors.background.card,
                    periodSelectorBorderColor = Theme.colors.stroke.primary,
                    clockDialSelectedContentColor = Theme.colors.button.onPrimary,
                    clockDialUnselectedContentColor = Theme.colors.shade.primary,
                    periodSelectorSelectedContainerColor = Theme.colors.brand.primary,
                    periodSelectorUnselectedContainerColor = Theme.colors.background.bottomSheetCard,
                    periodSelectorSelectedContentColor = Theme.colors.button.onPrimary,
                    periodSelectorUnselectedContentColor = Theme.colors.shade.primary,
                    timeSelectorSelectedContainerColor = Theme.colors.brand.primary,
                    timeSelectorUnselectedContainerColor = Theme.colors.background.bottomSheetCard,
                    timeSelectorSelectedContentColor = Theme.colors.button.onPrimary,
                    timeSelectorUnselectedContentColor = Theme.colors.shade.primary
                )
            )
        },
        containerColor = Theme.colors.background.card
    )
}
