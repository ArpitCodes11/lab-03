package com.example.listycity3

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import kotlin.collections.lastIndex
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.clickable

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    // It gets the action used to the edited city.
    onUpdateCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<City?>(null) }  // stores the city being edited

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    // clears the current edit when user presses "+" button
                    if (selectedCity != null) {
                        selectedCity = null
                        newCityName = ""
                        newProvinceName = ""
                    }
                    showAddCityFields = !showAddCityFields
                }
            ) {
                Text("+")
            }
        }

        if (showAddCityFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {

                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            // Creates a new city
                            val updatedCity = City(
                                name = newCityName,
                                province = newProvinceName
                            )
                            val oldCity = selectedCity
// add a city if none is selected otherwise save th e edit
                            if (oldCity == null) {
                                onAddCity(updatedCity)
                            } else {
                                onUpdateCity(oldCity, updatedCity)
                            }
// we finish editing, clear fields and hide them
                            selectedCity = null
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                        }
                    }
                )
                {// show  the button for adding or editing
                    if (selectedCity == null) {
                        Text("Add City")
                    } else {
                        Text("Save Changes")
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    onClick = {
                        // Selects the city and show its value
                        selectedCity = city
                        newCityName = city.name
                        newProvinceName = city.province
                        showAddCityFields = true
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
// runs the action when we tap the city row
fun CityRow(city: City, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            // gives an update action.
            onUpdateCity = { _, _ -> }
        )
    }
}
