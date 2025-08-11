package com.paris_2.san3a.presentation.screen.requestDetails.components.addOfferSection

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.datetime.LocalDate
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    datePickerState: DatePickerState,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let {
                    val localDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .let { date -> LocalDate(date.year, date.monthValue, date.dayOfMonth) }
                    onDateSelected(localDate)
                }
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
        colors = DatePickerDefaults.colors(containerColor = Theme.colors.background.card)
    ) {
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                onSurface = Theme.colors.shade.primary,
                primary = Theme.colors.brand.primary
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Theme.colors.background.card,
                    titleContentColor = Theme.colors.shade.primary,
                    headlineContentColor = Theme.colors.shade.primary,
                    weekdayContentColor = Theme.colors.shade.secondary,
                    subheadContentColor = Theme.colors.shade.secondary,
                    yearContentColor = Theme.colors.shade.primary,
                    currentYearContentColor = Theme.colors.brand.primary,
                    selectedYearContentColor = Theme.colors.button.onPrimary,
                    selectedYearContainerColor = Theme.colors.brand.primary,
                    dayContentColor = Theme.colors.shade.primary,
                    selectedDayContentColor = Theme.colors.button.onPrimary,
                    selectedDayContainerColor = Theme.colors.brand.primary,
                    todayContentColor = Theme.colors.brand.primary,
                    todayDateBorderColor = Theme.colors.brand.primary
                )
            )
        }
    }
}