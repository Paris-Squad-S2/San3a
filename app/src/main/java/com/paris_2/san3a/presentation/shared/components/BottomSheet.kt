package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    isVisible: Boolean = true,
    skipPartiallyExpanded: Boolean = true,
    header: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {},
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    LaunchedEffect(isVisible) {
        when {
            isVisible -> sheetState.show()
            else -> sheetState.hide()
        }
    }

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .fillMaxWidth(),
            sheetState = sheetState,
            containerColor = Theme.colors.background.bottomSheet,
            dragHandle = {
                BottomSheetDragHandle()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                header?.invoke()
                content()
            }
        }
    }
}

@Composable
private fun BottomSheetDragHandle() {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .background(
                color = Theme.colors.shade.quaternary,
                shape = RoundedCornerShape(8.dp)
            )
            .size(width = 48.dp, height = 3.dp)

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetPrev() {
    San3aTheme {
        BottomSheet(
            header = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Edit Profile",
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.title.small,
                        modifier = Modifier.weight(1F)
                    )

                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = null
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Your name",
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.primary
                )
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = "John Doe",
                    onValueChange = {},
                )
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "Your profile photo",
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.primary
                )

                Image(
                    modifier = Modifier.size(96.dp),
                    painter = painterResource(R.drawable.image_person),
                    contentDescription = "image person"
                )
            }
        }

    }
}