package ru.hse.lavenderfeel.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.let

@Composable
fun MyTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(8.dp),
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
}

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    isError: Boolean,
    enabled: Boolean = true,
    errorText: String? = null,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier.padding(8.dp),
        value = value,
        label = { Text(label) },
        isError = isError,
        enabled = enabled,
        onValueChange = onValueChange,
        supportingText = { errorText?.let { Text(it) } }
    )
}

@Composable
fun LoadingBlock() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun RawImageOrPlaceholder(
    url: String?,
    @DrawableRes placeholderId: Int,
    contentScale: ContentScale = ContentScale.Crop,
    context: Context,
    modifier: Modifier = Modifier
) {
    if (url != null) {
        val rawResId = rawResId(url, LocalContext.current)
        val imageBitmap by remember(rawResId) {
            mutableStateOf(
                try {
                    BitmapFactory.decodeStream(context.resources.openRawResource(rawResId))
                        ?.asImageBitmap()
                } catch (e: Exception) {
                    Log.i("RawImageOrPlaceholder", "Error loading image from raw resource: $url", e)
                    null
                }
            )
        }
        imageBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = modifier,
                contentScale = contentScale,
            )
        }
    } else {
        Image(
            painter = painterResource(id = placeholderId),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
        )
    }
}

@Composable
fun MyCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .clickable { onCheckedChange(!isChecked) }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null,
        )
        Text(label)
    }
}

@Composable
fun BottomBlockTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}

@Composable
fun BottomBlockSubtitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}