package com.planetsistemas.login.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
//import retrofit2.http.Path
//import retrofit2.http.Query

private const val BASE_URL = "https://dummyjson.com"

interface IApiService {
    @GET("/products")
    suspend fun getAll(
        // var on path: path/{name}/
        //@Path("name") name: String,
        // params: page.com?api_key={api_key}
        //@Query("api_key") apiKey: String,
    ): Result // async
}

object ApiService {
    fun makeIAPiService(): IApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // json to object
            .build()
            .create(IApiService::class.java)
    }
}