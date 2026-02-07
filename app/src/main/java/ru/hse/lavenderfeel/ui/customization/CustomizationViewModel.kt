package ru.hse.lavenderfeel.ui.customization

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.hse.lavenderfeel.R
import ru.hse.lavenderfeel.ui.AvatarLayer
import ru.hse.lavenderfeel.ui.CustomizationCategory

class CustomizationViewModel : ViewModel() {
    var name by mutableStateOf("Your Name")
    var selectedCategory by mutableStateOf(CustomizationCategory.EYES)
    var selectedLayers by mutableStateOf<Map<CustomizationCategory, AvatarLayer>>(emptyMap())

    val resourcesByCategory = mapOf(
        CustomizationCategory.CLOTHES to listOf(
            AvatarLayer(resId = R.drawable.clothes_suit_big, description = "Костюм большой"),
            AvatarLayer(resId = R.drawable.clothes_suit_small, description = "Костюм маленький"),
            AvatarLayer(resId = R.drawable.clothes_tshirt_big, description = "Футболка большая"),
            AvatarLayer(resId = R.drawable.clothes_tshirt_small, description = "Футболка маленькая"),
            AvatarLayer(resId = R.drawable.clothes_sweater_big, description = "Свитер большой"),
            AvatarLayer(resId = R.drawable.clothes_sweater_small, description = "Свитер маленький"),
        ),
        CustomizationCategory.EYES to listOf(
            AvatarLayer(resId = R.drawable.eye_contact_base, description = "Обычные глаза"),
            AvatarLayer(resId = R.drawable.eye_contact_squint, description = "Прищуренные глаза"),
        ),
        CustomizationCategory.ACCESSORIES to listOf(
            AvatarLayer(resId = R.drawable.accessory_beard, description = "Борода"),
            AvatarLayer(resId = R.drawable.accessory_eyebags, description = "Мешки под глазами"),
            AvatarLayer(resId = R.drawable.accessory_eyelashes, description = "Ресницы"),
            AvatarLayer(resId = R.drawable.accessory_flower, description = "Цветок"),
            AvatarLayer(resId = R.drawable.accessory_freckles, description = "Веснушки"),
            AvatarLayer(resId = R.drawable.accessory_hat, description = "Шляпа"),
            AvatarLayer(resId = R.drawable.accessory_mustache, description = "Усы"),
            AvatarLayer(resId = R.drawable.accessory_patch, description = "Пластырь"),
            AvatarLayer(resId = R.drawable.accessory_sweat, description = "Пот"),
            AvatarLayer(resId = R.drawable.accessory_tear, description = "Слеза"),
        )
    )

    fun selectLayer(category: CustomizationCategory, layer: AvatarLayer) {
        selectedLayers = selectedLayers.toMutableMap().apply { put(category, layer) }
    }
}
