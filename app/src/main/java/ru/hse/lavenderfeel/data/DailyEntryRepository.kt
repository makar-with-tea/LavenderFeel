package ru.hse.lavenderfeel.data

import ru.hse.lavenderfeel.domain.DailyEntry
import java.time.LocalDate

class DailyEntryRepository {

    fun getByDate(date: LocalDate): DailyEntry? =
        InMemoryStorage.getDailyEntry(date)

    fun save(entry: DailyEntry) {
        InMemoryStorage.saveDailyEntry(entry)
    }

    fun getAll(): List<DailyEntry> =
        InMemoryStorage.getAllDailyEntries()
}