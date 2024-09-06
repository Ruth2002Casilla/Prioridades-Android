package edu.ucne.composedemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.ucne.composedemo.data.local.database.PrioridadDb
import edu.ucne.composedemo.presentation.prioridades.PriorityScreen
import edu.ucne.composedemo.presentation.prioridades.PrioritiesTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

@Composable
fun PrioridadNavHost(
    navHostController: NavHostController,
    db: PrioridadDb
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val prioridadDao = remember { db.prioridadDao() }
    val coroutineScope = rememberCoroutineScope()

    val prioridadList by prioridadDao.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    NavHost(
        navController = navHostController,
        startDestination = "prioridad_list"
    ) {
        composable(
            route = "prioridad_list"
        ) {
            PrioritiesTable(
                prioridadesList = prioridadList,
                onAddPriority = { navHostController.navigate("prioridad/0") },
                onEditPriority = { prioridad ->
                    navHostController.navigate("prioridad/${prioridad.prioridadId}")
                },
                onDeletePriority = { prioridad ->
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            prioridadDao.delete(prioridad)
                        }
                    }
                }
            )
        }
        composable(
            route = "prioridad/{prioridadId}",
            arguments = listOf(navArgument("prioridadId") { type = NavType.IntType })
        ) { backStackEntry ->
            val prioridadId = backStackEntry.arguments?.getInt("prioridadId") ?: 0
            val prioridadToEdit = prioridadList.find { it.prioridadId == prioridadId }

            PriorityScreen(
                db = db,
                goPrioridadList = { navHostController.navigate("prioridad_list") },
                prioridadToEdit = prioridadToEdit
            )
        }
    }
}
