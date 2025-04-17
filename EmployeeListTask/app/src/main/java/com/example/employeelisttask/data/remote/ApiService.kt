package com.example.employeelisttask.data.remote

import com.example.employeelisttask.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UserResponse>
}
