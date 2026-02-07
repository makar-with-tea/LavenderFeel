package ru.hse.lavenderfeel.data

import ru.hse.lavenderfeel.domain.AccessoryType
import ru.hse.lavenderfeel.domain.Avatar
import ru.hse.lavenderfeel.domain.ClothesType

class AvatarService(
    private val avatarRepository: AvatarRepository
) {

    fun getAvatar(): Avatar =
        avatarRepository.getAvatar() ?: Avatar.default()

    fun updateAvatar(
        clothes: ClothesType,
        accessory: AccessoryType,
        eyecontact: Boolean,
        isnarrowed: Boolean
    ): Avatar {
        val updated = Avatar(
            clothes = clothes,
            accessory = accessory,
            eyecontact = eyecontact,
            isnarrowed = isnarrowed
        )
        avatarRepository.saveAvatar(updated)
        return updated
    }
}