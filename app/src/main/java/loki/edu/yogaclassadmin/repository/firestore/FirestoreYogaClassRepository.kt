package loki.edu.yogaclassadmin.repository.firestore


import com.google.firebase.firestore.FirebaseFirestore
import loki.edu.yogaclassadmin.model.YogaClass

class FirestoreYogaClassRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val classCollection = firestore.collection("yoga_classes")

    fun addClass(yogaClass: YogaClass, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        classCollection.document(yogaClass.id)
            .set(yogaClass)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getClasses(onSuccess: (List<YogaClass>) -> Unit, onFailure: (Exception) -> Unit) {
        classCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val classes = querySnapshot.toObjects(YogaClass::class.java)
                onSuccess(classes)
            }
            .addOnFailureListener { onFailure(it) }
    }

    // Other methods for updating and deleting classes in Firestore
}
