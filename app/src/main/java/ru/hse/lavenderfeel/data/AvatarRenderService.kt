package ru.hse.lavenderfeel.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.hse.lavenderfeel.domain.Avatar
import ru.hse.lavenderfeel.domain.ClothesType
import ru.hse.lavenderfeel.domain.DailyEntry
import ru.hse.lavenderfeel.domain.Emotion
import ru.hse.lavenderfeel.domain.FaceColor
import ru.hse.lavenderfeel.ui.AvatarLayer
import ru.hse.lavenderfeel.R

class AvatarRenderService(
    avatarService: AvatarService,
    dailyEntryService: DailyEntryService,
) {
    val currentAvatarLayers: Flow<List<AvatarLayer>> = combine(
        avatarService.currentAvatar,
        dailyEntryService.currentEntry
    ) { avatar, entry ->
        getAvatarLayers(avatar, entry)
    }

    fun getAvatarLayers(avatar: Avatar, entry: DailyEntry?): List<AvatarLayer> {
        val layers = mutableListOf<AvatarLayer>()

        layers.add(clothesLayer(avatar.clothes))
        layers.add(faceColorLayer(entry?.faceColor ?: FaceColor.PURPLE))
        layers.add(emotionLayer(entry?.emotion ?: Emotion.NEUTRAL))
        if (avatar.accessory_eyebags) {
            layers.add(
                AvatarLayer(
                    resId = R.drawable.accessory_eyebags,
                    description = "Мешки под глазами"
                )
            )
        }
        if (avatar.isnarrowed) {
            layers.add(
                AvatarLayer(
                    resId = R.drawable.eye_contact_squint,
                    description = "Прищуренные глаза"
                )
            )
        } else {
            layers.add(
                AvatarLayer(
                    resId = R.drawable.eye_contact_base,
                    description = "Обычные глаза"
                )
            )
        }

        if (avatar.accessory_hat) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_hat, description = "Шляпа"))
        }
        if (avatar.accessory_beard) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_beard, description = "Борода"))
        }

        if (avatar.accessory_eyelashes) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_eyelashes, description = "Ресницы"))
        }
        if (avatar.accessory_flower) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_flower, description = "Цветок"))
        }
        if (avatar.accessory_freckles) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_freckles, description = "Веснушки"))
        }
        if (avatar.accessory_mustache) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_mustache, description = "Усы"))
        }
        if (avatar.accessory_patch) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_patch, description = "Пластырь"))
        }
        if (avatar.accessory_sweat) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_sweat, description = "Пот"))
        }
        if (avatar.accessory_tear) {
            layers.add(AvatarLayer(resId = R.drawable.accessory_tear, description = "Слеза"))
        }
        if (entry?.negativeHabits?.selfharm ?: false){
            layers.add(AvatarLayer(resId = R.drawable.day_selfharm, description = "Селфхарм"))
        }
        if (!(entry?.positiveHabits?.food ?: true)){
            layers.add(AvatarLayer(resId = R.drawable.day_hunger, description = "Голод"))
        }
        if (!(entry?.positiveHabits?.water ?: true)){
            layers.add(AvatarLayer(resId = R.drawable.day_thirst, description = "Жажда"))
        }
        if (!(entry?.positiveHabits?.sleep ?: true)){
            layers.add(AvatarLayer(resId = R.drawable.day_insomnia, description = "Сон"))
        }
        if (entry?.negativeHabits?.alcohol ?: false){
            layers.add(AvatarLayer(resId = R.drawable.day_puke, description = "Алкоголь"))
        }
        if (entry?.negativeHabits?.smoke ?: false){
            layers.add(AvatarLayer(resId = R.drawable.day_smoke, description = "Курение"))
        }

        return layers
    }

    private fun clothesLayer(clothes: ClothesType): AvatarLayer =
        when (clothes) {
            ClothesType.clothes_tshirt_small -> AvatarLayer(
                R.drawable.clothes_tshirt_small,
                "Футболка маленькая"
            )

            ClothesType.clothes_tshirt_big -> AvatarLayer(
                R.drawable.clothes_tshirt_big,
                "Футболка большая"
            )

            ClothesType.clothes_sweater_small -> AvatarLayer(
                R.drawable.clothes_sweater_small,
                "Свитер маленький"
            )

            ClothesType.clothes_sweater_big -> AvatarLayer(
                R.drawable.clothes_sweater_big,
                "Свитер большой"
            )

            ClothesType.clothes_suit_small -> AvatarLayer(
                R.drawable.clothes_suit_small,
                "Костюм маленький"
            )

            ClothesType.clothes_suit_big -> AvatarLayer(
                R.drawable.clothes_suit_big,
                "Костюм большой"
            )
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

    private fun emotionLayer(emotion: Emotion): AvatarLayer =
        when (emotion) {
            Emotion.NEUTRAL -> AvatarLayer(R.drawable.emotion_neutral, "Нейтральный")
            Emotion.SAD -> AvatarLayer(R.drawable.emotion_sad, "Грустный")
            Emotion.ANGRY -> AvatarLayer(R.drawable.emotion_angry, "Злой")
            Emotion.HAPPY -> AvatarLayer(R.drawable.emotion_happy, "Радостный")
        }
}