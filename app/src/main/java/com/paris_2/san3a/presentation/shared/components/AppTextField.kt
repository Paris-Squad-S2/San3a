package com.paris_2.san3a.presentation.shared.components

import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    leadingIcon: Int? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    label: String? = null,
    forgotPasswordClick: (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

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
                    color = Theme.colors.shade.secondary,
                    modifier = Modifier
                )
            }
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.large)),
            textStyle = Theme.textStyle.body.medium.medium,
            placeholder = placeholder?.let {
                {
                    Text(
                        text = it,
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.tertiary
                    )
                }
            },
            singleLine = singleLine,
            maxLines = maxLines,
            isError = isError,
            enabled = enabled,
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = "leading icon",
                        tint = Theme.colors.shade.tertiary
                    )
                }
            },
            trailingIcon = {
                when {
                    isPassword -> {
                        val image =
                            if (passwordVisible) painterResource(R.drawable.eye_opened) else painterResource(
                                R.drawable.eye_closed
                            )
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(painter = image, contentDescription = "Toggle password visibility")
                        }
                    }
                    isError -> {
                        Icon(
                            painter = painterResource(R.drawable.outline_danger_triangle),
                            contentDescription = "Error",
                            tint = Theme.colors.additional.primary.red
                        )
                    }
                    else -> trailingIcon?.invoke()
                }
            },
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(Theme.radius.large),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Theme.colors.shade.primary,
                focusedBorderColor = Theme.colors.brand.primary,
                unfocusedBorderColor = Theme.colors.stroke.primary,
                errorBorderColor = Theme.colors.additional.primary.red,
                errorTextColor = Theme.colors.shade.primary,
                cursorColor = Theme.colors.brand.primary,
                errorCursorColor = Color.Transparent,
            )
        )
        if (isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
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
                    modifier = Modifier.clickable {
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
            leadingIcon = R.drawable.due_tone_profile
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
            leadingIcon = R.drawable.due_tone_profile
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
            leadingIcon = R.drawable.due_tone_profile
        )
    }
}
