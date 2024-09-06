package edu.ucne.composedemo.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.composedemo.data.local.database.PrioridadDb
import edu.ucne.composedemo.presentation.prioridades.PriorityScreen
import edu.ucne.composedemo.presentation.prioridades.PrioritiesTable

@Composable
fun PrioridadNavHost(
    navHostController: NavHostController,
    context: Context
){
    val lifecycleOwner = LocalLifecycleOwner.current

    val prioridadDb = remember { PrioridadDb.getDatabase(context) }
    val prioridadDao = remember { prioridadDb.prioridadDao() }

    val prioridadList by prioridadDao.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadList
    ){
        composable<Screen.PrioridadList> {
            PrioritiesTable(
                prioridadesList = prioridadList,
                onAddPriority = { navHostController.navigate(Screen.Prioridad(0)) }
            )
        }
        composable<Screen.Prioridad> {
            PriorityScreen(
                goPrioridadList = { navHostController.navigate(Screen.PrioridadList) }
            )
        }
    }
}
