package cz.ackee.skeleton.model.api

import android.content.Context
import cz.ackee.skeleton.BuildConfig
import cz.ackee.useragent.UserAgent
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.Version
import java.io.IOException

/**
 * Interceptor that inserts headers that ackee backends uses for logging - application version and Ackee User Agent
 */
class AckeeInterceptor(val context: Context, val deviceUuid: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        return chain.proceed(originalRequest
            .newBuilder()
            .header("X-Version", BuildConfig.VERSION_CODE.toString())
            .header("User-Agent", UserAgent.getInstance(context).getUserAgentString(Version.userAgent()))
            .header("X-Device-Id", deviceUuid)
            .build())
    }
}
