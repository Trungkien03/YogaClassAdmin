package loki.edu.yogaclassadmin.database.repository.firestore


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import loki.edu.yogaclassadmin.model.User

class UserRepository() {
    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun addUser(user: User): Boolean {
        return try {
            userCollection.document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            val querySnapshot = userCollection
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .await()

            querySnapshot.documents.isNotEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getUserById(userId: String): User? {
        return try {
            userCollection.document(userId).get().await().toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }


    suspend fun getUserByEmail(email: String): User? {
        return try {
            val querySnapshot = userCollection
                .whereEqualTo("email", email)
                .get()
                .await()


            querySnapshot.documents.forEach { document ->
                Log.d("FirestoreQuery", "Document data: ${document.data}")
            }

            querySnapshot.documents.firstOrNull()?.toObject(User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("FirestoreQuery", "Error fetching user by email: ${e.message}")
            null
        }
    }




    suspend fun updateUser(user: User): Boolean {
        return try {
            userCollection.document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteUser(userId: String): Boolean {
        return try {
            userCollection.document(userId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
