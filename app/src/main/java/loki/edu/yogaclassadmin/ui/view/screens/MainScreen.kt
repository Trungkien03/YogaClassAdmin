package loki.edu.yogaclassadmin.ui.view.screens

import ProfileScreen
import YogaClassListScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import loki.edu.yogaclassadmin.ui.navigation.BottomNavItem
import loki.edu.yogaclassadmin.ui.navigation.BottomNavigationBar
import loki.edu.yogaclassadmin.ui.view.bottomtabs.InstanceScreen

@Composable
fun MainScreen(
    navController1: NavHostController,
    appState: AppStateViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.YogaClass.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.YogaClass.route) { YogaClassListScreen(navController1) }
            composable(BottomNavItem.Instance.route) { InstanceScreen() }
            composable(BottomNavItem.Profile.route) { ProfileScreen( navController1, appState) }
        }
    }
}
