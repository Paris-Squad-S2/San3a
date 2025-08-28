package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import com.paris_2.san3a.presentation.utill.myClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun SnackBar(
    text:String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(Theme.radius.extraLarge),
                ambientColor = Theme.colors.brand.primary,
                spotColor = Theme.colors.brand.primary,
                clip = true,
            )
            .background(Theme.colors.background.card)
            .myClickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}


@PreviewMultiDevices
@Composable
private fun SnackBarErrorPreview() {
    San3aTheme {
        Column(Modifier.padding(50.dp)) {
            SnackBar(
                text = "some_error_happened",
            )

        }

    }
}

