package ru.hse.lavenderfeel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.hse.lavenderfeel.ui.LoadingScreen
import ru.hse.lavenderfeel.ui.MyTitle
import ru.hse.lavenderfeel.ui.main.MainScreen
import ru.hse.lavenderfeel.ui.main.MainViewModel
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
                        screen = Screen.Main,
                        onDayClick = { /* TODO */ },
                        onEditAvatar = { /* TODO */ },
                    )
                }
            }
        }
    }
}

enum class Screen {
    Main, DayReport, Customization, Loading
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    screen: Screen,
    onDayClick: (LocalDate) -> Unit,
    onEditAvatar: () -> Unit,
//    onSaveDayReport: (DayReportData) -> Unit,
//    onSaveCustomization: (CustomizationData) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { MyTitle(text = "LavenderFeel") },
                navigationIcon = {
                    IconButton(onClick = {
                        // TODO handle back navigation
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
        when (screen) {
            Screen.Loading -> LoadingScreen()
            Screen.Main -> MainScreen(MainViewModel(), onDayClick, onEditAvatar)
            else -> LoadingScreen()
//        Screen.DayReport -> DayReportScreen(onSaveDayReport)
//        Screen.Customization -> CustomizationScreen(onSaveCustomization)
        }
    }
}
