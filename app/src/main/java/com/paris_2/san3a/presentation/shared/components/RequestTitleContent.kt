package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun RequestTitleContent(
    value: String,
    onValueChange: (String) -> Unit,
    suggestions: List<String>,
    selectedSuggestion: String?,
    onChipClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = stringResource(R.string.e_g_kitchen_sink_is_leaking),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background.bottomSheet)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke.primary,
                    shape = RoundedCornerShape(Theme.radius.large)
                )
                .clip(RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.background.card),
            textStyle = Theme.textStyle.body.medium.medium.copy(color = Theme.colors.shade.primary),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = Theme.textStyle.body.medium.regular,
                            color = Theme.colors.shade.tertiary
                        )
                    }
                    innerTextField()
                }
            }
        )
        LazyRow(
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(suggestions) { suggestion ->
                SelectionChip(
                    title = suggestion,
                    onClick = { onChipClick(suggestion) },
                    isSelected = selectedSuggestion == suggestion,
                )
            }
        }
    }
}

@Composable
fun SelectionChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .height(36.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Theme.colors.brand.secondary else Theme.colors.shade.quinary,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                if (isSelected) Theme.colors.brand.tertiary else Theme.colors.shade.quinary
            )
            .clickable { onClick() }
    ){
        Text(
            text = title,
            style = Theme.textStyle.label.medium.medium,
            color = Theme.colors.shade.secondary,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview
@PreviewLightDark
@Composable
private fun Preview() {
    var text by remember { mutableStateOf("") }
    var selectedSuggestion by remember { mutableStateOf<String?>(null) }
    San3aTheme {
        RequestTitleContent(
            value = text,
            onValueChange = { text = it },
            hint = "e.g., Kitchen sink is leaking",
            suggestions = listOf("Shower is not working", "Bathroom faucet is broken", "Toilet is clogged"),
            selectedSuggestion = selectedSuggestion,
            onChipClick = {
                selectedSuggestion = it
                text = it
            }
        )
    }
}