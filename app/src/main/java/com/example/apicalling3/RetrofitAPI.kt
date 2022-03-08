package com.example.apicalling3

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("users")
    open fun createPost(@Body dataModal: DataModal?): Call<DataModal?>?
}