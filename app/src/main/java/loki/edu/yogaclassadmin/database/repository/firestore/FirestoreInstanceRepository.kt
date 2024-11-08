package loki.edu.yogaclassadmin.database.repository.firestore

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
            false
        }
    }

    suspend fun getInstanceById(instanceId: String): YogaClassInstance? {
        return try {
            instanceCollection.document(instanceId).get().await().toObject(YogaClassInstance::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateInstance(instance: YogaClassInstance): Boolean {
        return try {
            instanceCollection.document(instance.id).set(instance).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteInstance(instanceId: String): Boolean {
        return try {
            instanceCollection.document(instanceId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}