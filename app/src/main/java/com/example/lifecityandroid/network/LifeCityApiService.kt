package com.example.lifecityandroid.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


private const val BASE_URL = "https://192.168.0.184:5001"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
private val retrofit2 = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
interface LifeCityApiService {
    @GET("api/hulpbronnen")
    suspend fun gethulpbronnen():
            List<HulpbronProperty>

    @POST("api/Account")
    fun logIn(@Body request: LoginProperty): Call<String>

    @POST("api/Account/register")
    fun Register(@Body RegisterData: RegisterProperty): Call<String>
}

object LoginApi{
    val retrofitService : LifeCityApiService by lazy {
        retrofit2.create(LifeCityApiService::class.java)
    }
}