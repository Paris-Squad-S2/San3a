package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.components.LanguageOption
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun ChangeLanguageBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
) {
    BottomSheet(
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        header = {
            val layoutDirection = LocalLayoutDirection.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (layoutDirection == LayoutDirection.Rtl) {
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            painterResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(R.string.close),
                            tint = Theme.colors.shade.secondary
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.change_language),
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.small,
                    modifier = Modifier.weight(1F)
                )
                if (layoutDirection == LayoutDirection.Ltr) {
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            painterResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(R.string.close)
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LanguageOption(
                    text = stringResource(R.string.english),
                    flagRes = R.drawable.ic_ukk_flag,
                    selected = selectedLanguage == "en",
                    onClick = { onLanguageSelected("en") },
                    modifier = Modifier.weight(1f)

                )
                LanguageOption(
                    text = stringResource(R.string.arabic),
                    flagRes = R.drawable.ic_eg_fflag,
                    selected = selectedLanguage == "ar",
                    onClick = { onLanguageSelected("ar") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }

    }

}


@Preview(showBackground = true, name = "ChangeLanguage - LTR")
@Composable
private fun ChangeLanguageBottomSheetPreviewLTR() {
    ChangeLanguageBottomSheet(
        isVisible = true,
        onDismissRequest = {},
        selectedLanguage = "en",
        onLanguageSelected = {}
    )
}

@Preview(showBackground = true, name = "ChangeLanguage - RTL")
@Composable
private fun ChangeLanguageBottomSheetPreviewRTL() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ChangeLanguageBottomSheet(
            isVisible = true,
            onDismissRequest = {},
            selectedLanguage = "ar",
            onLanguageSelected = {}
        )
    }
}