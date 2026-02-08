package ru.hse.lavenderfeel.ui.customization

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.hse.lavenderfeel.ui.CustomizationCategory

@Composable
fun CustomizationView(
    viewModel: CustomizationViewModel,
    modifier: Modifier = Modifier,
) {
    viewModel.init()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.tertiaryContainer),
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var selectedCategory by remember { mutableStateOf(CustomizationCategory.CLOTHES) }
        Box(modifier = Modifier.height(80.dp)) {
            TextField(
                modifier = Modifier
                    .padding(8.dp),
                value = viewModel.name,
                label = { Text("имя") },
                maxLines = 1,
                onValueChange = { viewModel.saveName(it)},
                colors = TextFieldDefaults.colors().copy(
                    focusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )
        }
        Spacer(Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            CustomizationCategory.entries.forEach { category ->
                val isSelected = selectedCategory == category
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.tertiaryContainer
                            else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                            else BorderStroke((-1).dp, Color.Transparent),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedCategory = category }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category.displayName,
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        val layers = viewModel.resourcesByCategory[selectedCategory] ?: emptyList()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 96.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(layers) { layer ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .border(
                            if (viewModel.isLayerSelected(layer))
                                BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                            else BorderStroke((-1).dp, Color.Transparent),
                        )
                        .clickable {
                            viewModel.selectLayer(layer)
                        }
                ) {
                    Image(
                        painter = painterResource(id = layer.resId),
                        contentDescription = layer.description,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                    )
                }
            }
        }
    }
}
