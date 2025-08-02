package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview

@Composable
fun GovernmentBottomSheet(
    modifier: Modifier = Modifier,
    governments: List<String>,
    onClick: (String) -> Unit = {},
    isVisible: Boolean = false,
) {

    BottomSheet(
        modifier = modifier,
        isVisible = isVisible,
        header = {
            Text(
                text = "Choose Government",
                style = Theme.textStyle.title.medium
            )
        },
        content = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(governments) { government ->
                    GovernmentItem(title = government, onClick = onClick)
                }

            }
        }
    )
}

@Composable
fun GovernmentItem(modifier: Modifier = Modifier, title: String, onClick: (String) -> Unit = {}) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.bottomSheetCard)
    ) {
        Row {
            Text(
                text = title,
                style = Theme.textStyle.body.medium.medium,
                modifier = Modifier.weight(1F)
            )
            Icon(
                painter = painterResource(
                    R.drawable.ic_alt_arrow_right_outline
                ),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun GovernmentBottomSheetPreview() {
    BasePreview {
        val list = listOf(
            "Giza",
            "Cairo"
        )
        GovernmentBottomSheet(governments = list)
    }
}