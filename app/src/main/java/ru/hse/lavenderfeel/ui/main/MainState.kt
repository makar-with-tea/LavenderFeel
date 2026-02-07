package ru.hse.lavenderfeel.ui.main
import java.time.LocalDate

data class AvatarLayer(
    val resId: Int,
    val description: String? = null,
)

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val color: EmotionColor,
)

enum class EmotionColor {
    VERY_SAD, SAD, NEUTRAL, HAPPY, VERY_HAPPY, NONE
}
