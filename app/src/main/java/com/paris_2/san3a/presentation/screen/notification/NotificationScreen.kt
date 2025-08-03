package com.paris_2.san3a.presentation.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.notification.components.NotificationCard
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import java.time.LocalDate

@Composable
fun NotificationScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
    ) {
        AppBar(
            title = "Notifications",
            onBackClick = { onBackClick() },

            )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
        ) {
            item {
                NotificationCard(
                    title = "title",
                    caption = "caption",
                    date = LocalDate.now(),
                    modifier.fillMaxWidth()
                )
            }
            item {
                NotificationCard(
                    title = "title",
                    caption = "caption",
                    date = LocalDate.now(),
                    modifier.fillMaxWidth()
                )
            }
            item {
                NotificationCard(
                    title = "title",
                    caption = "caption",
                    date = LocalDate.now(),
                    modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Preview
@Composable
private fun NotificationScreenPreview() {
    San3aTheme {
        NotificationScreen(
            {}
        )
    }
}