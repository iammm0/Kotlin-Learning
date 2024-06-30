import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicistscard.transmissionModels.auth.requests.EmailCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _loginStatus = MutableStateFlow("")

    fun sendVerificationCode(email: String) {
        viewModelScope.launch {
            try {
                val request = SendCodeRequest(identifier = email, operation = "EMAIL_LOGIN")
                val response: SendCodeResponse = repository.sendVerificationCode(request)
                if (response.success) {
                    _loginStatus.value = "验证代码已发送"
                } else {
                    _loginStatus.value = "发送失败: ${response.message}"
                }
            } catch (e: Exception) {
                _loginStatus.value = "发送失败: ${e.message}"
            }
        }
    }

    fun loginWithEmailCode(email: String, emailCode: String) {
        viewModelScope.launch {
            try {
                val request = EmailCodeLoginRequest(email = email, emailCode = emailCode)
                val response: LoginResponse = repository.loginWithEmailCode(request)
                if (response.success) {
                    _loginStatus.value = "登录成功"
                } else {
                    _loginStatus.value = "登录失败: ${response.errorMessage}"
                }
            } catch (e: Exception) {
                _loginStatus.value = "请求失败: ${e.message}"
            }
        }
    }
}
