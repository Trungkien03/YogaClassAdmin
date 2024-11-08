package loki.edu.yogaclassadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import loki.edu.yogaclassadmin.ui.navigation.AppNavHost
import loki.edu.yogaclassadmin.ui.navigation.NavigationItem
import loki.edu.yogaclassadmin.ui.navigation.Screen
import loki.edu.yogaclassadmin.ui.theme.YogaClassAdminTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)

        setContent {


            YogaClassAdminTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YogaClassAdminTheme() {
        val navController = rememberNavController()
        AppNavHost(navController = navController)
    }
}
