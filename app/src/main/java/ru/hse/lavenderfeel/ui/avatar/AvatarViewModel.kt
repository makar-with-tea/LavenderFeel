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
import ru.hse.lavenderfeel.data.DataModule
import ru.hse.lavenderfeel.ui.AvatarLayer

class AvatarViewModel: ViewModel() {
    private var _avatarLayers: MutableStateFlow<List<AvatarLayer>> = MutableStateFlow(emptyList())
    val avatarLayers: StateFlow<List<AvatarLayer>> = _avatarLayers

    fun loadInitialLayers() {
        viewModelScope.launch(Dispatchers.IO) {
            // todo класс лизы
            delay(1000) // simulate loading
            DataModule.avatarRenderService.currentAvatarLayers.collect {
                _avatarLayers.value = it
            }
        }
    }
}
