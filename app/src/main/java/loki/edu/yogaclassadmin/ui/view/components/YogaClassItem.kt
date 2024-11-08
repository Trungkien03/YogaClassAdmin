import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import loki.edu.yogaclassadmin.R
import loki.edu.yogaclassadmin.model.YogaClass

@Composable
fun YogaClassItem(yogaClass: YogaClass, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = yogaClass.imageUrl ?: R.drawable.yoga_logo,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = yogaClass.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1
            )
            Text(
                text = "Instructor: ${yogaClass.classType}",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                maxLines = 1
            )
            Text(
                text = "${yogaClass.date} • ${yogaClass.time}",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                maxLines = 1
            )
        }
    }
}



