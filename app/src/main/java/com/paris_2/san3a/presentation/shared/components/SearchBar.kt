package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier
){
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Theme.colors.stroke.primary,
                shape = RoundedCornerShape(Theme.radius.large)
            )
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card),
        textStyle = Theme.textStyle.body.medium.regular,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ){
                Row{
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Theme.colors.shade.tertiary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Box{
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = Theme.textStyle.body.medium.regular,
                                color = Theme.colors.shade.tertiary,
                            )
                        }
                        innerTextField()
                    }
                }
                if (value.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "Search",
                        tint = Theme.colors.shade.tertiary,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable { onValueChange("") }
                    )
                }
            }
        }
    )
}

@Preview
@PreviewLightDark
@Composable
private fun Preview(){
    var text by remember { mutableStateOf("") }
    San3aTheme {
        SearchBar(
            value = text,
            onValueChange = { text = it },
            hint = "Search ...",
        )
    }
}