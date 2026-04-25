package com.brelinx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.brelinx.data.Project
import com.brelinx.ui.screens.DashboardScreen
import com.brelinx.ui.screens.LoginScreen
import com.brelinx.ui.screens.ProjectDetailScreen
import com.brelinx.ui.theme.BrelinxclientTheme

sealed class Screen {
    object Login : Screen()
    object Dashboard : Screen()
    data class ProjectDetail(val project: Project) : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BrelinxclientTheme {
                BrelinxApp()
            }
        }
    }
}

@Composable
fun BrelinxApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (val screen = currentScreen) {
        is Screen.Login -> LoginScreen(
            onLoginSuccess = { currentScreen = Screen.Dashboard }
        )
        is Screen.Dashboard -> DashboardScreen(
            onProjectClick = { project -> currentScreen = Screen.ProjectDetail(project) },
            onLogout = { currentScreen = Screen.Login }
        )
        is Screen.ProjectDetail -> ProjectDetailScreen(
            project = screen.project,
            onBack = { currentScreen = Screen.Dashboard }
        )
    }
}
