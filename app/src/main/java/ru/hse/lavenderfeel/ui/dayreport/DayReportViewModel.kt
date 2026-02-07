package ru.hse.lavenderfeel.ui.dayreport

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.hse.lavenderfeel.data.DataModule
import ru.hse.lavenderfeel.domain.Emotion
import ru.hse.lavenderfeel.domain.FaceColor
import ru.hse.lavenderfeel.domain.NegativeHabits
import ru.hse.lavenderfeel.domain.PositiveHabits
import java.time.LocalDate

class DayReportViewModel(
    val date: LocalDate,
): ViewModel() {
    var isLoading by mutableStateOf(true)
    var mood by mutableStateOf<Emotion?>(null)
    var dayColor by mutableStateOf<FaceColor?>(null)
    var description by mutableStateOf("")
    var positiveChecks by mutableStateOf(
        mapOf(
            "ateWell" to false,
            "drankWater" to false,
            "sleptWell" to false
        )
    )
    var negativeChecks by mutableStateOf(
        mapOf(
            "smoked" to false,
            "drankAlcohol" to false,
            "selfHarmed" to false
        )
    )

    init {
        val dailyEntity = DataModule.dailyEntryService.getEntryForDate(date)
        Log.i("marathinks", "Loaded daily entry for $date: $dailyEntity")
        mood = dailyEntity?.emotion ?: Emotion.NEUTRAL

        dayColor = dailyEntity?.faceColor ?: FaceColor.PURPLE
        description = dailyEntity?.note ?: ""
        val positiveHabits = dailyEntity?.positiveHabits ?: PositiveHabits(food = false, water = false, sleep = false)
        positiveChecks = mapOf(
                "ateWell" to positiveHabits.food,
                "drankWater" to positiveHabits.water,
                "sleptWell" to positiveHabits.sleep
            )
        val negativeHabits = dailyEntity?.negativeHabits ?: NegativeHabits(smoke = false, alcohol = false, selfharm = false)
        negativeChecks = mapOf(
            "smoked" to negativeHabits.smoke,
            "drankAlcohol" to negativeHabits.alcohol,
            "selfHarmed" to negativeHabits.selfharm
        )

        isLoading = false
    }

    fun setPositiveCheck(key: String, value: Boolean) {
        positiveChecks = positiveChecks.toMutableMap().apply { put(key, value) }
    }

    fun setNegativeCheck(key: String, value: Boolean) {
        negativeChecks = negativeChecks.toMutableMap().apply { put(key, value) }
    }

    fun saveReport() {
        val emotionType = mood ?: Emotion.NEUTRAL
        val faceColorEnum = dayColor ?: FaceColor.PURPLE

        val positiveHabits = PositiveHabits(
            food = positiveChecks["ateWell"] ?: false,
            water = positiveChecks["drankWater"] ?: false,
            sleep = positiveChecks["sleptWell"] ?: false
        )

        val negativeHabits = NegativeHabits(
            smoke = negativeChecks["smoked"] ?: false,
            alcohol = negativeChecks["drankAlcohol"] ?: false,
            selfharm = negativeChecks["selfHarmed"] ?: false
        )

        DataModule.dailyEntryService.saveDailyEntry(
            date = date,
            note = description,
            emotion = emotionType,
            faceColor = faceColorEnum,
            positive = positiveHabits,
            negative = negativeHabits
        )

    }
}
