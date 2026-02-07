package ru.hse.lavenderfeel.ui

import android.content.Context
import androidx.compose.ui.graphics.Color
import ru.hse.lavenderfeel.domain.Emotion
import ru.hse.lavenderfeel.domain.FaceColor
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

fun Emotion?.toColor(): Color {
    return when (this) {
        Emotion.ANGRY -> Color(0xFFF17070)
        Emotion.SAD -> Color(0xFF5AADFF)
        Emotion.NEUTRAL -> Color(0xFFB991FF)
        Emotion.HAPPY -> Color(0xFFFFE376)
        null -> Color(0xFF757575).copy(alpha = 0.2f)
    }
}

fun FaceColor.toColor(): Color {
    return when (this) {
        FaceColor.DARKBLUE -> Color(0xFFAACFFF)
        FaceColor.GREEN -> Color(0xFFC3FFA2)
        FaceColor.LIGHTBLUE -> Color(0xFFAAFFED)
        FaceColor.ORANGE -> Color(0xFFFFB871)
        FaceColor.PINK -> Color(0xFFF48EC1)
        FaceColor.PURPLE -> Color(0xFFB8B3FF)
        FaceColor.RED -> Color(0xFFFF8B78)
        FaceColor.YELLOW -> Color(0xFFFFE1A4)
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