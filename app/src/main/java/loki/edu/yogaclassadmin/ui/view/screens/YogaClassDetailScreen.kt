import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YogaClassDetailScreen(
    navController: NavController,
    yogaClassId: String,
    viewModel: YogaClassDetailViewModel = viewModel() // Sử dụng viewModel() để khởi tạo
) {
    val yogaClass by viewModel.yogaClass.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(yogaClassId) {
        viewModel.loadYogaClass(yogaClassId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Class Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Áp dụng padding từ Scaffold
        ) {
            when {
                isLoading -> {
                    // Hiển thị hiệu ứng loading
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                yogaClass != null -> {
                    YogaClassDetailContent(yogaClass = yogaClass!!)
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
}

@Composable
fun YogaClassDetailContent(yogaClass: YogaClass) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box {
            AsyncImage(
                model = yogaClass.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
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

        Spacer(modifier = Modifier.height(16.dp))

        // Thông tin chi tiết lớp học
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Instructor: ${yogaClass.classType}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = Color.Gray
            )

            Text(
                text = "Date: ${yogaClass.date} • Time: ${yogaClass.time}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Capacity: ${yogaClass.capacity} students",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Price: $${yogaClass.price}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = yogaClass.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
