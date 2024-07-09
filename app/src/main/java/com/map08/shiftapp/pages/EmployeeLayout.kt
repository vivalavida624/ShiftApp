package com.map08.shiftapp.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.map08.shiftapp.EmployeeNavItem
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.R

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
                modifier = Modifier.height(60.dp), // 设置 TopAppBar 的高度
                title = {
                    Text(
                        text = "Employee",
                        fontSize = 24.sp, // 更大的字体尺寸
                        fontWeight = FontWeight.Bold, // 加粗
                        color = Color(0xFF44474f)
                    )
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
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "Log Out",
                            tint = Color(0xFF44474f)
                        )
                        Text(
                            text = "Log Out",
                            fontSize = 18.sp, // 中等字体尺寸
                            fontWeight = FontWeight.Medium, // 中等粗细
                            color = Color(0xFF44474f),
                            modifier = Modifier.padding(start = 8.dp) // 图标与文字间距
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFebecf4) // 设置 TopAppBar 的背景色
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(65.dp) // 设置导航栏的高度
            ) {
                employeeNavItemList.forEachIndexed { index, employeeNavItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = employeeNavItem.icon, contentDescription = "Icon")
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }
    ) { innerPadding ->
        EmployeeContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}

@Composable
fun EmployeeContentScreen(modifier: Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> EmployeeTimePage(modifier)
        1 -> EmployeeHomeScreen(modifier)
        2 -> EmployeeProfilePage(modifier)
    }
}
