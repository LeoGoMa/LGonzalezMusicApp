package com.example.lgonzalezmusicapp

import android.app.Application
import coil.Coil
import coil.ImageLoader
import okhttp3.OkHttpClient

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .header("User-Agent", "MusicApp/1.0")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }
}