import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import loki.edu.yogaclassadmin.database.repository.firestore.FirestoreInstanceRepository
import loki.edu.yogaclassadmin.model.YogaClass
import loki.edu.yogaclassadmin.database.repository.firestore.FirestoreYogaClassRepository
import loki.edu.yogaclassadmin.model.ClassType
import loki.edu.yogaclassadmin.model.YogaClassInstance

class YogaClassDetailViewModel : ViewModel() {
    private val repository = FirestoreYogaClassRepository()
    private val instanceRepository = FirestoreInstanceRepository()


    // Yoga detail
    private val _yogaClass = MutableStateFlow<YogaClass?>(null)
    val yogaClass = _yogaClass.asStateFlow()
    private val _yogaType = MutableStateFlow<ClassType?>(null)
    val yogaType = _yogaType.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // Yoga Instances
    private val _yogaClassInstances = MutableStateFlow<List<YogaClassInstance>>(emptyList())
    val yogaClassInstances = _yogaClassInstances.asStateFlow()
    private val _isLoadingGetInstance = MutableStateFlow(false)
    val isLoadingGetInstance = _isLoadingGetInstance.asStateFlow()


    // load detail yoga class
    fun loadYogaClass(yogaClassId: String) {
        Log.e("YogaClassDetailViewModel", "Loading yoga class with ID: $yogaClassId")
        viewModelScope.launch {
            _isLoading.value = true
            val yogaClass = repository.getYogaClassById(yogaClassId)
            _yogaClass.value =  yogaClass
            _yogaType.value = repository.getClassTypeById(yogaClass?.classType.toString())
            _isLoading.value = false
        }
    }



    // get all instance belong to yoga class
    fun loadYogaClassInstances(yogaClassId: String) {
        viewModelScope.launch {
            _isLoadingGetInstance.value = true
            _yogaClassInstances.value = instanceRepository.getAllInstancesByClassId(yogaClassId)
            _isLoadingGetInstance.value = false
        }
    }
}
