package loki.edu.yogaclassadmin.model

// quản lý các loại lớp học riêng biệt
data class ClassType(
    val id: String = "",
    val name: String = "",               // Tên loại lớp học
    val description: String? = null      // Mô tả chi tiết (tùy chọn)
)
