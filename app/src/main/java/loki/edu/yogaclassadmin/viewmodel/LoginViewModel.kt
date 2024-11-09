import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import loki.edu.yogaclassadmin.model.SignInResult
import loki.edu.yogaclassadmin.model.User
import loki.edu.yogaclassadmin.database.repository.firestore.UserRepository
import loki.edu.yogaclassadmin.ui.state.SignInState

class LoginViewModel : ViewModel() {
    private val userRepository: UserRepository = UserRepository()
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    private val _stateGoogleSign = MutableStateFlow(SignInState())

    val passwordVisible = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val currentUser = mutableStateOf<User?>(null)
    val errorMessage = mutableStateOf("")

    fun setErrorMessage(message: String) {
        errorMessage.value = message
    }


    fun setIsLoading(value: Boolean) {
        isLoading.value = value
    }


    fun onSignInResult(result: SignInResult) {
        _stateGoogleSign.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage,
                isLoading = false
            )
        }
    }

    fun resetState() {
        _stateGoogleSign.update { SignInState() }
    }

    fun loginUser(onResult: (Boolean) -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            val isAuthenticated = userRepository.loginUser(email.value, password.value)
            isLoading.value = false
            if (!isAuthenticated) {
                errorMessage.value = "Invalid email or password."
                onResult(false)
            } else {
                val user = userRepository.getUserByEmail(email.value)
                currentUser.value = user
                onResult(true)
            }
        }
    }
    fun onSignInResult(result: SignInResult, onResult: (Boolean) -> Unit) {

        _stateGoogleSign.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage,
                isLoading = false
            )
        }

        if (result.data != null) {
            checkUserInDatabaseAfterGoogleSignIn(result.data.email.toString(),onResult)
        } else {
            setErrorMessage(result.errorMessage ?: "Unknown sign-in error")
        }
    }

     fun checkUserInDatabaseAfterGoogleSignIn(email: String,onResult: (Boolean) -> Unit) {
        isLoading.value = true // Bắt đầu loading
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)

            isLoading.value = false // Kết thúc loading
            if (user == null) {
                setErrorMessage("User not found in the database.")
                onResult(false)
            } else {
                currentUser.value = user
                onResult(true)
            }
        }
    }
}
