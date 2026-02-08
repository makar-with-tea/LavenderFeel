package ru.hse.lavenderfeel.data

import kotlinx.coroutines.flow.MutableStateFlow
import ru.hse.lavenderfeel.domain.DailyEntry
import ru.hse.lavenderfeel.domain.Emotion
import ru.hse.lavenderfeel.domain.FaceColor
import ru.hse.lavenderfeel.domain.NegativeHabits
import ru.hse.lavenderfeel.domain.PositiveHabits
import java.time.LocalDate
import java.time.Month

class DailyEntryService(
    private val dailyEntryRepository: DailyEntryRepository
) {
    private val _currentEntry: MutableStateFlow<DailyEntry?> = MutableStateFlow(null)
    val currentEntry: MutableStateFlow<DailyEntry?> = _currentEntry
    fun saveDailyEntry(
        date: String,
        note: String,
        emotion: Emotion,
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
        _currentEntry.value = entry
        return entry
    }

    fun getEntryForDate(date: String): DailyEntry? {
        return dailyEntryRepository.getByDate(date).also {
            _currentEntry.value = it
        }
    }

    fun getHistory(): List<DailyEntry> =
        dailyEntryRepository.getAll()
}