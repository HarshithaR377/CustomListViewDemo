package com.example.employeelisttask.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeelisttask.data.model.Employee
import com.example.employeelisttask.data.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel : ViewModel() {
    private val repository = EmployeeRepository()
    var employees = mutableStateListOf<Employee>()
    var isLoader = mutableStateOf(false)
    var error = mutableStateOf<String?>("")
    var currentPage = 1
    var totalPages = Int.MAX_VALUE

    init {
        fetchUsers()
    }

    fun fetchUsers(page: Int = currentPage) {
        if (page > totalPages) return
        viewModelScope.launch {
            isLoader.value = true
            try {
                val result = repository.fetchUsers(page)
                if (result.isNotEmpty()) {
                    employees.addAll(result)
                    currentPage++
                } else {
                    totalPages = page - 1
                }
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                isLoader.value = false
            }
        }
    }
}

