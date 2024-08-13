import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicistscard.apiServices.IAuthApiService
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authApiService: IAuthApiService) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginState: StateFlow<Result<LoginResponse>?> = _loginState

    private val _registerState = MutableStateFlow<Result<LoginResponse>?>(null)
    val registerState: StateFlow<Result<LoginResponse>?> = _registerState

    fun loginWithPassword(identifier: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authApiService.loginWithPassword(identifier, password)
                _loginState.value = Result.success(response)
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
        }
    }

    fun loginWithVerificationCode(email: String, code: String) {
        viewModelScope.launch {
            try {
                val response = authApiService.loginWithVerificationCode(email, code)
                _loginState.value = Result.success(response)
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
        }
    }

    fun registerUser(registrationRequest: RegistrationRequest) {
        viewModelScope.launch {
            try {
                val response = authApiService.registerUser(registrationRequest)
                _registerState.value = Result.success(response)
            } catch (e: Exception) {
                _registerState.value = Result.failure(e)
            }
        }
    }

    fun resetPassword(identifier: String, newPassword: String, code: String) {
        viewModelScope.launch {
            try {
                val success = authApiService.resetPassword(identifier, newPassword, code)
                if (success) {
                    // Handle success
                } else {
                    // Handle failure
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun sendVerificationCode(identifier: String) {
        viewModelScope.launch {
            try {
                val response = authApiService.sendVerificationCode(identifier, SendCodeRequest(identifier))
                // Handle success
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
