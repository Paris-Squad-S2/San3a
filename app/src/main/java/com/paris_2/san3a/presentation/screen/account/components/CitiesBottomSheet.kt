package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview

@Composable
fun CitiesBottomSheet(
    modifier: Modifier = Modifier,
    cities: List<String>,
    onClick: (String) -> Unit = {},
    isVisible: Boolean = false,
    skipPartiallyExpanded: Boolean = false,
    onDismissRequest: () -> Unit = {},
) {

    BottomSheet(
        modifier = modifier,
        isVisible = isVisible,
        skipPartiallyExpanded = skipPartiallyExpanded,
        onDismissRequest = onDismissRequest,
        header = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = onDismissRequest) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        },
        content = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(cities) { cities ->
                    LocationCard(title = cities, onClick = onClick)
                }

            }
        })
}


@Preview
@Composable
private fun GovernmentBottomSheetPreview() {
    BasePreview {
        val list = listOf(
            "Giza", "Cairo"
        )
        CitiesBottomSheet(cities = list)
    }
}