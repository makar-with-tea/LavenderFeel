package ru.hse.lavenderfeel.ui
import java.time.LocalDate

data class AvatarLayer(
    val resId: Int,
    val description: String? = null,
)

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val emotion: Emotion,
    val isToday: Boolean = false,
)

enum class Emotion {
    SAD, ANGRY, NEUTRAL, HAPPY, NONE
}

enum class DayColor {
    BLUE, GREEN, LIGHT_BLUE, ORANGE, PINK, PURPLE, RED, YELLOW
}

enum class CustomizationCategory(val displayName: String) {
    CLOTHES("Одежда"),
    EYES("Глаза"),
    ACCESSORIES("Аксессуары")
}
