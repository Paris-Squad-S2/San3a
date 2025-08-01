package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.utsman.osmandcompose.MapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
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
        modifier = modifier.size(200.dp),
        cameraState = cameraState,
        properties = MapProperties(
            zoomButtonVisibility = ZoomButtonVisibility.NEVER,
            isAnimating = false,
            isFlingEnable = false,
            isTilesScaledToDpi = false,
            isUseDataConnection = false,
            isEnableRotationGesture = false,
        ),
        onMapClick = { geoPoint ->
            cameraState.geoPoint = GeoPoint(geoPoint.latitude, geoPoint.longitude)
        }
    ) {
        Marker(
            state =
                rememberMarkerState(geoPoint = GeoPoint(latitude, longitude)),

            )
    }
}