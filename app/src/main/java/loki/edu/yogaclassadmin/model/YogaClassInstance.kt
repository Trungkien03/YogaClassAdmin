package loki.edu.yogaclassadmin.model

// lớp YogaClassInstance.kt để biểu diễn các phiên học của từng lớp học.
data class YogaClassInstance(
    val id: String = "",                 // ID duy nhất của phiên học
    val classId: String = "",            // ID của lớp học liên kết
    val instanceDate: String = "",       // Ngày diễn ra phiên học
    val instructor: String = "",         // Giảng viên phụ trách
    val notes: String? = null            // Ghi chú bổ sung (tùy chọn)
)
