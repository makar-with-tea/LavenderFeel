package ru.hse.lavenderfeel.data

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import ru.hse.lavenderfeel.R
import ru.hse.lavenderfeel.domain.AccessoryType
import ru.hse.lavenderfeel.domain.Avatar
import ru.hse.lavenderfeel.domain.ClothesType
import ru.hse.lavenderfeel.ui.AvatarLayer
import ru.hse.lavenderfeel.ui.CustomizationCategory

class AvatarService(
    private val avatarRepository: AvatarRepository
) {
    private val _currentAvatar: MutableStateFlow<Avatar> = MutableStateFlow(avatarRepository.getAvatar() ?: Avatar.default())
    val currentAvatar: MutableStateFlow<Avatar> = _currentAvatar

    fun getAvatar(): Avatar =
        avatarRepository.getAvatar() ?: Avatar.default()

    fun updateAvatar(
        name: String,
        clothes: ClothesType,
        eyecontact: Boolean,
        isnarrowed: Boolean,
        accessory_hat: Boolean,
        accessory_flower: Boolean,
        accessory_patch: Boolean,
        accessory_freckles: Boolean,
        accessory_mustache: Boolean,
        accessory_beard: Boolean,
        accessory_sweat: Boolean,
        accessory_eyebags: Boolean,
        accessory_eyelashes: Boolean,
        accessory_tear: Boolean
    ): Avatar {
        val updated = Avatar(
            name = name,
            clothes = clothes,
            eyecontact = eyecontact,
            isnarrowed = isnarrowed,
            accessory_hat = accessory_hat,
            accessory_flower = accessory_flower,
            accessory_patch = accessory_patch,
            accessory_freckles = accessory_freckles,
            accessory_mustache = accessory_mustache,
            accessory_beard = accessory_beard,
            accessory_sweat = accessory_sweat,
            accessory_eyebags = accessory_eyebags,
            accessory_eyelashes = accessory_eyelashes,
            accessory_tear = accessory_tear
        )
        avatarRepository.saveAvatar(updated)
        _currentAvatar.value = updated
        return updated
    }
    fun updateName(name: String){
        avatarRepository.setName(name)
        _currentAvatar.value = avatarRepository.getAvatar() ?: Avatar.default()
    }

    fun buildAvatar(layers: List<AvatarLayer>): Avatar {
        var clothes = ClothesType.clothes_tshirt_big
        var eyecontact = true
        var isnarrowed = false
        var accessory_hat = false
        var accessory_flower = false
        var accessory_patch = false
        var accessory_freckles = false
        var accessory_mustache = false
        var accessory_beard = false
        var accessory_sweat = false
        var accessory_eyebags = false
        var accessory_eyelashes = false
        var accessory_tear = false
        for (layer in layers) {
            if (layer.resId == R.drawable.clothes_suit_big) clothes = ClothesType.clothes_suit_big
            if (layer.resId == R.drawable.clothes_suit_small) clothes = ClothesType.clothes_suit_small
            if (layer.resId == R.drawable.clothes_tshirt_big) clothes = ClothesType.clothes_tshirt_big
            if (layer.resId == R.drawable.clothes_tshirt_small) clothes = ClothesType.clothes_tshirt_small
            if (layer.resId == R.drawable.clothes_sweater_big) clothes = ClothesType.clothes_sweater_big
            if (layer.resId == R.drawable.clothes_sweater_small) clothes = ClothesType.clothes_sweater_small
            if (layer.resId == R.drawable.eye_contact_base){
                isnarrowed = false
                eyecontact = true
            }
            if (layer.resId == R.drawable.eye_contact_squint){
                isnarrowed = true
                eyecontact = true
            }
            if (layer.resId == R.drawable.accessory_hat) accessory_hat = true
            if (layer.resId == R.drawable.accessory_flower) accessory_flower = true
            if (layer.resId == R.drawable.accessory_patch) accessory_patch = true
            if (layer.resId == R.drawable.accessory_freckles) accessory_freckles = true
            if (layer.resId == R.drawable.accessory_mustache) accessory_mustache = true
            if (layer.resId == R.drawable.accessory_beard) accessory_beard = true
            if (layer.resId == R.drawable.accessory_sweat) accessory_sweat = true
            if (layer.resId == R.drawable.accessory_eyebags) accessory_eyebags = true
            if (layer.resId == R.drawable.accessory_eyelashes) accessory_eyelashes = true
            if (layer.resId == R.drawable.accessory_tear) accessory_tear = true
        }
        val updated = Avatar(
            name = getAvatar().name,
            clothes = clothes,
            eyecontact = eyecontact,
            isnarrowed = isnarrowed,
            accessory_hat = accessory_hat,
            accessory_flower = accessory_flower,
            accessory_patch = accessory_patch,
            accessory_freckles = accessory_freckles,
            accessory_mustache = accessory_mustache,
            accessory_beard = accessory_beard,
            accessory_sweat = accessory_sweat,
            accessory_eyebags = accessory_eyebags,
            accessory_eyelashes = accessory_eyelashes,
            accessory_tear = accessory_tear
        )
        avatarRepository.saveAvatar(updated)
        _currentAvatar.value = updated
        Log.d("Avatar", clothes.toString())
        return updated
    }
}