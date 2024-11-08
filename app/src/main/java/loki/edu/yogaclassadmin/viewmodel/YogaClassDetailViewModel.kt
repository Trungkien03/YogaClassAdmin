import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import loki.edu.yogaclassadmin.model.YogaClass
import loki.edu.yogaclassadmin.database.repository.firestore.FirestoreYogaClassRepository

class YogaClassDetailViewModel : ViewModel() {
    private val repository = FirestoreYogaClassRepository()

    private val _yogaClass = MutableStateFlow<YogaClass?>(null)
    val yogaClass = _yogaClass.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadYogaClass(yogaClassId: String) {
        Log.e("YogaClassDetailViewModel", "Loading yoga class with ID: $yogaClassId")
        viewModelScope.launch {
            _isLoading.value = true
            _yogaClass.value = repository.getYogaClassById(yogaClassId)
            _isLoading.value = false
        }
    }
}
