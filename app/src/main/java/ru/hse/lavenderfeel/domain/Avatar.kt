package ru.hse.lavenderfeel.domain

data class Avatar(
    val clothes: ClothesType,
    val accessory: AccessoryType,
    val eyecontact: Boolean,
    val isnarrowed: Boolean

) {
    companion object {
        fun default() = Avatar(
            clothes = ClothesType.clothes_tshirt_big,
            accessory = AccessoryType.NONE,
            eyecontact = true,
            isnarrowed = false
        )
    }
}