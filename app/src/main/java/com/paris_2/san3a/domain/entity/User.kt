package com.paris_2.san3a.domain.entity

data class User(
    val id: String,
    val phone: String,
    val fullName: String,
    val profilePhoto: String,
    val nationalIdFrontImage: String,
    val nationalIdBackImage: String,
    val offeredServices: List<String>,
    val workMedia: List<String>,
    val workDescription: List<Service>,
    val requestedServices: List<Service>,
    val accountType: AccountType
    // val location: String, todo implement the location
)

enum class AccountType {
    CUSTOMER,
    CRAFTSMAN
}