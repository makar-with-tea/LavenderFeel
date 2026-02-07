package ru.hse.lavenderfeel.ui.avatar

import androidx.compose.runtime.mutableStateListOf
import ru.hse.lavenderfeel.R
import ru.hse.lavenderfeel.ui.AvatarLayer

class AvatarViewModel {
    private var _avatarLayers = mutableStateListOf(
        AvatarLayer(resId = R.drawable.clothes_suit_big, description = "Костюм большой"),
        AvatarLayer(resId = R.drawable.face_red),
        AvatarLayer(resId = R.drawable.eye_contact_base, description = "Обычные глаза"),
        AvatarLayer(resId = R.drawable.emotion_smile),
    )
    val avatarLayers: List<AvatarLayer> = _avatarLayers

    fun addOrDeleteLayer(layer: AvatarLayer) {
        if (_avatarLayers.contains(layer)) {
            _avatarLayers.remove(layer)
        } else {
            _avatarLayers.add(layer)
        }
    }
}