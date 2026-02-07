package ru.hse.lavenderfeel.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.hse.lavenderfeel.ui.main.EmotionColor
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.io.readBytes
import kotlin.io.use
import kotlin.text.format

fun Boolean?.orFalse(): Boolean = this ?: false

fun Int?.orZero(): Int = this ?: 0

fun <T> T?.orDefault(default: T): T = this ?: default

class BitmapHelper {
    companion object {
        fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
            val stream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream) ?: return null
            return stream.toByteArray()
        }

        fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
            byteArray ?: return null
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }
}

fun rawResId(name: String, context: Context): Int {
    return context.resources.getIdentifier(name, "raw", context.packageName)
}

fun nameAndAgeString(name: String, age: Int): String = "$name, $age"

fun getBytesFromUri(context: Context, uri: Uri): ByteArray? {
    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        return inputStream.readBytes()
    }
    return null
}

fun EmotionColor.toColor(): Color {
    return when (this) {
        EmotionColor.VERY_SAD -> Color(0xFFF17070)
        EmotionColor.SAD -> Color(0xFF5AADFF)
        EmotionColor.NEUTRAL -> Color(0xFFB991FF)
        EmotionColor.HAPPY -> Color(0xFF8BFF90)
        EmotionColor.VERY_HAPPY -> Color(0xFFFFE376)
        EmotionColor.NONE -> Color(0xFF757575).copy(alpha = 0.2f)
    }
}
