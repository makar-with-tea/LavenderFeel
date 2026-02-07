package ru.hse.lavenderfeel.ui.customization

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.hse.lavenderfeel.ui.MyTextField
import ru.hse.lavenderfeel.ui.AvatarLayer
import ru.hse.lavenderfeel.ui.CustomizationCategory
import ru.hse.lavenderfeel.ui.avatar.AvatarView

@Composable
fun CustomizationView(
    viewModel: CustomizationViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.tertiaryContainer),
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ),
    ) {
        MyTextField(
            value = viewModel.name,
            label = "Имя",
            isError = false,
            onValueChange = { viewModel.name = it }
        )
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomizationCategory.entries.forEach { category ->
                val isSelected = viewModel.selectedCategory == category
                Text(
                    text = category.displayName,
                    modifier = Modifier
                        .clickable { viewModel.selectedCategory = category }
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surface
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        val layers = viewModel.resourcesByCategory[viewModel.selectedCategory] ?: emptyList()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 48.dp),
            modifier = Modifier
                .fillMaxWidth()
//                .height(200.dp)
        ) {
            items(layers) { layer ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(64.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable { viewModel.selectLayer(viewModel.selectedCategory, layer) }
                ) {
                    AvatarView(listOf(layer), modifier = Modifier.size(48.dp))
                }
            }
        }
    }
}
