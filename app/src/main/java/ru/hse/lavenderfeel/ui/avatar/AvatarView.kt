package ru.hse.lavenderfeel.ui.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ru.hse.lavenderfeel.ui.AvatarLayer

@Composable
fun AvatarView(layers: List<AvatarLayer>, modifier: Modifier = Modifier) {
    Box(modifier) {
        layers.forEach { layer ->
            Image(
                painter = painterResource(id = layer.resId),
                contentDescription = layer.description,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
