package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import com.paris_2.san3a.presentation.utill.myClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.radius.defaultSan3aRadius
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun RequestCardForCraftsMan(
    modifier: Modifier = Modifier,
    title: String,
    type: String,
    offers: Int,
    imageUri: String,
    description: String,
    location: String,
    maxWidth: Int? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .then(
                if (maxWidth != null) {
                    Modifier.widthIn(max = maxWidth.dp)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .clip(RoundedCornerShape(defaultSan3aRadius.extraExtraLarge))
            .background(Theme.colors.background.card)
            .myClickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(Theme.radius.extraLarge))
                    .size(48.dp),
                model = imageUri,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = title,
                    style = Theme.textStyle.body.medium.semibold,
                    color = Theme.colors.shade.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = type,
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.shade.secondary
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(3.dp)
                            .clip(RoundedCornerShape(Theme.radius.full))
                            .background(Theme.colors.shade.tertiary)
                    )
                    Text(
                        text = stringResource(R.string.number_of_offers, offers),
                        style = Theme.textStyle.body.small.semibold,
                        color = Theme.colors.brand.primary
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            color = Theme.colors.shade.quaternary,
            thickness = 1.dp
        )
        Column {
            Text(
                text = description.trim(),
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location_outline),
                    contentDescription = "",
                    tint = Theme.colors.shade.secondary,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = location,
                    style = Theme.textStyle.body.small.medium,
                    color = Theme.colors.shade.secondary
                )
            }
        }
    }
}

@Preview
@Composable
fun RequestCardForCraftsManPreview() {
    RequestCardForCraftsMan(
        title = "Title",
        type = "Type",
        offers = 2,
        description = "Description",
        location = "Location",
        onClick = {},
        imageUri = "https://example.com/image.jpg",
    )
}