package com.paris_2.san3a.presentation.shared.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.utill.myClickable

@Immutable
data class AppTextFieldColors(
    val unfocusedTextColor: Color,
    val focusedTextColor: Color,
    val focusedBorderColor: Color,
    val unfocusedBorderColor: Color,
    val errorBorderColor: Color,
    val errorTextColor: Color,
    val cursorColor: Color,
    val errorCursorColor: Color,
    val disabledTextColor: Color,
    val disabledBorderColor: Color,
    val disabledPlaceholderColor: Color,
    val disabledLeadingIconColor: Color,
    val disabledTrailingIconColor: Color,
    val disabledLabelColor: Color,
)

@Composable
fun appTextFieldColors(
    textColor: Color? = null
): AppTextFieldColors = AppTextFieldColors(
    unfocusedTextColor = textColor ?: Theme.colors.shade.primary,
    focusedTextColor = Theme.colors.shade.primary,
    focusedBorderColor = Theme.colors.brand.primary,
    unfocusedBorderColor = Theme.colors.stroke.primary,
    errorBorderColor = Theme.colors.additional.primary.red,
    errorTextColor = Theme.colors.shade.primary,
    cursorColor = Theme.colors.brand.primary,
    errorCursorColor = Color.Transparent,
    disabledTextColor = Theme.colors.shade.primary,
    disabledBorderColor = Theme.colors.stroke.primary,
    disabledPlaceholderColor = Theme.colors.shade.tertiary,
    disabledLeadingIconColor = Theme.colors.shade.tertiary,
    disabledTrailingIconColor = Theme.colors.shade.tertiary,
    disabledLabelColor = Theme.colors.shade.tertiary,
)

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    label: String? = null,
    forgotPasswordClick: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textColor: Color? = null,
    textStyle: TextStyle? = null,
    readOnly: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val colors = appTextFieldColors(textColor)
    var isFocused by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (label != null) {
                Text(
                    text = label,
                    style = Theme.textStyle.body.medium.regular,
                    color = if (!enabled) colors.disabledLabelColor else Theme.colors.shade.secondary,
                    modifier = Modifier
                )
            }
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            enabled = enabled,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else visualTransformation,
            textStyle = textStyle ?: Theme.textStyle.body.medium.medium.copy(
                color = when {
                    !enabled -> colors.disabledTextColor
                    isError -> colors.errorTextColor
                    else -> colors.unfocusedTextColor
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                .border(
                    width = 1.dp,
                    color = when {
                        !enabled -> colors.disabledBorderColor
                        isError -> colors.errorBorderColor
                        isFocused -> colors.focusedBorderColor
                        else -> colors.unfocusedBorderColor
                    },
                    shape = RoundedCornerShape(Theme.radius.large)
                )
                .clip(RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.large)),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(
                when {
                    isError -> colors.errorCursorColor
                    else -> colors.cursorColor
                }
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            Theme.colors.background.card,
                            RoundedCornerShape(Theme.radius.large)
                        )
                        .fillMaxWidth()
                        .padding(
                            start = if (leadingIcon != null) 16.dp else 0.dp,
                            end = if (isPassword || isError || trailingIcon != null) 16.dp else 0.dp
                        )
                ) {
                    leadingIcon?.invoke()

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                start = if (leadingIcon != null) 8.dp else 16.dp,
                                end = if (singleLine) 0.dp else 16.dp,
                                top = 12.dp,
                                bottom = 12.dp,
                            )
                    ) {
                        if (value.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                style = Theme.textStyle.body.medium.regular,
                                color = if (!enabled) colors.disabledPlaceholderColor else Theme.colors.shade.tertiary,
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                overflow = if (singleLine) TextOverflow.Ellipsis else TextOverflow.Clip
                            )
                        }
                        innerTextField()
                    }
                    when {
                        isPassword -> {
                            val image =
                                if (passwordVisible) painterResource(R.drawable.eye_opened) else painterResource(
                                    R.drawable.eye_closed
                                )
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = image,
                                    contentDescription = stringResource(R.string.toggle_password_visibility)
                                )
                            }
                        }

                        isError -> {
                            Icon(
                                painter = painterResource(R.drawable.outline_danger_triangle),
                                contentDescription = stringResource(R.string.error),
                                tint = colors.errorBorderColor
                            )
                        }

                        trailingIcon != null -> {
                            trailingIcon()
                        }
                    }
                }
            }
        )
        AnimatedVisibility(
            visible = isError && !errorMessage.isNullOrEmpty()
        ) {
            Text(
                text = errorMessage ?: "",
                color = Theme.colors.additional.primary.red,
                style = Theme.textStyle.body.small.regular,
            )
        }
        if (isPassword) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    textDecoration = TextDecoration.Underline,
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.secondary,
                    modifier = Modifier.myClickable {
                        if (forgotPasswordClick != null) {
                            forgotPasswordClick()
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun PreviewBasicAppTextField() {
    San3aTheme {
        var text by remember { mutableStateOf("") }
        AppTextField(
            label = "label",
            value = text,
            onValueChange = { text = it },
            placeholder = "Enter your name",
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.due_tone_profile),
                    contentDescription = null,
                    tint = Theme.colors.shade.tertiary
                )
            }
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewPasswordAppTextField() {
    San3aTheme {
        var password by remember { mutableStateOf("") }
        AppTextField(
            label = "label",
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
            isPassword = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.due_tone_profile),
                    contentDescription = null,
                    tint = Theme.colors.shade.tertiary
                )
            }
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewErrorAppTextField() {
    San3aTheme {
        var email by remember { mutableStateOf("") }
        AppTextField(
            label = "label",
            value = email,
            onValueChange = { email = it },
            placeholder = "Email",
            isError = true,
            errorMessage = "Error Message",
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.due_tone_profile),
                    contentDescription = null,
                    tint = Theme.colors.shade.tertiary
                )
            }
        )
    }
}