package com.map08.shiftapp.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import com.map08.shiftapp.LocalAuthViewModel
import com.map08.shiftapp.LocalNavController
import com.map08.shiftapp.ManagerNavItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerHomePage() {

    val navController = LocalNavController.current
    val authViewModel = LocalAuthViewModel.current

    val managerNavItemList = listOf(
        ManagerNavItem("Home", Icons.Default.Home),
        ManagerNavItem("Time", Icons.Default.DateRange),
        ManagerNavItem("List", Icons.AutoMirrored.Filled.List),
        ManagerNavItem("Profile", Icons.Default.Person),
        
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Manager Home", fontSize = 20.sp)
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
                managerNavItemList.forEachIndexed { index, managerNavItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = managerNavItem.icon, contentDescription = "Icon")
                        },
                        label = {
                            Text(text = managerNavItem.label)
                        }
                    )
                }
            }
        }
    ){ innerPadding ->
        ManagerContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex)

    }
}

@Composable
fun ManagerContentScreen(modifier: Modifier, selectedIndex: Int) {
    when(selectedIndex) {
        0-> ManagerHomePage()
        1-> ManagerTimePage()
        2-> ManagerListPage()
        3-> ManagerProfilePage()
    }


}