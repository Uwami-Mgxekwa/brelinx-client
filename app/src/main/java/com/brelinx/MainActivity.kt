package com.brelinx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.brelinx.data.Project
import com.brelinx.ui.screens.*
import com.brelinx.ui.theme.BrelinxclientTheme
import com.brelinx.ui.theme.BrelinxTeal
import com.brelinx.ui.theme.BrelinxWhite

sealed class Screen(val route: String, val label: String = "", val icon: ImageVector? = null) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard", "Home", Icons.Default.Home)
    object Files : Screen("files", "Files", Icons.Default.Folder)
    object Messages : Screen("messages", "Messages", Icons.Default.Email)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    data class ProjectDetail(val project: Project) : Screen("project_detail")
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
    
    val bottomNavItems = listOf(
        Screen.Dashboard,
        Screen.Files,
        Screen.Messages,
        Screen.Profile
    )

    if (currentScreen == Screen.Login) {
        LoginScreen(
            onLoginSuccess = { currentScreen = Screen.Dashboard }
        )
    } else {
        Scaffold(
            bottomBar = {
                // Only show bottom bar for main navigation screens
                if (currentScreen in bottomNavItems || currentScreen is Screen.ProjectDetail) {
                    NavigationBar(
                        containerColor = BrelinxWhite,
                        tonalElevation = 8.dp
                    ) {
                        bottomNavItems.forEach { screen ->
                            NavigationBarItem(
                                icon = { screen.icon?.let { Icon(it, contentDescription = screen.label) } },
                                label = { Text(screen.label) },
                                selected = currentScreen == screen || (screen == Screen.Dashboard && currentScreen is Screen.ProjectDetail),
                                onClick = { currentScreen = screen },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = BrelinxTeal,
                                    selectedTextColor = BrelinxTeal,
                                    indicatorColor = BrelinxTeal.copy(alpha = 0.1f)
                                )
                            )
                        }
                    }
                }
            }
        ) { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                when (val screen = currentScreen) {
                    is Screen.Dashboard -> DashboardScreen(
                        onProjectClick = { project -> currentScreen = Screen.ProjectDetail(project) },
                        onLogout = { currentScreen = Screen.Login }
                    )
                    is Screen.Files -> FilesScreen()
                    is Screen.Messages -> MessagesScreen()
                    is Screen.Profile -> ProfileScreen(
                        onLogout = { currentScreen = Screen.Login }
                    )
                    is Screen.ProjectDetail -> ProjectDetailScreen(
                        project = screen.project,
                        onBack = { currentScreen = Screen.Dashboard }
                    )
                    else -> {}
                }
            }
        }
    }
}

