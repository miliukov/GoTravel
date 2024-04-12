package com.example.gotravel.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(
    navHostController: NavHostController,
    context: Context
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navHostController, context)
        }
    ) {
        NavHost(navController = navHostController, startDestination = "screen_1") {
            composable("screen_1") {
                 // ShowRoute(context, navHostController)
                ChooseTrans(context, navHostController)
            }
            composable("screen_2") {
                // air tickets
            }
            composable("screen_3") {
                // Hotels()
            }
            composable("screen_4") {
                // Profile()
            }
            composable("choose") {
                ChooseTrans(context, navHostController)
            }
            composable("by_plane") {
                ShowRouteFlights(context, navHostController)
            }
            composable("by_car") {
                ShowRoutesCar(context, navHostController)
            }
        }
    }
}

