package ru.hse.lavenderfeel.ui.avatar

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.lavenderfeel.R
import ru.hse.lavenderfeel.ui.AvatarLayer

class AvatarViewModel: ViewModel() {
    private var _avatarLayers: MutableStateFlow<List<AvatarLayer>> = MutableStateFlow(emptyList())
    val avatarLayers: StateFlow<List<AvatarLayer>> = _avatarLayers

    fun loadInitialLayers() {
        viewModelScope.launch(Dispatchers.IO) {
            // todo класс лизы
            delay(1000) // simulate loading
            val mockLayersStateFlow = MutableStateFlow(
                listOf(
                    AvatarLayer(resId = R.drawable.clothes_suit_big),
                    AvatarLayer(resId = R.drawable.face_red),
                    AvatarLayer(resId = R.drawable.eye_contact_base, description = "Обычные глаза"),
                    AvatarLayer(resId = R.drawable.emotion_smile),
                )
            )
            mockLayersStateFlow.collect {
                _avatarLayers.value = it
            }
        }
    }
}
