package loki.edu.yogaclassadmin.model



sealed class User(
    open val id: String = "",
    open val email: String = "",          // Email used for login
    open val name: String = "",           // User's name
    open val password: String = "",       // Password (not used in this example)
    open val role: String,                // Role (instructor, student, admin)
    open val status: String = "active"    // Status (e.g., active, inactive, pending)
) {
    data class Instructor(
        override val id: String = "",
        override val email: String = "",
        override val name: String = "",
        val specialization: String? = null,
        val bio: String? = null,
        val contact: String? = null,
        override val status: String = "active" // Status specific to Instructor
    ) : User(id, email, name, role = "instructor", status = status)

    data class Student(
        override val id: String = "",
        override val email: String = "",
        override val name: String = "",
        override val status: String = "active" // Status specific to Student
    ) : User(id, email, name, role = "student", status = status)

    data class Admin(
        override val id: String = "",
        override val email: String = "",
        override val name: String = "",
        override val status: String = "active" // Status specific to Admin
    ) : User(id, email, name, role = "admin", status = status)
}

