package com.example.employeelisttask.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.employeelisttask.presentation.screen.EmployeeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeListTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    EmployeeScreen(employeeViewmodel = viewModel())
                }
            }
        }
    }
}



