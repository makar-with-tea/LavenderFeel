package ru.hse.lavenderfeel.data

import ru.hse.lavenderfeel.domain.AccessoryType
import ru.hse.lavenderfeel.domain.Avatar
import ru.hse.lavenderfeel.domain.ClothesType
import ru.hse.lavenderfeel.domain.DailyEntry
import ru.hse.lavenderfeel.domain.EmotionType
import ru.hse.lavenderfeel.domain.FaceColor
import ru.hse.lavenderfeel.ui.AvatarLayer
import java.time.LocalDate
import ru.hse.lavenderfeel.R

class AvatarRenderService(
    private val avatarRepository: AvatarRepository,
    private val dailyEntryRepository: DailyEntryRepository
) {
    fun getAvatarLayers(date: LocalDate): MutableList<AvatarLayer> {
        val layers = mutableListOf<AvatarLayer>()

        val avatar = avatarRepository.getAvatar() ?: Avatar.default()
        val entry = dailyEntryRepository.getByDate(date)

        layers.add(clothesLayer(avatar.clothes))
        layers.add(faceColorLayer(entry!!.faceColor))

        return layers
    }
    private fun clothesLayer(clothes: ClothesType): AvatarLayer =
        when (clothes) {
            ClothesType.clothes_tshirt_small -> AvatarLayer(R.drawable.clothes_tshirt_small, "Футболка маленькая")
            ClothesType.clothes_tshirt_big -> AvatarLayer(R.drawable.clothes_tshirt_big, "Футболка большая")
            ClothesType.clothes_sweater_small -> AvatarLayer(R.drawable.clothes_sweater_small, "Свитер маленький")
            ClothesType.clothes_sweater_big -> AvatarLayer(R.drawable.clothes_sweater_big, "Свитер большой")
            ClothesType.clothes_suit_small -> AvatarLayer(R.drawable.clothes_suit_small, "Костюм маленький")
            ClothesType.clothes_suit_big -> AvatarLayer(R.drawable.clothes_suit_big, "Костюм большой")
        }
    private fun faceColorLayer(color: FaceColor?): AvatarLayer =
        when (color) {
            FaceColor.RED -> AvatarLayer(R.drawable.face_red, "Красный")
            FaceColor.ORANGE -> AvatarLayer(R.drawable.face_orange, "Оранжевый")
            FaceColor.YELLOW -> AvatarLayer(R.drawable.face_yellow, "Желтый")
            FaceColor.GREEN -> AvatarLayer(R.drawable.face_green, "Зеленый")
            FaceColor.LIGHTBLUE -> AvatarLayer(R.drawable.face_light_blue, "Голубой")
            FaceColor.DARKBLUE -> AvatarLayer(R.drawable.face_blue, "Синий")
            FaceColor.PURPLE -> AvatarLayer(R.drawable.face_purple, "Фиолетовый")
            FaceColor.PINK -> AvatarLayer(R.drawable.face_pink, "Розовый")
            else -> AvatarLayer(R.drawable.face_orange, "Оранжевый")
        }

}