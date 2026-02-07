package ru.hse.lavenderfeel.domain

import java.time.LocalDate

data class DailyEntry(
    val date: LocalDate,
    val emotion: EmotionType,
    val faceColor: FaceColor,
    val positiveHabits: PositiveHabits,
    val negativeHabits: NegativeHabits
)