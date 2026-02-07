package ru.hse.lavenderfeel.ui.customization

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.hse.lavenderfeel.R
import ru.hse.lavenderfeel.data.DataModule
import ru.hse.lavenderfeel.ui.AvatarLayer
import ru.hse.lavenderfeel.ui.CustomizationCategory
import java.time.LocalDate

class CustomizationViewModel(
    initialName: String = "Незнакомец",
    initialSelectedLayers: List<AvatarLayer> = emptyList()
) : ViewModel() {
    var name by mutableStateOf(initialName)

    val resourcesByCategory = mapOf(
        CustomizationCategory.CLOTHES to mutableListOf(
            AvatarLayer(resId = R.drawable.clothes_suit_big, description = "Костюм большой"),
            AvatarLayer(resId = R.drawable.clothes_suit_small, description = "Костюм маленький"),
            AvatarLayer(resId = R.drawable.clothes_tshirt_big, description = "Футболка большая"),
            AvatarLayer(resId = R.drawable.clothes_tshirt_small, description = "Футболка маленькая"),
            AvatarLayer(resId = R.drawable.clothes_sweater_big, description = "Свитер большой"),
            AvatarLayer(resId = R.drawable.clothes_sweater_small, description = "Свитер маленький"),
        ),
        CustomizationCategory.EYES to mutableListOf(
            AvatarLayer(resId = R.drawable.eye_contact_base, description = "Обычные глаза"),
            AvatarLayer(resId = R.drawable.eye_contact_squint, description = "Прищуренные глаза"),
        ),
        CustomizationCategory.ACCESSORIES to mutableListOf(
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

    var selectedLayers by mutableStateOf(
        initialSelectedLayers
            .filter { layer ->
                resourcesByCategory.values.any { it.contains(layer) }
            }
            .toMutableList()
    )

    fun init() {
        DataModule.dailyEntryService.getEntryForDate(LocalDate.now())
    }

    fun saveName(newName: String) {
        name = newName
        DataModule.avatarService.updateName(name)
    }

    fun selectLayer(layer: AvatarLayer) {
        if (selectedLayers.contains(layer)) {
            selectedLayers = selectedLayers.toMutableList().also { it.remove(layer) }
        } else {
            when (layer) {
                in resourcesByCategory[CustomizationCategory.CLOTHES] ?: emptyList() -> {
                    selectedLayers.removeAll(resourcesByCategory[CustomizationCategory.CLOTHES] ?: emptyList())
                }
                in resourcesByCategory[CustomizationCategory.EYES] ?: emptyList() -> {
                    selectedLayers.removeAll(resourcesByCategory[CustomizationCategory.EYES] ?: emptyList())
                }
            }
            selectedLayers = selectedLayers.toMutableList().also { it.add(layer) }
        }
        DataModule.avatarService.buildAvatar(selectedLayers)
    }

    fun isLayerSelected(layer: AvatarLayer): Boolean {
        return selectedLayers.contains(layer)
    }
}
