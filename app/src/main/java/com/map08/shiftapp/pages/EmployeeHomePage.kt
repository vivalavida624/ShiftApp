package com.map08.shiftapp.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.map08.shiftapp.AuthViewModel
import com.map08.shiftapp.EmployeeNavItem

@Composable
fun EmployeeHomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val employeeNavItemList = listOf(
        EmployeeNavItem("Time", Icons.Default.DateRange),
        EmployeeNavItem("Home", Icons.Default.Home),
        EmployeeNavItem("Profile", Icons.Default.Person),
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
    ){ innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), navController, authViewModel,selectedIndex)
        
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel,selectedIndex: Int) {
    when(selectedIndex) {
        0-> EmployeeTimePage()
//        1-> EmployeeHomePage(modifier, navController, authViewModel)
        2-> EmployeeProfilePage()
    }


}