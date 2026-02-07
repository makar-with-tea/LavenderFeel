package ru.hse.lavenderfeel.ui.calendar

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.lavenderfeel.data.DataModule
import ru.hse.lavenderfeel.ui.CalendarDay
import ru.hse.lavenderfeel.ui.getName
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class CalendarViewModel : ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var currentMonth: YearMonth by mutableStateOf(YearMonth.now())
        private set

    val monthName: String
        get() = currentMonth.month.getName() + " " + currentMonth.year

    val daysOfWeek: List<String> = DayOfWeek.entries
        .map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }

    var days: List<CalendarDay> by mutableStateOf(emptyList())
        private set

    init {
        loadMonthData()
    }

    fun loadMonthData() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            val firstDayOfMonth = currentMonth.atDay(1)
            val lastDayOfMonth = currentMonth.atEndOfMonth()
            val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
            val totalDays = firstDayOfWeek + lastDayOfMonth.dayOfMonth
            val weeks = (totalDays + 6) / 7
            val dayList = mutableListOf<CalendarDay>()
            var dayCounter = 1 - firstDayOfWeek
            val daysEmotions = DataModule.dailyEntryService.getHistory()
                .associateBy { it.date }
            Log.i("marathinks", "${currentMonth.month}: ${daysEmotions}")
            for (w in 0 until weeks) {
                for (d in 0..6) {
                    val date = currentMonth.atDay(1).plusDays((dayCounter++).toLong())
                    dayList.add(
                        CalendarDay(
                            date = date,
                            isCurrentMonth = date.month == currentMonth.month,
                            emotion = daysEmotions[date]?.emotion,
                            isToday = date == LocalDate.now()
                        )
                    )
                }
            }
            withContext(Dispatchers.Main) {
                days = dayList
                isLoading = false
            }
        }
    }

    fun prevMonth() {
        currentMonth = currentMonth.minusMonths(1)
        loadMonthData()
    }

    fun nextMonth() {
        currentMonth = currentMonth.plusMonths(1)
        loadMonthData()
    }

    fun init() {
        DataModule.dailyEntryService.getEntryForDate(LocalDate.now())
    }
}