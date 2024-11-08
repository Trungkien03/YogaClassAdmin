package loki.edu.yogaclassadmin.ui.navigation


import AddNewClassScreen
import YogaClassDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import loki.edu.yogaclassadmin.ui.view.screens.LoginScreen
import loki.edu.yogaclassadmin.ui.view.screens.MainScreen

enum class Screen {
    HOME,
    LOGIN,
    ADD_NEW_CLASS
}
sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object AddNewClass: NavigationItem(Screen.ADD_NEW_CLASS.name)
    object Detail: NavigationItem("detail/{yogaClassId}") {
        fun createRoute(yogaClassId: String) = "detail/$yogaClassId"
    }
}



@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Login.route
) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate(NavigationItem.Home.route) {
                popUpTo(NavigationItem.Login.route) { inclusive = true }
            }
        } else {
            navController.navigate(NavigationItem.Login.route) {
                popUpTo(NavigationItem.Home.route) { inclusive = true }
            }
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }
        composable(NavigationItem.Home.route) {
            MainScreen(navController)
        }
        composable(NavigationItem.AddNewClass.route) {
            AddNewClassScreen(navController)
        }
        composable(
            route = NavigationItem.Detail.route,
            arguments = listOf(navArgument("yogaClassId") { type = NavType.StringType })
        ) { backStackEntry ->
            val yogaClassId = backStackEntry.arguments?.getString("yogaClassId")
            if (yogaClassId != null) {
                YogaClassDetailScreen(navController = navController, yogaClassId = yogaClassId)
            }
        }
    }
}

