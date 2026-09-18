package com.example.listycity3

import androidx.compose.runtime.mutableStateListOf

class CityRepository {
    private val _cities = mutableStateListOf(
        City("Edmonton", "AB"),
        City("Vancouver", "BC"),
        City("Toronto", "ON")
    )

    val cities: List<City>
        get() = _cities

    fun addCity(city: City) {
        _cities.add(city)
    }

// Here we replace the selected city with a new edited city
    fun updateCity(oldCity: City, updatedCity: City) {
        // we find the index of the selected city in the list
        val index = _cities.indexOf(oldCity)
        // replaces the city if it was found
        if (index != -1) {
            _cities[index] = updatedCity
        }
    }
}
