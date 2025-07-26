package com.paris_2.san3a.presentation.navigation


import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Serializable
sealed interface SearchDestination

@OptIn(InternalSerializationApi::class)
fun SearchDestination.toJson(): String =
    Json.encodeToString(SearchDestination::class.serializer(), this)

fun String.fromJsonToSearchDestination(): SearchDestination =
    Json.decodeFromString(this)