package com.ggufsurgeon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ggufsurgeon.ui.screens.EditScreen
import com.ggufsurgeon.ui.screens.HomeScreen
import com.ggufsurgeon.ui.screens.MainViewModel
import com.ggufsurgeon.ui.screens.MergeScreen
import com.ggufsurgeon.ui.screens.OptimizeScreen
import com.ggufsurgeon.ui.screens.ViewerScreen

@Composable
fun SurgeonNavGraph() {
    val navController = rememberNavController()
    val vm: MainViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(vm = vm, onNavigate = { navController.navigate(it) })
        }
        composable("viewer") { ViewerScreen(vm) }
        composable("edit") { EditScreen(vm) }
        composable("merge") { MergeScreen(vm) }
        composable("optimize") { OptimizeScreen(vm) }
    }
}
