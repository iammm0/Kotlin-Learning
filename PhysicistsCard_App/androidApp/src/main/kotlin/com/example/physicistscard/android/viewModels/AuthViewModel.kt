import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicistscard.apiServices.IAuthApiService
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authApiService: IAuthApiService
) : ViewModel() {

    // Login state management
    private val _loginState = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginState: StateFlow<Result<LoginResponse>?> = _loginState

    // Registration state management
    private val _registerState = MutableStateFlow<Result<LoginResponse>?>(null)
    val registerState: StateFlow<Result<LoginResponse>?> = _registerState

    // Password reset state
    private val _resetPasswordState = MutableStateFlow<Result<Boolean>?>(null)
    val resetPasswordState: StateFlow<Result<Boolean>?> = _resetPasswordState

    // Verification code sending state
    private val _sendCodeState = MutableStateFlow<Result<Boolean>?>(null)
    val sendCodeState: StateFlow<Result<Boolean>?> = _sendCodeState

    // 邮箱验证码登录状态
    private val _loginWithEmailCodeState = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginWithEmailCodeState: StateFlow<Result<LoginResponse>?> = _loginWithEmailCodeState

    // 手机验证码登录状态
    private val _loginWithPhoneCodeState = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginWithPhoneCodeState: StateFlow<Result<LoginResponse>?> = _loginWithPhoneCodeState


    // 使用邮箱验证码登录
    fun loginWithEmailCode(email: String, code: String) {
        viewModelScope.launch {
            _loginWithEmailCodeState.value = try {
                val response = authApiService.loginWithVerificationCode(email, code)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 使用手机验证码登录
    fun loginWithPhoneCode(phone: String, code: String) {
        viewModelScope.launch {
            _loginWithPhoneCodeState.value = try {
                val response = authApiService.loginWithVerificationCode(phone, code)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Login with password
    fun loginWithPassword(identifier: String, password: String) {
        viewModelScope.launch {
            _loginState.value = try {
                val response = authApiService.loginWithPassword(identifier, password)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Login with verification code
    fun loginWithVerificationCode(email: String, code: String) {
        viewModelScope.launch {
            _loginState.value = try {
                val response = authApiService.loginWithVerificationCode(email, code)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Register new user
    fun registerUser(registrationRequest: RegistrationRequest) {
        viewModelScope.launch {
            _registerState.value = try {
                val response = authApiService.registerUser(registrationRequest)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Reset password
    fun resetPassword(identifier: String, newPassword: String, code: String) {
        viewModelScope.launch {
            _resetPasswordState.value = try {
                val success = authApiService.resetPassword(identifier, newPassword, code)
                Result.success(success)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Send verification code
    fun sendVerificationCode(identifier: String, operation: String) {
        viewModelScope.launch {
            _sendCodeState.value = try {
                authApiService.sendVerificationCode(identifier, SendCodeRequest(identifier, operation))
                Result.success(true)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // Clear state functions (optional)
    fun clearLoginState() {
        _loginState.value = null
    }

    fun clearRegisterState() {
        _registerState.value = null
    }

    fun clearResetPasswordState() {
        _resetPasswordState.value = null
    }

    fun clearSendCodeState() {
        _sendCodeState.value = null
    }
}

