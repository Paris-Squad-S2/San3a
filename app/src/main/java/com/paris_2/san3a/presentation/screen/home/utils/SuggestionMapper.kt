package com.paris_2.san3a.presentation.screen.home.utils

fun getSuggestions(serviceType: String): List<String> {
    return when(serviceType){
        "Plumping" -> listOf("Shower is not working", "Bathroom faucet is broken", "Drain Cleaning", "Water Heater Installation")
        "Electrical Services" -> listOf("Wiring", "Circuit Breaker Repair", "Lighting Installation", "Appliance Repair")
        "Carpentry" -> listOf("Furniture Assembly", "Cabinet Installation", "Door Repair", "Custom Shelving")
        "Cleaning" -> listOf("House Cleaning", "Office Cleaning", "Carpet Cleaning", "Window Washing")
        "Painting" -> listOf("Wall Painting", "Ceiling Painting", "Fence Painting", "Touch-Up Painting")
        "AC Repair" -> listOf("Filter Replacement", "Coolant Refill", "Thermostat Repair", "Duct Cleaning")
        "Landscaping" -> listOf("Lawn Mowing", "Tree Trimming", "Garden Design", "Irrigation System Installation")
        else -> emptyList()
    }
}