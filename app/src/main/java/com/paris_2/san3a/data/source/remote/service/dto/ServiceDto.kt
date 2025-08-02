package com.paris_2.san3a.data.source.remote.service.dto

import com.google.firebase.firestore.DocumentId

data class ServiceDto(
    @DocumentId
    val id: String ,
    val title: Map<String, String>,
    val description: Map<String, String>
){
    companion object{
        fun fromJson(data: Map<String, Any>, id: String): ServiceDto {
            val titleMap = (data["name"] as? Map<*, *>)?.mapNotNull {
                val key = it.key as? String
                val value = it.value as? String
                if (key != null && value != null) key to value else null
            }?.toMap() ?: emptyMap()

            val descriptionMap = (data["description"] as? Map<*, *>)?.mapNotNull {
                val key = it.key as? String
                val value = it.value as? String
                if (key != null && value != null) key to value else null
            }?.toMap() ?: emptyMap()

            return ServiceDto(
                id = id,
                title = titleMap,
                description = descriptionMap
            )
        }
    }
}