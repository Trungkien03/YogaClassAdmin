package loki.edu.yogaclassadmin.model

//  lưu trữ thông tin đặt chỗ của người dùng.
data class Booking(
    val id: String = "",
    val instanceId: String = "",         // ID của phiên học đã đặt
    val userEmail: String = "",          // Email của người dùng đặt chỗ
    val bookingDate: String = "",        // Ngày đặt chỗ
    val status: String = "confirmed"     // Trạng thái đặt chỗ (Confirmed, Cancelled)
)
