import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicistscard.businessLogic.IAuthService
import com.example.physicistscard.transmissionModels.auth.requests.EmailCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authService: IAuthService) : ViewModel() {
    private val _loginStatus = MutableStateFlow("")
    val loginStatus: StateFlow<String> = _loginStatus

    fun sendVerificationCode(email: String) {
        viewModelScope.launch {
            try {
                val request = SendCodeRequest(identifier = email, operation = "email_register")
                val response: SendCodeResponse = authService.sendVerificationCode(request)
                if (response.success) {
                    _loginStatus.value = "Verification code sent"
                } else {
                    _loginStatus.value = "Send failed: ${response.message}"
                }
            } catch (e: Exception) {
                _loginStatus.value = "Send failed: ${e.message}"
            }
        }
    }

    fun loginWithEmailCode(email: String, emailCode: String) {
        viewModelScope.launch {
            try {
                val request = EmailCodeLoginRequest(email = email, emailCode = emailCode)
                val response: LoginResponse = authService.emailCodeLogin(request)
                if (response.success) {
                    _loginStatus.value = "Login successful"
                } else {
                    _loginStatus.value = "Login failed: ${response.errorMessage}"
                }
            } catch (e: Exception) {
                _loginStatus.value = "Request failed: ${e.message}"
            }
        }
    }
}
