import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    showBackIcon: Boolean = true,
    actionIcon: @Composable () -> Unit = {},
    title: String,
    onBackClick: () -> Unit,
    showBackGround: Boolean = true,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = if (showBackGround) Theme.colors.background.card else Color.Unspecified)
            .padding(8.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackIcon) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_outline),
                    contentDescription = null,
                    tint = Theme.colors.button.onSecondary
                )
            }
        }
        Text(
            text = title,
            modifier = Modifier.weight(1F),
            style = Theme.textStyle.title.medium,
            color = Theme.colors.shade.primary,
        )
        actionIcon()
    }

}

@PreviewLightDark
@Composable
private fun AppBarPrev() {
    AppBar(
        title = "Title",
        onBackClick = {},
        actionIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_notification_outline),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    )
}