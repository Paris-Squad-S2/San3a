package com.paris_2.san3a.presentation.screen.notification.components


import com.paris_2.san3a.domain.entity.Notification
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationUiModel> = emptyList(),
    val error: String? = null
)

data class NotificationUiModel(
    val id: String,
    val title: String,
    val caption: String,
    val date: LocalDateTime,
)

@OptIn(ExperimentalTime::class)
fun Notification.toUiModel(): NotificationUiModel {
    return NotificationUiModel(
        id = id,
        title = title,
        caption = caption,
        date = date,
    )
}

fun List<Notification>.toUiModelList(): List<NotificationUiModel> =
    map { it.toUiModel() }