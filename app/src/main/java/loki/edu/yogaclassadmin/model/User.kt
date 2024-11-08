package loki.edu.yogaclassadmin.model

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val profileImage: String = "",
    val role: String = "",
    val status: String = "active",
    val specialization: String? = null,
    val bio: String? = null,
    val contact: String? = null
)
