package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.util.GeoPoint

@Composable
fun LocationWebView(modifier: Modifier = Modifier, longitude: Double, latitude: Double) {

    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(latitude, longitude)
        zoom = 5.0
    }

    OpenStreetMap(
        cameraState = cameraState,
    ) {
        Marker(
            state =
                rememberMarkerState(geoPoint = GeoPoint(latitude, longitude))
        )
    }
}