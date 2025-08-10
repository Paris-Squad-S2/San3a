package com.paris_2.san3a.presentation.screen.requestDetails.components.addOfferSection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.AppTextField
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOfferForm(
    modifier: Modifier = Modifier,
    price: String,
    onPriceChange: (String) -> Unit,
    selectedDate: LocalDate?,
    onDateChange: (LocalDate) -> Unit,
    selectedTime: LocalTime?,
    onTimeChange: (LocalTime) -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit,
    showDatePicker: Boolean,
    onShowDatePickerChange: (Boolean) -> Unit,
    datePickerState: DatePickerState,
    showTimePicker: Boolean,
    onShowTimePickerChange: (Boolean) -> Unit,
    timePickerState: TimePickerState,
    isOfferValid: Boolean,
) {
    Card(
        shape = RoundedCornerShape(Theme.radius.tripleXLarge),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.add_your_offer),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary
            )
            Text(
                text = stringResource(R.string.be_competitive_but_fair_consider_materials_and_time),
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary
            )

            AppTextField(
                value = price,
                onValueChange = onPriceChange,
                label = stringResource(R.string.your_price),
                placeholder = stringResource(R.string.price_placeholder),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_wallet_outline),
                        contentDescription = null,
                        tint = Theme.colors.shade.tertiary
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            PickerField(
                value = selectedDate?.toString(),
                label = stringResource(R.string.preferred_date),
                placeholder = stringResource(R.string.select_date),
                leadingIcon = R.drawable.ic_calendar_minimalistic_outline,
                onClick = { onShowDatePickerChange(true) }
            )

            PickerField(
                value = selectedTime?.toString(),
                label = stringResource(R.string.preferred_time),
                placeholder = stringResource(R.string.select_time),
                leadingIcon = R.drawable.ic_clock_circle_outline,
                onClick = { onShowTimePickerChange(true) }
            )

            AppTextField(
                value = message,
                onValueChange = onMessageChange,
                label = stringResource(R.string.message_to_customer),
                placeholder = stringResource(R.string.offer_message_placeholder),
                minLines = 4,
                singleLine = false
            )

            AppButton(
                type = AppButtonType.Primary,
                size = AppButtonSize.Large,
                state = if (isOfferValid) AppButtonState.Enable else AppButtonState.Disabled,
                text = stringResource(R.string.send_offer),
                onClick = onSendClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            datePickerState = datePickerState,
            onDateSelected = onDateChange,
            onDismiss = { onShowDatePickerChange(false) }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            timePickerState = timePickerState,
            onTimeSelected = onTimeChange,
            onDismiss = { onShowTimePickerChange(false) }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AddOfferFormPreview() {
    val price = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val selectedTime = remember { mutableStateOf<LocalTime?>(null) }
    val message = remember { mutableStateOf("") }
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    AddOfferForm(
        price = price.value,
        onPriceChange = { price.value = it },
        selectedDate = selectedDate.value,
        onDateChange = { selectedDate.value = it },
        selectedTime = selectedTime.value,
        onTimeChange = { selectedTime.value = it },
        message = message.value,
        onMessageChange = { message.value = it },
        onSendClick = {},
        showDatePicker = showDatePicker.value,
        onShowDatePickerChange = { showDatePicker.value = it },
        datePickerState = datePickerState,
        showTimePicker = showTimePicker.value,
        onShowTimePickerChange = { showTimePicker.value = it },
        timePickerState = timePickerState,
        isOfferValid = true
    )
}
