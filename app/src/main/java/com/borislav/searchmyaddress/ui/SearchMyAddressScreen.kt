package com.borislav.searchmyaddress.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

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
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState {
                /*position = LatLng(48.8566, 2.3522) */ // Default to Paris, adjust as needed
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            SearchBar()
            // Here, you will eventually add the list of address suggestions
        }
    }
}

@Composable
fun SearchBar(
    value: TextFieldValue = remember { TextFieldValue() },
    onValueChange: (TextFieldValue) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = androidx.compose.ui.graphics.RectangleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchMyAddressContentPreview() {
    SearchMyAddressScreen()
}