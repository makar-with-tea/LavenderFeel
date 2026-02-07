package ru.hse.lavenderfeel.ui.dayreport

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.hse.lavenderfeel.data.DataModule
import ru.hse.lavenderfeel.domain.DailyEntry
import ru.hse.lavenderfeel.domain.EmotionType
import ru.hse.lavenderfeel.domain.FaceColor
import ru.hse.lavenderfeel.domain.NegativeHabits
import ru.hse.lavenderfeel.domain.PositiveHabits
import ru.hse.lavenderfeel.ui.DayColor
import ru.hse.lavenderfeel.ui.Emotion
import java.time.LocalDate

class DayReportViewModel(
    val date: LocalDate,
): ViewModel() {
    var isLoading by mutableStateOf(true)
    var mood by mutableStateOf(Emotion.NONE)
    var dayColor by mutableStateOf(DayColor.PURPLE)
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

    fun loadData() {
        // todo да
        val dailyEntity = DataModule.dailyEntryService.getEntryForDate(date)
        mood =
            when(dailyEntity!!.emotion){
                EmotionType.emotion_sad -> Emotion.SAD
                EmotionType.emotion_angry -> Emotion.ANGRY
                EmotionType.emotion_happy -> Emotion.HAPPY
                EmotionType.emotion_smile -> Emotion.NEUTRAL
                EmotionType.emotion_neutral -> Emotion.NEUTRAL
            }

        dayColor =
            when(dailyEntity.faceColor){
                FaceColor.RED -> DayColor.RED
                FaceColor.ORANGE -> DayColor.ORANGE
                FaceColor.YELLOW -> DayColor.YELLOW
                FaceColor.GREEN -> DayColor.GREEN
                FaceColor.LIGHTBLUE -> DayColor.LIGHT_BLUE
                FaceColor.DARKBLUE -> DayColor.BLUE
                FaceColor.PURPLE -> DayColor.PURPLE
                FaceColor.PINK -> DayColor.PINK
            }
        description = dailyEntity.note
        val positiveHabits = dailyEntity.positiveHabits
        positiveChecks = mapOf(
                "ateWell" to positiveHabits.food,
                "drankWater" to positiveHabits.water,
                "sleptWell" to positiveHabits.sleep
            )
        val negativeHabits = dailyEntity.negativeHabits
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
        //todo да
        val emotionType = when (mood) {
            Emotion.SAD -> EmotionType.emotion_sad
            Emotion.ANGRY -> EmotionType.emotion_angry
            Emotion.HAPPY -> EmotionType.emotion_happy
            Emotion.NEUTRAL -> EmotionType.emotion_neutral
            else -> EmotionType.emotion_neutral
        }

        val faceColorEnum = when (dayColor) {
            DayColor.RED -> FaceColor.RED
            DayColor.ORANGE -> FaceColor.ORANGE
            DayColor.YELLOW -> FaceColor.YELLOW
            DayColor.GREEN -> FaceColor.GREEN
            DayColor.LIGHT_BLUE -> FaceColor.LIGHTBLUE
            DayColor.BLUE -> FaceColor.DARKBLUE
            DayColor.PURPLE -> FaceColor.PURPLE
            DayColor.PINK -> FaceColor.PINK
        }

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
