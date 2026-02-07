package ru.hse.lavenderfeel.ui
import java.time.LocalDate

data class AvatarLayer(
    val resId: Int,
    val description: String? = null,
)

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val color: EmotionColor,
    val isToday: Boolean = false,
)

enum class EmotionColor {
    VERY_SAD, SAD, NEUTRAL, HAPPY, VERY_HAPPY, NONE
}

enum class CustomizationCategory(val displayName: String) {
    CLOTHES("Одежда"),
    EYES("Глаза"),
    ACCESSORIES("Аксессуары")
}