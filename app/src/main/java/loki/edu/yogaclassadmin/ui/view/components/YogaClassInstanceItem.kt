package loki.edu.yogaclassadmin.ui.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import loki.edu.yogaclassadmin.model.YogaClassInstance

@Composable
fun YogaClassInstanceItem(instance: YogaClassInstance) {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = instance.title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Date: ${instance.instanceDate}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(
            text = instance.description,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}