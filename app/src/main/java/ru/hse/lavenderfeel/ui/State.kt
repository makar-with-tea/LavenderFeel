package ru.hse.lavenderfeel.ui
import ru.hse.lavenderfeel.domain.Emotion
import java.time.LocalDate

data class AvatarLayer(
    val resId: Int,
    val description: String? = null,
)

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val emotion: Emotion?,
    val isToday: Boolean = false,
)

enum class CustomizationCategory(val displayName: String) {
    CLOTHES("Одежда"),
    EYES("Глаза"),
    ACCESSORIES("Аксессуары")
}
