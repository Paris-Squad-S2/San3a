package com.paris_2.san3a.presentation.shared.utils

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Preview(
    name = "Phone",
    group = "devices",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480"
)
@Preview(
    name = "Arabic",
    locale = "ar",
    group = "devices",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480"
)

@Preview(
    name = "Foldable",
    group = "devices",
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480"
)

@Preview(
    name = "Tablet",
    group = "devices",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480"
)

@PreviewLightDark
annotation class PreviewMultiDevices
