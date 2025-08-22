package com.paris_2.san3a.domain.entity

@Suppress("UNCHECKED_CAST")
data class User(
    val id: String,
    val phone: String,
    val fullName: String,
    val profilePhoto: String,
    val nationalIdFrontImage: String,
    val nationalIdBackImage: String,
    val workDescription: String,
    val accountType: AccountType,
    val location: Location
){
    companion object {
        fun fromJson(data: Map<String, Any>, id: String): User {
            return User(
                id = id,
                phone = id,
                fullName = data["fullName"]?.toString() ?: "",
                profilePhoto = data["profilePhoto"]?.toString() ?: "https://firebasestorage.googleapis.com/v0/b/cell-monitor21.appspot.com/o/fake%20user%20Img%20don't%20delete%20it.png?alt=media&token=d226452f-3e87-44ef-8a7a-e5e7649fee9f",
                nationalIdFrontImage = data["nationalIdFrontImage"]?.toString() ?: "",
                nationalIdBackImage = data["nationalIdBackImage"]?.toString() ?: "",
                workDescription = data["workDescription"]?.toString() ?: "",
                accountType = AccountType.entries.find {
                    it.name == data["accountType"]?.toString()
                } ?: AccountType.CUSTOMER,
                location = Location.fromJson(data["location"] as? Map<String, Any> ?: emptyMap())
            )
        }
    }
}

enum class AccountType {
    CUSTOMER,
    CRAFTSMAN
}