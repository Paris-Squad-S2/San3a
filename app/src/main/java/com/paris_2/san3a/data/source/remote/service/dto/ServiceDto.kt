package com.paris_2.san3a.data.source.remote.service.dto

import com.google.firebase.firestore.DocumentId

data class ServiceDto(
    @DocumentId
    val id: String ,
    val title: Map<String, String>,
    val description: Map<String, String>,
    val suggestions: Map<String, List<String>>,
    val imageUrl: String,
    val darkImageUrl: String,
){
    companion object {

        @Suppress("UNCHECKED_CAST")
        fun fromJson(data: Map<String, Any>, id: String): ServiceDto {
            val titleMap = (data["name"] as? Map<*, *>)?.filterKeys { it is String }
                ?.mapValues { it.value as? String ?: "" } as? Map<String, String> ?: emptyMap()

            val descriptionMap = (data["description"] as? Map<*, *>)?.filterKeys { it is String }
                ?.mapValues { it.value as? String ?: "" } as? Map<String, String> ?: emptyMap()

            val imageUrl = data["imgUrl"] as? String ?: ""
            val darkImageUrl = data["darkImgUrl"] as? String ?: ""

            val suggestionsMap = (data["suggestions"] as? Map<*, *>)?.filterKeys { it is String }
                ?.mapValues { (it.value as? List<*>)?.filterIsInstance<String>() ?: emptyList() }
                as? Map<String, List<String>> ?: emptyMap()

            return ServiceDto(
                id = id,
                title = titleMap,
                description = descriptionMap,
                imageUrl = imageUrl,
                darkImageUrl = darkImageUrl,
                suggestions = suggestionsMap
            )
        }
    }
}