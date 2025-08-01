package com.paris_2.san3a.presentation.screen.account.components

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun LocationWebView(
    modifier: Modifier = Modifier,
    initLan: Double = 30.0444,
    initLong: Double = 31.2357,
) {

    var aLatitude by remember { mutableDoubleStateOf(initLan) }
    var aLongitude by remember { mutableDoubleStateOf(initLong) }

    val markerGeoPoint = rememberMarkerState(
        geoPoint = GeoPoint(aLatitude, aLongitude)
    )
    val cameraState = rememberCameraState {
        geoPoint = markerGeoPoint.geoPoint
        zoom = 15.0
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
            markerGeoPoint.geoPoint = geoPoint
            Log.d(
                "TAG",
                "LocationWebView:on click || ${geoPoint.latitude} + ${geoPoint.longitude} "
            )
            Log.d(
                "TAG",
                "LocationWebView: On Long click ||  ${markerGeoPoint.geoPoint.latitude} + ${markerGeoPoint.geoPoint.longitude} "
            )
        }
    ) {
        Marker(
            state =
                markerGeoPoint,
        )
    }

}