package loki.edu.yogaclassadmin.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomTab {
    YOGA_CLASS,
    INSTANCE,
    PROFILE
}

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    object YogaClass : BottomNavItem("Yoga Class", Icons.Filled.Home, "yoga_class")
    object Instance : BottomNavItem("Instance", Icons.Filled.Class, "instance")
    object Profile: BottomNavItem("Profile", Icons.Filled.Person, "profile")
}

fun getBottomNavItem(tab: BottomTab): BottomNavItem {
    return when (tab) {
        BottomTab.YOGA_CLASS -> BottomNavItem.YogaClass
        BottomTab.INSTANCE -> BottomNavItem.Instance
        BottomTab.PROFILE -> BottomNavItem.Profile
    }
}
