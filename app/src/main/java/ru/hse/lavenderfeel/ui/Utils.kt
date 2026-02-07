package ru.hse.lavenderfeel.ui

import android.content.Context
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

fun Boolean?.orFalse(): Boolean = this ?: false

fun Int?.orZero(): Int = this ?: 0

fun <T> T?.orDefault(default: T): T = this ?: default

fun rawResId(name: String, context: Context): Int {
    return context.resources.getIdentifier(name, "raw", context.packageName)
}

fun Emotion.toColor(): Color {
    return when (this) {
        Emotion.ANGRY -> Color(0xFFF17070)
        Emotion.SAD -> Color(0xFF5AADFF)
        Emotion.NEUTRAL -> Color(0xFFB991FF)
        Emotion.HAPPY -> Color(0xFFFFE376)
        Emotion.NONE -> Color(0xFF757575).copy(alpha = 0.2f)
    }
}

fun DayColor.toColor(): Color {
    return when (this) {
        DayColor.BLUE -> Color(0xFFAACFFF)
        DayColor.GREEN -> Color(0xFFC3FFA2)
        DayColor.LIGHT_BLUE -> Color(0xFFAAFFED)
        DayColor.ORANGE -> Color(0xFFFFB871)
        DayColor.PINK -> Color(0xFFF48EC1)
        DayColor.PURPLE -> Color(0xFFB8B3FF)
        DayColor.RED -> Color(0xFFFF8B78)
        DayColor.YELLOW -> Color(0xFFFFE1A4)
    }
}

private fun Emotion.toAvatarResId(): Int {
    return when (this) {
        Emotion.SAD -> ru.hse.lavenderfeel.R.drawable.emotion_sad
        Emotion.ANGRY -> ru.hse.lavenderfeel.R.drawable.emotion_angry
        Emotion.NEUTRAL -> ru.hse.lavenderfeel.R.drawable.emotion_neutral
        Emotion.HAPPY -> ru.hse.lavenderfeel.R.drawable.emotion_happy
        else -> ru.hse.lavenderfeel.R.drawable.emotion_neutral
    }
}

fun Month.getName() = this.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

fun LocalDate.getDdMmYyyy(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.monthValue.toString().padStart(2, '0')
    val year = this.year.toString()
    return "$day.$month.$year"
}