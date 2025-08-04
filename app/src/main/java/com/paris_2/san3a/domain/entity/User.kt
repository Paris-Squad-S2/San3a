package com.paris_2.san3a.domain.entity

data class User(
    val id: String,
    val phone: String,
    val fullName: String,
    val profilePhoto: String,
    val rating: Double,
    val review: Int,
    val nationalIdFrontImage: String,
    val nationalIdBackImage: String,
    val workDescription: String,
    val accountType: AccountType,
    val location: Location
)

enum class AccountType {
    CUSTOMER,
    CRAFTSMAN
}