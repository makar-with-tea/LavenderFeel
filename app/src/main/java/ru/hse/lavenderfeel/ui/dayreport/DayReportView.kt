package ru.hse.lavenderfeel.ui.dayreport

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.hse.lavenderfeel.ui.BottomBlockSubtitle
import ru.hse.lavenderfeel.ui.BottomBlockTitle
import ru.hse.lavenderfeel.ui.DayColor
import ru.hse.lavenderfeel.ui.Emotion
import ru.hse.lavenderfeel.ui.LoadingScreen
import ru.hse.lavenderfeel.ui.MyTextField
import ru.hse.lavenderfeel.ui.MyCheckBox
import ru.hse.lavenderfeel.ui.getDdMmYyyy
import ru.hse.lavenderfeel.ui.getName
import ru.hse.lavenderfeel.ui.toColor

@Composable
fun DayReportView(
    viewModel: DayReportViewModel,
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (viewModel.isLoading) {
            viewModel.loadData()
            LoadingScreen()
            return
        }
        val dateString = viewModel.date.getDdMmYyyy()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.tertiaryContainer),
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BottomBlockTitle(text = dateString)
                Row {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {}
                    IconButton(
                        onClick = { viewModel.saveReport() },
                        modifier = Modifier
                    ) {
                        Icon(
                            Icons.Filled.Save,
                            contentDescription = "Сохранить отчет",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                    }
                }
            }
            Spacer(Modifier
                .height(8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            BottomBlockSubtitle("Как ты себя чувствуешь?")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Emotion.entries.filter { it != Emotion.NONE }.forEach { mood ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = { viewModel.mood = mood },
                            modifier = Modifier
                                .background(
                                    if (viewModel.mood == mood) MaterialTheme.colorScheme.secondaryContainer
                                    else MaterialTheme.colorScheme.surface,
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    BorderStroke(
                                        2.dp,
                                        if (viewModel.mood == mood) MaterialTheme.colorScheme.onPrimaryContainer
                                        else Color.Transparent
                                    ),
                                    RoundedCornerShape(8.dp)
                                )
                        ) {
                            val icon = when (mood) {
                                Emotion.SAD -> Icons.Filled.SentimentDissatisfied
                                Emotion.ANGRY -> Icons.Filled.SentimentVeryDissatisfied
                                Emotion.NEUTRAL -> Icons.Filled.SentimentNeutral
                                Emotion.HAPPY -> Icons.Filled.SentimentVerySatisfied
                                else -> Icons.Filled.SentimentNeutral
                            }
                            Icon(icon, contentDescription = mood.name)
                        }
                        val moodName = when (mood) {
                            Emotion.SAD -> "Грустно"
                            Emotion.ANGRY -> "Агрессивно"
                            Emotion.NEUTRAL -> "Нейтрально"
                            Emotion.HAPPY -> "Счастливо"
                            else -> ""
                        }
                        Text(moodName)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            BottomBlockSubtitle("Какого цвета был день?")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DayColor.entries.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color.toColor(),
                                CircleShape
                            )
                            .border(
                                BorderStroke(
                                    2.dp,
                                    if (viewModel.dayColor == color) MaterialTheme.colorScheme.onPrimaryContainer
                                    else Color.Transparent
                                ),
                                CircleShape
                            )
                            .clickable { viewModel.dayColor = color },
                        contentAlignment = Alignment.Center
                    ) {}
                }
            }

            Spacer(Modifier.height(8.dp))

            Box {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    value = viewModel.description,
                    label = { Text("Описание дня") },
                    onValueChange = { viewModel.description = it },
                    colors = TextFieldDefaults.colors().copy(
                        focusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        BottomBlockSubtitle("Успехи")
                    }
                    MyCheckBox(
                        isChecked = viewModel.positiveChecks["ateWell"] ?: false,
                        onCheckedChange = { viewModel.setPositiveCheck("ateWell", it) },
                        label = "Хорошо поел"
                    )
                    MyCheckBox(
                        isChecked = viewModel.positiveChecks["drankWater"] ?: false,
                        onCheckedChange = { viewModel.setPositiveCheck("drankWater", it) },
                        label = "Пил воду"
                    )
                    MyCheckBox(
                        isChecked = viewModel.positiveChecks["sleptWell"] ?: false,
                        onCheckedChange = { viewModel.setPositiveCheck("sleptWell", it) },
                        label = "Выспался"
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        BottomBlockSubtitle("Проблемы")
                    }
                    MyCheckBox(
                        isChecked = viewModel.negativeChecks["smoked"] ?: false,
                        onCheckedChange = { viewModel.setNegativeCheck("smoked", it) },
                        label = "Курил"
                    )
                    MyCheckBox(
                        isChecked = viewModel.negativeChecks["drankAlcohol"] ?: false,
                        onCheckedChange = { viewModel.setNegativeCheck("drankAlcohol", it) },
                        label = "Пил алкоголь"
                    )
                    MyCheckBox(
                        isChecked = viewModel.negativeChecks["selfHarmed"] ?: false,
                        onCheckedChange = { viewModel.setNegativeCheck("selfHarmed", it) },
                        label = "Вредил себе"
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
