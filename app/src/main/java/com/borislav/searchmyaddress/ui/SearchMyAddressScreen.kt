package com.borislav.searchmyaddress.ui

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.borislav.searchmyaddress.domain.model.Address
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
                // Default to Paris, adjust as needed
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            SearchBar(action)
            AddressDropdown(state.searchResults, action)
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
                value = newValue
                if (newValue.text.length >= 3) {
                    action(SearchMyAddressAction.Search(newValue.text))
                }
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
}

@Composable
fun AddressItem(address: Address, action: (SearchMyAddressAction) -> Unit) {
    Text(
        text = address.streetName,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { action(SearchMyAddressAction.SelectAddress(address)) }
            .padding(12.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SearchMyAddressContentPreview() {
    SearchMyAddressScreen()
}