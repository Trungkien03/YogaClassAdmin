package loki.edu.yogaclassadmin.ui.navigation


import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
    ) {
        val items = listOf(
            BottomNavItem.YogaClass,
            BottomNavItem.Instance,
            BottomNavItem.Profile
        )

        items.forEach { item ->
            val isSelected = navController.currentDestination?.route == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

            )
        }
    }
}
