import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import loki.edu.yogaclassadmin.model.YogaClass
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.Brush
import loki.edu.yogaclassadmin.model.ClassType
import loki.edu.yogaclassadmin.model.YogaClassInstance
import loki.edu.yogaclassadmin.ui.view.components.BackButton
import loki.edu.yogaclassadmin.ui.view.components.YogaClassInstanceItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YogaClassDetailScreen(
    navController: NavController,
    yogaClassId: String,
    viewModel: YogaClassDetailViewModel = viewModel()
) {
    val yogaClass by viewModel.yogaClass.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val yogaClassInstances by viewModel.yogaClassInstances.collectAsState()
    val yogaClassType by viewModel.yogaType.collectAsState()

    LaunchedEffect(yogaClassId) {
        viewModel.loadYogaClass(yogaClassId)
        viewModel.loadYogaClassInstances(yogaClassId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            yogaClass != null -> {
                YogaClassDetailContent(
                    navController = navController,
                    yogaClass = yogaClass!!,
                    yogaClassInstances = yogaClassInstances,
                    yogaClassType = yogaClassType!!
                )
            }
            else -> {
                Text(
                    text = "Error loading class details",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun YogaClassDetailContent(
    navController: NavController,
    yogaClass: YogaClass,
    yogaClassInstances: List<YogaClassInstance>,
    yogaClassType: ClassType
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.height(250.dp)) {
            AsyncImage(
                model = yogaClass.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                BackButton(navController = navController)
                IconButton(
                    onClick = { /* Handle favorite action */ },
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                        .size(36.dp)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Favorite",
                        tint = Color.Black
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                        )
                    )
            ) {
                Text(
                    text = yogaClass.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = yogaClass.title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )


            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "${yogaClass.capacity} Capacity", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
                Text(text = "${yogaClassType.name}", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
                Text(text = "312 kcal", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
            }



            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )
            Text(
                text = yogaClass.description ?: "No description available",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Instances
            Text(
                text = "Instances",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Black
            )
            if (yogaClassInstances.isEmpty()) {
                Text(
                    text = "No sessions available for this class.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(yogaClassInstances.size) { it ->
                        val item = yogaClassInstances.get(it)
                        YogaClassInstanceItem(instance = item)
                    }

                }
            }

        }
    }

}

