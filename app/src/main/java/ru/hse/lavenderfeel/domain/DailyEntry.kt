package ru.hse.lavenderfeel.domain

import java.time.LocalDate

data class DailyEntry(
    val date: LocalDate,
    val note: String = "",
    val emotion: Emotion = Emotion.NEUTRAL,
    val faceColor: FaceColor = FaceColor.PURPLE,
    val positiveHabits: PositiveHabits,
    val negativeHabits: NegativeHabits
)