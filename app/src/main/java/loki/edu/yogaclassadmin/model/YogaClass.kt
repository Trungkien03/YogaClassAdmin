package loki.edu.yogaclassadmin.model

// đại diện cho thông tin của một lớp học yoga.
data class YogaClass(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val imageUrl: String? = null,
    val time: String = "",
    val capacity: Int = 0,
    val classType: String = "",
    val price: Double = 0.0,
    val description: String? = null
)
