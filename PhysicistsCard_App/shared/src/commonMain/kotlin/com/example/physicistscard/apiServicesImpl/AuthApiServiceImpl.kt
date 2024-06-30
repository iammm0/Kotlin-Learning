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
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApiServiceImpl(private val client: HttpClient) : AuthApiService {
    private val baseUrl = "http://127.0.0.1:8080/user"

    override suspend fun registerUser(request: RegistrationRequest): RegistrationResponse {
        return client.post("$baseUrl/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun loginWithEmailCode(request: EmailCodeLoginRequest): LoginResponse {
        return client.post("$baseUrl/email-code-login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun loginWithPhoneCode(request: PhoneCodeLoginRequest): LoginResponse {
        return client.post("$baseUrl/phone-code-login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun sendVerificationCode(request: SendCodeRequest): SendCodeResponse {
        return client.post("$baseUrl/send-code") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse {
        return client.post("$baseUrl/password/change-password") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
