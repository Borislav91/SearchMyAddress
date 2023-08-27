package com.borislav.searchmyaddress.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.borislav.searchmyaddress.domain.model.Address
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.borislav.searchmyaddress.R
import com.google.maps.android.compose.MarkerInfoWindow
import timber.log.Timber

@Composable
fun SearchMyAddressScreen() {
    val viewModel = hiltViewModel<SearchMyAddressViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchMyAddressContent(
        state = state,
        action = viewModel.submitAction
    )
}

@Composable
fun SearchMyAddressContent(
    state: SearchMyAddressState,
    action: (SearchMyAddressAction) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val defaultLatLng = LatLng(48.8566, 2.3522)  // Default coordinates for Paris
        val targetLatLng = state.selectedAddress?.let {
            LatLng(it.latitude, it.longitude)
        } ?: defaultLatLng

        val markerState = rememberMarkerState(position = targetLatLng)
        val context = LocalContext.current
        val icon = bitmapDescriptorFromVector(context = context, R.drawable.ic_pin_marker)


        val cameraPosition = if (state.selectedAddress != null) {
            CameraPosition(targetLatLng, 15f, 0f, 0f)
        } else {
            CameraPosition(defaultLatLng, 10f, 0f, 0f)
        }

        val cameraState = rememberCameraPositionState()
        cameraState.position = cameraPosition

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraState
        ) {
            state.selectedAddress?.let { address ->
                val markerTitle = if (address.latitude == 48.8566 && address.longitude == 2.3522) {
                    "Paris - default address"
                } else {
                    address.streetName.ifEmpty { "No Street Name Available" }
                }
                MarkerInfoWindow(
                    state = rememberMarkerState(position = LatLng(address.latitude, address.longitude)),
                    title = formatFrenchAddress(address),
                    visible = true,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                )

            }
        }



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            SearchBar(action)
            // Only show dropdown if no address is selected
            if (state.isAddressSelected.not()) {
                AddressDropdown(state.searchResults, action)
            }
        }
    }
}




@Composable
fun SearchBar(
    action: (SearchMyAddressAction) -> Unit
) {
    var value by remember { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RectangleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.text != value.text) {
                    action(SearchMyAddressAction.ResetSelectedAddress)
                }
                Timber.tag("SearchMyAddressScreen").d("SearchBar - Entered text: ${newValue.text}")
                value = newValue
                action(SearchMyAddressAction.Search(newValue.text))
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AddressDropdown(results: List<Address>, action: (SearchMyAddressAction) -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().background(Color.White)
    ) {
        Column {
            results.forEachIndexed { index, address ->
                AddressItem(address, action)

                // Add a divider line between addresses but not after the last one
                if (index < results.size - 1) {
                    Divider()
                }
            }
        }
    }
    Timber.tag("SearchMyAddressScreen").d("AddressDropDown - List of Addresses: ${results.joinToString { it.streetName }}")
}

@Composable
fun AddressItem(address: Address, action: (SearchMyAddressAction) -> Unit) {
    Text(
        text = formatFrenchAddress(address), // use the formatFrenchAddress here
        modifier = Modifier
            .fillMaxWidth()
            .clickable { action(SearchMyAddressAction.SelectAddress(address)) }
            .padding(12.dp)
    )
}


@Composable
fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {
    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

fun formatFrenchAddress(address: Address): String {
    // Handling optional house number
    val houseNumberPart = address.houseNumber?.let { "$it, " } ?: ""
    return "$houseNumberPart${address.streetName}, ${address.postalCode} ${address.city}"
}


@Preview(showBackground = true)
@Composable
fun SearchMyAddressContentPreview() {
    SearchMyAddressScreen()
}