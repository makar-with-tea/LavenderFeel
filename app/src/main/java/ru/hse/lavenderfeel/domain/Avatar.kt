package ru.hse.lavenderfeel.domain

data class Avatar(
    val clothes: ClothesType,
    val accessory: AccessoryType,
    val eyecontact: Boolean,
    val isnarrowed: Boolean

) {
    companion object {
        fun default() = Avatar(
            clothes = ClothesType.CASUAL1,
            accessory = AccessoryType.NONE,
            eyecontact = true,
            isnarrowed = false
        )
    }
}