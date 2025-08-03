package com.paris_2.san3a.presentation.shared.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone light",
    group = "devices",
    device = "spec:width=360dp,height=640dp,dpi=480"
)
@Preview(
    name = "Phone dark",
    group = "devices",
    device = "spec:width=360dp,height=640dp,dpi=480",
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Phone landscape light",
    group = "devices",
    device = "spec:width=640dp,height=360dp,dpi=480"
)
@Preview(
    name = "Arabic light",
    locale = "ar",
    group = "devices",
    device = "spec:width=360dp,height=640dp,dpi=480"
)
@Preview(
    name = "Arabic dark",
    locale = "ar",
    group = "devices",
    device = "spec:width=360dp,height=640dp,dpi=480",
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Foldable",
    group = "devices",
    device = "spec:width=673dp,height=841dp,dpi=480"
)
@Preview(
    name = "Tablet",
    group = "devices",
    device = "spec:width=1280dp,height=800dp,dpi=480"
)

annotation class PreviewMultiDevices
