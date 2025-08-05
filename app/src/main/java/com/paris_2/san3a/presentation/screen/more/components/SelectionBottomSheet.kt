package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun SelectionBottomSheet(
    title: String,
    isVisible: Boolean,
    items: List<SelectionItemData>,
    onDismiss: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    BottomSheet(
        isVisible = isVisible,
        onDismissRequest = onDismiss,
        header = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.small,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onDismiss) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.close),
                        tint = Theme.colors.shade.secondary
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            items.forEach { item ->
                SelectionItem(
                    item = item,
                    onClick = { onItemClick(item.title) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionBottomSheetPreview() {
    var isVisible by remember { mutableStateOf(true) }

    San3aTheme {
        SelectionBottomSheet(
            title = "Choose Governorate",
            isVisible = isVisible,
            items = listOf(
                SelectionItemData("Governorate 1", true),
                SelectionItemData("Governorate 2", true),
                SelectionItemData("Governorate 3", true),
                SelectionItemData("Governorate 4", true),
            ),
            onDismiss = { isVisible = false },
            onItemClick = { selected ->
                println("Selected: $selected")
            }
        )
    }
}
