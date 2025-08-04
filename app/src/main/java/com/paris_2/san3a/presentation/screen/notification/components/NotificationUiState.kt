package com.paris_2.san3a.presentation.screen.notification.components


import com.paris_2.san3a.domain.entity.Notification
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
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
    val date: LocalDate,
    val userId: String,
)
@OptIn(ExperimentalTime::class)
fun Notification.toUiModel(): NotificationUiModel {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    return NotificationUiModel(
        id = id,
        title = title,
        caption = caption,
        date = runCatching { LocalDate.parse(date.toString()) }.getOrDefault(currentDate),
        userId = userId
    )
}

fun List<Notification>.toUiModelList(): List<NotificationUiModel> =
    map { it.toUiModel() }