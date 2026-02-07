package ru.hse.lavenderfeel.data

import ru.hse.lavenderfeel.domain.DailyEntry
import ru.hse.lavenderfeel.domain.EmotionType
import ru.hse.lavenderfeel.domain.FaceColor
import ru.hse.lavenderfeel.domain.NegativeHabits
import ru.hse.lavenderfeel.domain.PositiveHabits
import java.time.LocalDate

class DailyEntryService(
    private val dailyEntryRepository: DailyEntryRepository
) {

    fun saveDailyEntry(
        date: LocalDate,
        note: String,
        emotion: EmotionType,
        faceColor: FaceColor,
        positive: PositiveHabits,
        negative: NegativeHabits
    ): DailyEntry {

        val entry = DailyEntry(
            date = date,
            note = note,
            emotion = emotion,
            faceColor = faceColor,
            positiveHabits = positive,
            negativeHabits = negative
        )

        dailyEntryRepository.save(entry)
        return entry
    }

    fun getEntryForDate(date: LocalDate): DailyEntry? =
        dailyEntryRepository.getByDate(date)

    fun getHistory(): List<DailyEntry> =
        dailyEntryRepository.getAll()
}