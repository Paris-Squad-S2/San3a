package com.paris_2.san3a.presentation.screen.register.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme


enum class BottomSheetType {
    Terms,
    Privacy
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBottomSheet(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    headerText: String = "",
    skipPartiallyExpanded: Boolean = false,
    contentText: String = "",
    isErrorMessage: Boolean = false,
) {
    BottomSheet(
        header = {
            if (!isErrorMessage) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = headerText,
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.title.small
                    )
                    IconButton(onClick = onCloseClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = null
                        )
                    }
                }
            }
        },
        skipPartiallyExpanded = skipPartiallyExpanded
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                if (isErrorMessage) {
                    Image(
                        painter = painterResource(R.drawable.img_placeholder_lllustration),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.oops_something_broke),
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.Our_team_is_working_on_a_fix__Please_try_again_later_),
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.secondary,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                } else {
                    Text(
                        text = contentText,
                        style = Theme.textStyle.body.medium.medium,
                        color = Theme.colors.shade.primary
                    )
                }
            }
            item {
                AppButton(
                    type = AppButtonType.Primary,
                    state = AppButtonState.Enable,
                    onClick = onCloseClick,
                    modifier = Modifier.fillMaxWidth(),
                    size = AppButtonSize.Large,
                    text = stringResource(R.string.ok),
                )
            }
        }
    }
}