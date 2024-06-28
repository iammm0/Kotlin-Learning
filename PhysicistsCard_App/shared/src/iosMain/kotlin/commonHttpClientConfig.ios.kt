import io.ktor.client.*
import io.ktor.client.engine.darwin.*

actual fun createCommonHttpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(Darwin) {
        commonHttpClientConfig(config)
    }
}