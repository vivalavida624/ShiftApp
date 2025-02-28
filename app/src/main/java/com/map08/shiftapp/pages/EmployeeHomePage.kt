package com.map08.shiftapp.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.map08.shiftapp.EmployeeNavItem
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeHomePage() {

    val navController = LocalNavController.current
    val authViewModel = LocalAuthViewModel.current

    val employeeNavItemList = listOf(
        EmployeeNavItem("Time", Icons.Default.DateRange),
        EmployeeNavItem("Home", Icons.Default.Home),
        EmployeeNavItem("Profile", Icons.Default.Person),
    )

    var selectedIndex by remember { mutableIntStateOf(1) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Employee", fontSize = 20.sp)
                },
                actions = {
                    TextButton(
                        onClick = {
                            authViewModel.signout()
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                    ) {
                        Text(text = "Log Out")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                employeeNavItemList.forEachIndexed { index, employeeNavItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = employeeNavItem.icon, contentDescription = "Icon")
                        },
                        label = {
                            Text(text = employeeNavItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        EmployeeContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}

@Composable
fun EmployeeContentScreen( modifier: Modifier,selectedIndex: Int) {
    when (selectedIndex) {
        0 -> EmployeeTimePage()
        1 -> Text(text = "Home Content", modifier = Modifier.fillMaxSize())
        2 -> EmployeeProfilePage()
    }
}
