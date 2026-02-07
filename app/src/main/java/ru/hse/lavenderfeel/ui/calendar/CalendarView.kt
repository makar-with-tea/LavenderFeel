package ru.hse.lavenderfeel.ui.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.hse.lavenderfeel.ui.EmotionColor
import ru.hse.lavenderfeel.ui.toColor
import java.time.LocalDate

@Composable
fun CalendarView(
    viewModel: CalendarViewModel,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { viewModel.prevMonth() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Предыдущий месяц",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Text(
                text = viewModel.monthName,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
            IconButton(onClick = { viewModel.nextMonth() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Следующий месяц",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )

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
                    Text(
                        text = dayName,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    )
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
                            .border(
                                BorderStroke(
                                    if (day.isToday) 2.dp else (-1).dp,
                                    if (day.isToday) MaterialTheme.colorScheme.primary else Color.Transparent
                                ),
                                when (day.color) {
                                    EmotionColor.NONE -> RectangleShape
                                    else -> CircleShape
                                }
                            )
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
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
                                    color = when {
                                        day.isCurrentMonth -> MaterialTheme.colorScheme.onBackground
                                        else -> Color.Gray
                                    },
                                )
                        )
                    }
                }
            }
        }
    }
}
