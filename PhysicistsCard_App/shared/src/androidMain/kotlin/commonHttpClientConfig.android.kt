import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp

actual fun createCommonHttpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(OkHttp) {
        commonHttpClientConfig(config)
    }
}