package loki.edu.yogaclassadmin.database.repository.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import loki.edu.yogaclassadmin.model.ClassType
import loki.edu.yogaclassadmin.model.User
import loki.edu.yogaclassadmin.model.YogaClass

class FirestoreYogaClassRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val classCollection = firestore.collection("yoga_classes")
    private val classTypesCollection = firestore.collection("ClassTypes")

    // Thêm một ClassType mới vào Firestore
    suspend fun addClassType(classType: ClassType): Boolean {
        return try {
            classTypesCollection.document(classType.id).set(classType).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Thêm một YogaClass mới vào Firestore với callback cho thành công/thất bại
    fun addClass(yogaClass: YogaClass, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        classCollection.document(yogaClass.id)
            .set(yogaClass)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // Lấy tất cả các YogaClass từ Firestore với callback cho thành công/thất bại
    fun getClasses(onSuccess: (List<YogaClass>) -> Unit, onFailure: (Exception) -> Unit) {
        classCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val classes = querySnapshot.toObjects(YogaClass::class.java)
                onSuccess(classes)
            }
            .addOnFailureListener { onFailure(it) }
    }



    // Xóa YogaClass khỏi Firestore
    suspend fun deleteClass(classId: String): Boolean {
        return try {
            classCollection.document(classId).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Lấy tất cả các ClassType từ Firestore với callback
    fun getClassTypes(onSuccess: (List<ClassType>) -> Unit, onFailure: (Exception) -> Unit) {
        classTypesCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val classTypes = querySnapshot.toObjects(ClassType::class.java)
                onSuccess(classTypes)
            }
            .addOnFailureListener { onFailure(it) }
    }

    // Hàm để lấy YogaClass theo ID
    suspend fun getYogaClassById(yogaClassId: String): YogaClass? {
        return try {
            val document = classCollection
                .whereEqualTo("id", yogaClassId)
                .get()
                .await()
            document.documents.firstOrNull()?.toObject(YogaClass::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
