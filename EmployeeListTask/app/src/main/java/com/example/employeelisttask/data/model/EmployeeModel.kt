package com.example.employeelisttask.data.model

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<Employee>
)

data class Employee(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String
)
