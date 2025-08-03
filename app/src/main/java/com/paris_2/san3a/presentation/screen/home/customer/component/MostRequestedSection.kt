package com.paris_2.san3a.presentation.screen.home.customer.component

import androidx.compose.foundation.clickable
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
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.presentation.screen.home.customer.CustomerHomeInteractionListener
import com.paris_2.san3a.presentation.screen.home.utils.getResource
import com.paris_2.san3a.presentation.screen.home.utils.getResourceColors
import com.paris_2.san3a.presentation.screen.home.utils.getResourceTint
import com.paris_2.san3a.presentation.shared.components.CategoryItem
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlin.text.get

@Composable
fun MostRequestedServices(
    services: List<Service>,
    isArabic: Boolean,
    action: CustomerHomeInteractionListener,
    modifier: Modifier = Modifier,
    onServiceClick: (String, String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Most Requested",
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(start = 16.dp)
        )
        LazyRow(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(services) { service ->
                CategoryItem(
                    title = service.title[if (isArabic) "arabicName" else "englishName"] ?: "",
                    description = service.description[if (isArabic) "arabicDescription" else "englishDescription"]
                        ?: "",
                    tint = getResourceTint(service.id),
                    iconColor = getResourceColors(service.id),
                    isLarge = true,
                    painter = painterResource(getResource(service.id)),
                    modifier = Modifier.clickable {
                        action.onServiceClick(service.id)
                        onServiceClick(
                            service.title[if (isArabic) "arabicName" else "englishName"] ?: "",
                            service.id
                        )
                    }
                )
            }
        }
    }
}