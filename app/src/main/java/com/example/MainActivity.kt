package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DrawerContent
import com.example.ui.components.HeaderTopBar
import com.example.ui.screens.*
import com.example.ui.theme.KhilparaAppTheme
import com.example.ui.theme.PoliceNavyPrimary
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.ScreenRoute
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            val currentRoute by viewModel.currentRoute.collectAsState()

            var isSplashVisible by remember { mutableStateOf(true) }

            KhilparaAppTheme(darkTheme = isDarkMode) {
                if (isSplashVisible) {
                    SplashScreen(
                        onSplashFinished = {
                            viewModel.navigateTo(ScreenRoute.HOME)
                            isSplashVisible = false
                        }
                    )
                } else {
                    MainAppContent(
                        viewModel = viewModel,
                        currentRoute = currentRoute,
                        onEmergencyCall = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:999"))
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainAppContent(
    viewModel: MainViewModel,
    currentRoute: ScreenRoute,
    onEmergencyCall: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Back handler for drawer or back to home
    BackHandler(enabled = drawerState.isOpen || currentRoute != ScreenRoute.HOME) {
        if (drawerState.isOpen) {
            coroutineScope.launch { drawerState.close() }
        } else {
            viewModel.navigateTo(ScreenRoute.HOME)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute,
                onNavigate = { route -> viewModel.navigateTo(route) },
                onCloseDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HeaderTopBar(
                    title = if (currentRoute == ScreenRoute.SPLASH) "খিলপাড়া পুলিশ তদন্ত কেন্দ্র" else currentRoute.titleBn,
                    onMenuClick = { coroutineScope.launch { drawerState.open() } },
                    onEmergencyCallClick = onEmergencyCall
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = PoliceNavyPrimary
                ) {
                    NavigationBarItem(
                        selected = currentRoute == ScreenRoute.HOME || currentRoute == ScreenRoute.SPLASH,
                        onClick = { viewModel.navigateTo(ScreenRoute.HOME) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "হোম") },
                        label = { Text("হোম", fontSize = 11.sp) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ScreenRoute.UNION_DETAILS,
                        onClick = { viewModel.navigateTo(ScreenRoute.UNION_DETAILS) },
                        icon = { Icon(Icons.Default.LocationCity, contentDescription = "ইউনিয়ন") },
                        label = { Text("ইউনিয়ন", fontSize = 11.sp) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ScreenRoute.SEARCH,
                        onClick = { viewModel.navigateTo(ScreenRoute.SEARCH) },
                        icon = { Icon(Icons.Default.Search, contentDescription = "খুঁজুন") },
                        label = { Text("খুঁজুন", fontSize = 11.sp) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ScreenRoute.CHAT,
                        onClick = { viewModel.navigateTo(ScreenRoute.CHAT) },
                        icon = { Icon(Icons.Default.Forum, contentDescription = "হটলাইন") },
                        label = { Text("হটলাইন", fontSize = 11.sp) }
                    )
                    NavigationBarItem(
                        selected = currentRoute == ScreenRoute.SETTINGS,
                        onClick = { viewModel.navigateTo(ScreenRoute.SETTINGS) },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "সেটিংস") },
                        label = { Text("সেটিংস", fontSize = 11.sp) }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (currentRoute) {
                    ScreenRoute.SPLASH -> HomeScreen(viewModel = viewModel)
                    ScreenRoute.HOME -> HomeScreen(viewModel = viewModel)
                    ScreenRoute.POLICE_OFFICERS -> PoliceOfficersScreen(viewModel = viewModel)
                    ScreenRoute.UNION_DETAILS -> UnionDetailsScreen(viewModel = viewModel)
                    ScreenRoute.SCHOOL_COLLEGE -> SchoolCollegeScreen(viewModel = viewModel)
                    ScreenRoute.CNG_STATION -> CNGStationScreen(viewModel = viewModel)
                    ScreenRoute.IMPORTANT_PERSONS -> ImportantPersonsScreen(viewModel = viewModel)
                    ScreenRoute.SEARCH -> SearchScreen(viewModel = viewModel)
                    ScreenRoute.FAVORITES -> FavoritesScreen(viewModel = viewModel)
                    ScreenRoute.CHAT -> ChatScreen(viewModel = viewModel)
                    ScreenRoute.NOTICES -> NoticesScreen(viewModel = viewModel)
                    ScreenRoute.ADMIN_PANEL -> AdminPanelScreen(viewModel = viewModel)
                    ScreenRoute.SETTINGS -> SettingsScreen(viewModel = viewModel)
                    ScreenRoute.DEVELOPER_INFO -> DeveloperInfoScreen(viewModel = viewModel)
                    ScreenRoute.ABOUT_APP -> AboutAppScreen()
                    ScreenRoute.PRIVACY_POLICY -> PrivacyPolicyScreen()
                    ScreenRoute.WEB_IMPORT -> WebImportScreen(viewModel = viewModel)
                }
            }
        }
    }
}
