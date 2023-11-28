package com.example.gotravel.ui

import android.content.Context
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gotravel.R

@Composable
fun BottomNavigation(
    navController: NavController,
    context: Context
) {
    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
        BottomItem.Screen4
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon"
                    )
                },
                label = {
                    Text(
                        text = context.getString(item.title),
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = LightBlue,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

sealed class BottomItem(val title: Int, val iconId: Int, val route: String){
    object Screen1: BottomItem(R.string.route, R.drawable.route, "screen_1")
    object Screen2: BottomItem(R.string.air_tickets, R.drawable.airplane, "screen_2")
    object Screen3: BottomItem(R.string.hotels, R.drawable.hotel, "screen_3")
    object Screen4: BottomItem(R.string.profile, R.drawable.me, "screen_4")
}