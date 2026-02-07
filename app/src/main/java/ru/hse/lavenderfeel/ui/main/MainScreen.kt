package ru.hse.lavenderfeel.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.hse.lavenderfeel.ui.toColor
import java.time.LocalDate
import kotlin.text.chunked
import kotlin.text.forEach
import kotlin.toString

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onDayClick: (LocalDate) -> Unit,
    onEditAvatar: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.weight(1.5f),
            contentAlignment = Alignment.Center
        ) {
            AvatarView(
                layers = viewModel.avatarLayers,
                modifier = Modifier
                    .fillMaxSize()
            )
            IconButton(
                onClick = onEditAvatar,
                modifier = Modifier
                    .padding(top = 110.dp, end = 20.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .border(
                        BorderStroke(4.dp, MaterialTheme.colorScheme.secondaryContainer),
                        CircleShape
                    )
                    .align(Alignment.TopEnd)
            ) {
                Icon(Icons.Default.Brush, contentDescription = "Редактировать аватар")
            }
        }
        CalendarView(
            viewModel = viewModel,
            onDayClick = onDayClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(
                    BorderStroke(2.dp, MaterialTheme.colorScheme.tertiaryContainer),
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ),
        )
    }
}

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

@Composable
fun CalendarView(
    viewModel: MainViewModel,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { viewModel.prevMonth() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous month")
            }
            Text(
                text = viewModel.monthName,
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = { viewModel.nextMonth() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next month")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            ) {
            viewModel.daysOfWeek.forEach { dayName ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colorScheme.tertiaryContainer),
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(dayName)
                }
            }
        }
        val days = viewModel.days
        for (week in days.chunked(7)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                week.forEach { day ->
                    Box(
                        Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .clip(
                                when (day.color) {
                                    EmotionColor.NONE -> RectangleShape
                                    else -> CircleShape
                                }
                            )
                            .background(
                                if (day.isCurrentMonth) day.color.toColor() else day.color.toColor().copy(alpha = 0.2f)
                            )
                            .clickable(enabled = day.isCurrentMonth) { onDayClick(day.date) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.date.dayOfMonth.toString(),
                            color = if (day.isCurrentMonth) Color.Unspecified else Color.Gray
                        )
                    }
                }
            }
        }
    }
}
