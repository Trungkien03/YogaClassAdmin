package loki.edu.yogaclassadmin.repository.firestore


import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import loki.edu.yogaclassadmin.model.YogaClass

class FirestoreClassesRepository(private val firestore: FirebaseFirestore) {

    private val classCollection = firestore.collection("classes")

    suspend fun addClass(yogaClass: YogaClass): Boolean {
        return try {
            classCollection.document(yogaClass.id).set(yogaClass).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getClassById(classId: String): YogaClass? {
        return try {
            classCollection.document(classId).get().await().toObject(YogaClass::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateClass(yogaClass: YogaClass): Boolean {
        return try {
            classCollection.document(yogaClass.id).set(yogaClass).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteClass(classId: String): Boolean {
        return try {
            classCollection.document(classId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
