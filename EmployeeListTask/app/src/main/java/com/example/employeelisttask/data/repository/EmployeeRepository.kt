package com.example.employeelisttask.data.repository

import com.example.employeelisttask.data.model.Employee
import com.example.employeelisttask.data.remote.RetrofitClient

class EmployeeRepository {
    private val api = RetrofitClient.apiService
    suspend fun fetchUsers(page: Int): List<Employee> {
        val response = api.getUsers(page)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Failed to fetch users")
        }
    }
}