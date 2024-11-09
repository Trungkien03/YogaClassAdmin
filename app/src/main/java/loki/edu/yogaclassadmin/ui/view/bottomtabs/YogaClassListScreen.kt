import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import loki.edu.yogaclassadmin.ui.navigation.NavigationItem
import loki.edu.yogaclassadmin.viewmodel.YogaClassViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YogaClassListScreen(
    navController: NavController,
    viewModel: YogaClassViewModel = viewModel()
) {
    val yogaClasses = viewModel.yogaClasses.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF4CAF50), // Green color to match your theme
                contentColor = Color.White,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add New Class")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
                .fillMaxSize(),

        ) {
            Text(
                text = "Yoga Classes",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (yogaClasses.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No classes available",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(yogaClasses.size) { index ->
                        YogaClassItem(
                            yogaClass = yogaClasses[index],
                            onClick = { navController.navigate(NavigationItem.Detail.createRoute(yogaClasses[index].id)) }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add New Class") },
            text = { Text("Do you want to add a new yoga class?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate(NavigationItem.AddNewClass.route)
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

