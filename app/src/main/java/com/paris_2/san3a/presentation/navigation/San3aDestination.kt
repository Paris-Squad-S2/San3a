package com.paris_2.san3a.presentation.navigation


import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface San3aDestination

@OptIn(InternalSerializationApi::class)
fun San3aDestination.toJson(): String =
    Json.encodeToString(San3aDestination::class.serializer(), this)

fun String.fromJsonToSearchDestination(): San3aDestination =
    Json.decodeFromString(this)