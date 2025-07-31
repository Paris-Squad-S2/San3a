package com.paris_2.san3a.presentation.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

data class Country(
    val name: String,
    val code: String,
    val flagRes: Int,
)

@Composable
fun CountrySelector(
    selectedCountry: Country,
    countries: List<Country>,
    expanded: Boolean,
    onToggleDropdown: () -> Unit,
    onCountrySelected: (Country) -> Unit,
    onDismissDropdown: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.wrapContentSize()) {
        Column {
            CountrySelectorHeader(
                selectedCountry = selectedCountry,
                expanded = expanded,
                onClick = onToggleDropdown
            )

            CountrySelector_DropdownList(
                visible = expanded,
                countries = countries,
                selectedCountry = selectedCountry,
                onCountrySelected = { country ->
                    onCountrySelected(country)
                    onDismissDropdown()
                }
            )
        }
    }
}

@Composable
private fun CountrySelectorHeader(
    selectedCountry: Country,
    expanded: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Image(
            painter = painterResource(id = selectedCountry.flagRes),
            contentDescription = "${selectedCountry.name} flag",
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(2.dp))
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            imageVector = if (expanded) ImageVector.vectorResource(R.drawable.ic_alt_arrow_up_outline) else ImageVector.vectorResource(
                R.drawable.ic_alt_arrow_down_outline
            ),
            tint = Theme.colors.shade.tertiary,
            contentDescription = if (expanded) "Close dropdown" else "Open dropdown",
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun CountrySelector_DropdownList(
    visible: Boolean,
    countries: List<Country>,
    selectedCountry: Country,
    onCountrySelected: (Country) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            animationSpec = tween(250),
            expandFrom = Alignment.Top
        ) + fadeIn(animationSpec = tween(200)),
        exit = shrinkVertically(
            animationSpec = tween(200),
            shrinkTowards = Alignment.Top
        ) + fadeOut(animationSpec = tween(150)),
        modifier = Modifier.zIndex(10f)
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                items(
                    items = countries.filter { it != selectedCountry },
                    key = { it.code }
                ) { country ->
                    CountrySelector_DropdownItem(
                        country = country,
                        onClick = { onCountrySelected(country) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CountrySelector_DropdownItem(
    country: Country,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .padding(horizontal = 5.dp, vertical = 3.dp)
    ) {
        Image(
            painter = painterResource(id = country.flagRes),
            contentDescription = "${country.name} flag",
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(2.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InlineCountrySelectorPreview() {
    val previewCountries = listOf(
        Country("Iraq", "+964", R.drawable.ic_iraq_flag),
        Country("United Kingdom", "+44", R.drawable.ic_uk_flag),

        )

    var selectedCountry by remember { mutableStateOf(previewCountries[0]) }
    var expanded by remember { mutableStateOf(false) }

    San3aTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Text(
                    "Country Selector Component:",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.width(12.dp))

                CountrySelector(
                    selectedCountry = selectedCountry,
                    countries = previewCountries,
                    expanded = expanded,
                    onToggleDropdown = { expanded = !expanded },
                    onCountrySelected = { selectedCountry = it },
                    onDismissDropdown = { expanded = false }
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    "Selected: ${selectedCountry.name} (${selectedCountry.code})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}