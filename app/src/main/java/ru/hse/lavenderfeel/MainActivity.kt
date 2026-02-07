package ru.hse.lavenderfeel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.hse.lavenderfeel.ui.avatar.AvatarView
import ru.hse.lavenderfeel.ui.avatar.AvatarViewModel
import ru.hse.lavenderfeel.ui.LoadingScreen
import ru.hse.lavenderfeel.ui.MyTitle
import ru.hse.lavenderfeel.ui.calendar.CalendarView
import ru.hse.lavenderfeel.ui.calendar.CalendarViewModel
import ru.hse.lavenderfeel.ui.customization.CustomizationView
import ru.hse.lavenderfeel.ui.customization.CustomizationViewModel
import ru.hse.lavenderfeel.ui.dayreport.DayReportView
import ru.hse.lavenderfeel.ui.dayreport.DayReportViewModel
import ru.hse.lavenderfeel.ui.theme.CustomColors
import ru.hse.lavenderfeel.ui.theme.LavenderFeelTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LavenderFeelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen(
                        calendarViewModel = CalendarViewModel(),
                        avatarViewModel = AvatarViewModel(),
                    )
                }
            }
        }
    }
}

enum class Screen {
    Calendar, DayReport, Customization
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    calendarViewModel: CalendarViewModel,
    avatarViewModel: AvatarViewModel,
) {
    var screen by rememberSaveable { mutableStateOf(Screen.Calendar) }
    val screenStack = rememberSaveable { mutableStateListOf<Screen>() }

    val context = LocalContext.current
    fun handleBack() {
        if (screenStack.isNotEmpty()) {
            screen = screenStack.removeAt(screenStack.size - 1)
        } else {
            (context as? ComponentActivity)?.finish()
        }
    }
    BackHandler {
        handleBack()
    }

    val avatarLayers by avatarViewModel.avatarLayers.collectAsState()
    if (avatarLayers.isEmpty()) {
        avatarViewModel.loadInitialLayers()
        LoadingScreen()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { MyTitle(text = "LavenderFeel") },
                navigationIcon = {
                    IconButton(onClick = {
                        handleBack()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = "Назад",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.weight(1.5f),
                contentAlignment = Alignment.Center
            ) {
                AvatarView(
                    layers = avatarLayers,
                    modifier = Modifier
                        .fillMaxSize()
                )
                if (screen == Screen.Calendar) {
                    IconButton(
                        onClick = {
                            screenStack.add(screen)
                            screen = Screen.Customization
                        },
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
            }

            var chosenDay by rememberSaveable { mutableStateOf<LocalDate?>(null) }
            when (screen) {
                Screen.Calendar -> CalendarView(
                    viewModel = calendarViewModel,
                    onDayClick = { day ->
                        chosenDay = day
                        screenStack.add(screen)
                        screen = Screen.DayReport
                    },
                    modifier = Modifier.weight(1f)
                )
                Screen.Customization -> CustomizationView(
                    viewModel = CustomizationViewModel(
                        initialSelectedLayers = avatarLayers,
                    ),
                    modifier = Modifier.weight(1f),
                )
                Screen.DayReport -> if (chosenDay != null) {
                    DayReportView(
                        viewModel = DayReportViewModel(chosenDay!!),
                        modifier = Modifier.weight(1f)
                    )
                } else handleBack()
            }
        }
    }
}
