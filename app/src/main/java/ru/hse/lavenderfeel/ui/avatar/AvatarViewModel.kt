package ru.hse.lavenderfeel.ui.avatar

import androidx.compose.runtime.mutableStateListOf
import ru.hse.lavenderfeel.R
import ru.hse.lavenderfeel.ui.AvatarLayer

class AvatarViewModel {
    val avatarLayers = mutableStateListOf(
        AvatarLayer(resId = R.drawable.clothes_suit_big),
        AvatarLayer(resId = R.drawable.face_red),
        AvatarLayer(resId = R.drawable.eye_contact_base),
        AvatarLayer(resId = R.drawable.emotion_smile),
    )

}