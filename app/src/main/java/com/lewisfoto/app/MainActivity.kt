package com.lewisfoto.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lewisfoto.app.data.Motif
import com.lewisfoto.app.ui.detail.SpotDetailScreen
import com.lewisfoto.app.ui.favorites.FavoritesScreen
import com.lewisfoto.app.ui.home.HomeScreen
import com.lewisfoto.app.ui.map.MapScreen
import com.lewisfoto.app.ui.settings.SettingsScreen
import com.lewisfoto.app.ui.sources.SourcesScreen
import com.lewisfoto.app.ui.theme.LewisFotoTheme
import com.lewisfoto.app.viewmodel.AppViewModel
import org.osmdroid.config.Configuration
import java.io.File

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().apply {
            userAgentValue = packageName
            osmdroidBasePath = File(cacheDir, "osmdroid")
            osmdroidTileCache = File(cacheDir, "osmdroid/tiles")
        }
        enableEdgeToEdge()
        setContent {
            val viewModel: AppViewModel = viewModel()
            val darkMode by viewModel.darkMode.collectAsStateWithLifecycle()
            LewisFotoTheme(darkTheme = darkMode) {
                LewisFotoNav(viewModel)
            }
        }
    }
}

private data class BottomDestination(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun LewisFotoNav(viewModel: AppViewModel) {
    val navController = rememberNavController()
    val destinations = listOf(
        BottomDestination("home", "Start", Icons.Filled.Home),
        BottomDestination("map", "Karte", Icons.Filled.Explore),
        BottomDestination("favorites", "Merkliste", Icons.Filled.Bookmarks),
        BottomDestination("sources", "Quellen", Icons.Filled.Info),
        BottomDestination("settings", "Einstellungen", Icons.Filled.Settings)
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(modifier = Modifier.height(58.dp).navigationBarsPadding()) {
                destinations.forEach { dest ->
                    NavigationBarItem(
                        selected = currentRoute == dest.route,
                        onClick = {
                            navController.navigate(dest.route) {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(dest.icon, contentDescription = dest.label) },
                        label = { Text(dest.label, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onMotifClick = { motif -> navController.navigate("map/${motif.name}") }
                )
            }
            composable("map") {
                MapScreen(
                    viewModel = viewModel,
                    motif = null,
                    onSpotClick = { navController.navigate("detail/${it.id}") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "map/{motif}",
                arguments = listOf(navArgument("motif") { type = NavType.StringType })
            ) { entry ->
                val motif = Motif.fromName(entry.arguments?.getString("motif"))
                MapScreen(
                    viewModel = viewModel,
                    motif = motif,
                    onSpotClick = { navController.navigate("detail/${it.id}") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "detail/{spotId}",
                arguments = listOf(navArgument("spotId") { type = NavType.StringType })
            ) { entry ->
                val spot = viewModel.spotById(entry.arguments?.getString("spotId").orEmpty())
                if (spot != null) {
                    SpotDetailScreen(
                        viewModel = viewModel,
                        spot = spot,
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    navController.popBackStack()
                }
            }
            composable("favorites") {
                FavoritesScreen(
                    viewModel = viewModel,
                    onSpotClick = { navController.navigate("detail/${it.id}") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("sources") {
                SourcesScreen(viewModel = viewModel)
            }
            composable("settings") {
                SettingsScreen(viewModel = viewModel)
            }
        }
    }
}
