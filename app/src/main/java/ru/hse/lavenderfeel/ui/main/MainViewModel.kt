package ru.hse.lavenderfeel.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import ru.hse.lavenderfeel.R
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import java.util.Locale.getDefault

class MainViewModel : ViewModel() {
    val avatarLayers = mutableStateListOf(
        AvatarLayer(resId = R.drawable.suit_big),
        AvatarLayer(resId = R.drawable.face_red),
        AvatarLayer(resId = R.drawable.eye_contact_base),
        AvatarLayer(resId = R.drawable.emotion_smile),
    )

    var currentMonth: YearMonth by mutableStateOf(YearMonth.now())
        private set

    val monthName: String
        get() = currentMonth.month.getDisplayName(TextStyle.FULL_STANDALONE, getDefault())
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() } + " " + currentMonth.year

    val daysOfWeek: List<String> = DayOfWeek.entries
        .map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }

    val days: List<CalendarDay>
        get() {
            val firstDayOfMonth = currentMonth.atDay(1)
            val lastDayOfMonth = currentMonth.atEndOfMonth()
            val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
            val totalDays = firstDayOfWeek + lastDayOfMonth.dayOfMonth
            val weeks = (totalDays + 6) / 7
            val days = mutableListOf<CalendarDay>()
            var dayCounter = 1 - firstDayOfWeek
            for (w in 0 until weeks) {
                for (d in 0..6) {
                    val date = currentMonth.atDay(1).plusDays((dayCounter++).toLong())
                    days.add(
                        CalendarDay(
                            date = date,
                            isCurrentMonth = date.month == currentMonth.month,
                            color = EmotionColor.entries[d % EmotionColor.entries.size],
                        )
                    )
                }
            }
            return days
        }

    fun prevMonth() {
        currentMonth = currentMonth.minusMonths(1)
    }

    fun nextMonth() {
        currentMonth = currentMonth.plusMonths(1)
    }
}

