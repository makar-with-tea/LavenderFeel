package ru.hse.lavenderfeel.data

import android.os.Build
import androidx.annotation.RequiresApi
import ru.hse.lavenderfeel.domain.Avatar
import ru.hse.lavenderfeel.domain.DailyEntry
import java.time.LocalDate

object InMemoryStorage {

    var avatar: Avatar? = null
    private val dailyEntries: MutableMap<LocalDate, DailyEntry> = mutableMapOf()

    fun saveAvatar(avatar: Avatar) {
        this.avatar = avatar
    }

    fun saveDailyEntry(entry: DailyEntry) {
        val date = entry.date
        dailyEntries[date] = entry
    }

    fun getDailyEntry(date: LocalDate): DailyEntry? =
        dailyEntries[date]

    fun getAllDailyEntries(): List<DailyEntry> =
        dailyEntries.values.sortedByDescending { it.date }
}