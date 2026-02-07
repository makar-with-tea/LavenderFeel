package ru.hse.lavenderfeel.ui.dayreport

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        // todo класс лизы
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            isLoading = false
        }
    }

    fun setPositiveCheck(key: String, value: Boolean) {
        positiveChecks = positiveChecks.toMutableMap().apply { put(key, value) }
    }

    fun setNegativeCheck(key: String, value: Boolean) {
        negativeChecks = negativeChecks.toMutableMap().apply { put(key, value) }
    }

    fun saveReport() {
        // todo класс лизы
    }
}
