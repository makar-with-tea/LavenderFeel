package ru.hse.lavenderfeel.domain

data class Avatar(
    val name: String,
    val clothes: ClothesType,
    val eyecontact: Boolean,
    val isnarrowed: Boolean,
    val accessory_hat: Boolean,
    val accessory_flower: Boolean,
    val accessory_patch: Boolean,
    val accessory_freckles: Boolean,
    val accessory_mustache: Boolean,
    val accessory_beard: Boolean,
    val accessory_sweat: Boolean,
    val accessory_eyebags: Boolean,
    val accessory_eyelashes: Boolean,
    val accessory_tear: Boolean

) {
    companion object {
        fun default() = Avatar(
            name = "Некий Никто",
            clothes = ClothesType.clothes_tshirt_big,
            eyecontact = true,
            isnarrowed = false,
            accessory_hat = false,
            accessory_flower = false,
            accessory_patch = false,
            accessory_freckles = false,
            accessory_mustache = false,
            accessory_beard = false,
            accessory_sweat = false,
            accessory_eyebags = false,
            accessory_eyelashes = false,
            accessory_tear = false
        )
    }
}