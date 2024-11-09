package loki.edu.yogaclassadmin.database.repository.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import loki.edu.yogaclassadmin.model.YogaClassInstance


class FirestoreInstanceRepository ( ) {

    private val firestore = FirebaseFirestore.getInstance()
    private val instanceCollection = firestore.collection("instances")


    suspend fun addInstance(instance: YogaClassInstance): Boolean {
        return try {
            instanceCollection.document(instance.id).set(instance).await()
            true
        } catch (e: Exception) {
            Log.e("FirestoreInstanceRepository", "Error fetching instances: ${e.message}")
            false
        }
    }

    suspend fun getAllInstancesByClassId(classId: String): List<YogaClassInstance> {
        return try {
            val querySnapshot = instanceCollection.whereEqualTo("classId", classId).get().await()
            querySnapshot.toObjects(YogaClassInstance::class.java)
        } catch (e: Exception) {
            Log.e("FirestoreInstanceRepository", "Error fetching instances: ${e.message}")
            emptyList()
        }
    }

    suspend fun getInstanceById(instanceId: String): YogaClassInstance? {
        return try {
            instanceCollection.document(instanceId).get().await().toObject(YogaClassInstance::class.java)
        } catch (e: Exception) {
            Log.e("FirestoreInstanceRepository", "Error fetching instances: ${e.message}")
            null
        }
    }

    suspend fun updateInstance(instance: YogaClassInstance): Boolean {
        return try {
            instanceCollection.document(instance.id).set(instance).await()
            true
        } catch (e: Exception) {
            Log.e("FirestoreInstanceRepository", "Error fetching instances: ${e.message}")
            false
        }
    }

    suspend fun deleteInstance(instanceId: String): Boolean {
        return try {
            instanceCollection.document(instanceId).delete().await()
            true
        } catch (e: Exception) {
            Log.e("FirestoreInstanceRepository", "Error fetching instances: ${e.message}")
            false
        }
    }
}