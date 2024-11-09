package loki.edu.yogaclassadmin.ui.navigation


import AddNewClassScreen
import YogaClassDetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
    startDestination: String = NavigationItem.Login.route,
    appStateViewModel: AppStateViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel()
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val coroutineScope = rememberCoroutineScope()

    // Triggered when the currentUser changes
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            // After Google Sign-In, check if the user exists in the database
            loginViewModel.checkUserInDatabaseAfterGoogleSignIn(currentUser.email ?: "") { userExists ->
                if (userExists) {
                    loginViewModel.currentUser.value?.let { user ->
                        appStateViewModel.setUserLoggedIn(user)
                    }
                    navController.navigate(NavigationItem.Home.route) {
                        popUpTo(NavigationItem.Login.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(NavigationItem.Login.route) {
                        popUpTo(NavigationItem.Home.route) { inclusive = true }
                    }
                }
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
            MainScreen(navController, appStateViewModel)
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
