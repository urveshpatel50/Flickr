package au.com.deloitte.flickr.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Factory {

    private fun retrofitClient() = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/services/rest/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder().connectTimeout(
                10,
                TimeUnit.SECONDS
            ).addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }).build()

        ).build()


    fun service() = retrofitClient().create(ApiService::class.java)
}