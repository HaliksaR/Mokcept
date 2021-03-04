package ru.haliksar.mokcept.app.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.squareup.moshi.Moshi
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.haliksar.mokcept.app.handlers.GetMethodHandler
import ru.haliksar.mokcept.app.handlers.PostMethodHandler
import ru.haliksar.mokcept.core.Mokcept
import java.io.IOException
import ru.haliksar.mokcept.multimodule.core.Mokcept as MultimoduleMokcept


val FakeNetworkModule = module {

    factory(named(BASE)) {
        provideOkHttpClient(
            interceptors = listOf(
                Mokcept(
                    protocol = Protocol.HTTP_2,
                    handlers = listOf(
                        GetMethodHandler(androidContext()),
                        PostMethodHandler(androidContext())
                    )
                ),
                noConnectionInterceptor(androidContext())
            )
        )
    }

    factory(named(BASE)) {
        provideRetrofit(
            moshi = Moshi.Builder().build(),
            okHttpClient = get(),
            url = "https://www.haliksar.fun"
        )
    }

    factory(named(MULTI_MODULE)) {
        provideOkHttpClient(
            interceptors = listOf(
                MultimoduleMokcept(protocol = Protocol.HTTP_2),
                noConnectionInterceptor(androidContext())
            )
        )
    }

    factory(named(MULTI_MODULE)) {
        provideRetrofit(
            moshi = Moshi.Builder().build(),
            okHttpClient = get(),
            url = "https://www.haliksar.fun"
        )
    }
}

inline fun <reified T> createRetrofitService(retrofit: Retrofit): T =
    retrofit.create(T::class.java)

fun provideRetrofit(
    moshi: Moshi,
    okHttpClient: OkHttpClient,
    url: String
): Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .baseUrl(url)
    .build()

fun provideOkHttpClient(
    interceptors: List<Interceptor> = emptyList(),
    authenticators: List<Authenticator> = emptyList()
): OkHttpClient =
    OkHttpClient.Builder()
        .apply {
            interceptors.forEach { addInterceptor(it) }
            authenticators.forEach { authenticator(it) }
        }
        .build()


class NoConnectivityException : IOException()

internal fun noConnectionInterceptor(context: Context) = Interceptor { chain ->
    if (!isInternetAvailable(context)) {
        throw NoConnectivityException()
    } else {
        chain.proceed(chain.request())
    }
}

@Suppress("DEPRECATION")
private fun isInternetAvailable(context: Context): Boolean {
    with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = activeNetwork ?: return false
            val actNw = getNetworkCapabilities(networkCapabilities) ?: return false
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            when (activeNetworkInfo?.type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
    }
}