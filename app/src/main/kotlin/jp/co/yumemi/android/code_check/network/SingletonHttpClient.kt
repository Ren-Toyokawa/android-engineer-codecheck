package jp.co.yumemi.android.code_check.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

// FIXME: Hiltを使ってDIする方がいい。今は時間がないため object で代用
object HttpClientSingleton {
    val client: HttpClient = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}
