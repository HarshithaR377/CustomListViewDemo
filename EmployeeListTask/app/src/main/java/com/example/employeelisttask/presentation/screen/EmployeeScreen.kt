package com.example.employeelisttask.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.employeelisttask.presentation.components.EmployeeCard
import com.example.employeelisttask.presentation.viewmodel.EmployeeViewModel

@Composable
fun EmployeeScreen(employeeViewmodel: EmployeeViewModel = viewModel()) {
    val searchQuery = remember { mutableStateOf("") }
    val showSearchListener = remember { mutableStateOf(false) }
    val employees = employeeViewmodel.employees
    val isLoader = employeeViewmodel.isLoader
    val filteredUsers = if (searchQuery.value.isBlank()) employees else employees.filter {
        it.first_name.contains(searchQuery.value, ignoreCase = true)
    }
    val scaffoldBackgroundColor = Color(0xFFF0F8FF) // Soft Blue color for Scaffold background
    Scaffold(
        modifier = Modifier.background(scaffoldBackgroundColor),
        topBar = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Employee List",
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = TextStyle(textAlign = TextAlign.Center),
                        fontWeight = FontWeight.SemiBold
                    )

                    IconButton(onClick = {
                        showSearchListener.value = !showSearchListener.value
                        if (!showSearchListener.value) {
                            searchQuery.value = ""
                        }
                    }) {
                        Icon(
                            imageVector = if (showSearchListener.value) Icons.Filled.Close else Icons.Filled.Search,
                            contentDescription = "Toggle Search"
                        )
                    }
                }

                // Show search bar is visible only if `showSearch` is true
                if (showSearchListener.value) {
                    OutlinedTextField(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        label = { Text("Search by First Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF5A9BD5)) //Soft Blue background for the content
        ) {

            if (filteredUsers.isEmpty() && !isLoader.value) {
                Text(
                    "No results found",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filteredUsers) { user ->
                    EmployeeCard(user)
                }

                if (searchQuery.value.isBlank()) {
                    item {
                        if (employees.isNotEmpty() && !isLoader.value && employeeViewmodel.currentPage < employeeViewmodel.totalPages) {
                            Button(
                                onClick = { employeeViewmodel.fetchUsers() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text("Load More")
                            }
                        } else if (!isLoader.value) {
                            Text(
                                "No more data",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center,
                                style = TextStyle(textAlign = TextAlign.Center)
                            )
                        }
                    }
                }
            }

            if (isLoader.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

