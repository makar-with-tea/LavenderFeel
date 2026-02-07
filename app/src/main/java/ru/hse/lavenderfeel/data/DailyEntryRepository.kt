package ru.hse.lavenderfeel.data

import ru.hse.lavenderfeel.domain.DailyEntry
import java.time.LocalDate

class DailyEntryRepository {

    fun getByDate(date: LocalDate): DailyEntry? =
        SharedPreferencesStorage.getDailyEntry(date)

    fun save(entry: DailyEntry) {
        SharedPreferencesStorage.saveDailyEntry(entry)
    }

    fun getAll(): List<DailyEntry> =
        SharedPreferencesStorage.getAllDailyEntries()
}