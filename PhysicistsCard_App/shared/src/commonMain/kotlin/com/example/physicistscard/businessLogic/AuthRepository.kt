import com.example.physicistscard.apiServices.AuthApiService
import com.example.physicistscard.transmissionModels.auth.requests.EmailCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.PhoneCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.ResetPasswordRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.RegistrationResponse
import com.example.physicistscard.transmissionModels.auth.responses.ResetPasswordResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse

class AuthRepository(private val apiService: AuthApiService) {
    suspend fun registerUser(request: RegistrationRequest): RegistrationResponse {
        return apiService.registerUser(request)
    }

    suspend fun loginWithEmailCode(request: EmailCodeLoginRequest): LoginResponse {
        return apiService.loginWithEmailCode(request)
    }

    suspend fun loginWithPhoneCode(request: PhoneCodeLoginRequest): LoginResponse {
        return apiService.loginWithPhoneCode(request)
    }

    suspend fun sendVerificationCode(request: SendCodeRequest): SendCodeResponse {
        return apiService.sendVerificationCode(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse {
        return apiService.resetPassword(request)
    }
}
