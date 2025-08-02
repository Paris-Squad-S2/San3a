package com.paris_2.san3a.presentation.screen.home.customer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.domain.entity.MostRequestedServices
import com.paris_2.san3a.presentation.screen.home.utils.getResource
import com.paris_2.san3a.presentation.screen.home.utils.getResourceColors
import com.paris_2.san3a.presentation.screen.home.utils.getResourceTint
import com.paris_2.san3a.presentation.shared.components.CategoryItem
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun MostRequestedServices(
    listOfServices: List<MostRequestedServices>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Most Requested",
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary
        )
        LazyRow(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(listOfServices) { service ->
                CategoryItem(
                    title = service.title,
                    description = service.description,
                    tint = getResourceTint(service.id),
                    iconColor = getResourceColors(service.id),
                    isLarge = true,
                    painter = painterResource(getResource(service.id))
                )
            }
        }
    }
}