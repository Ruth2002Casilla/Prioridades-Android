package edu.ucne.composedemo.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.composedemo.data.local.dao.PrioridadDao
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.presentation.Screens.prioridades.CreatePrioridadesScreen
import edu.ucne.composedemo.presentation.Screens.prioridades.DeletePrioridadesScreen
import edu.ucne.composedemo.presentation.Screens.prioridades.EditPrioridadesScreen
import edu.ucne.composedemo.presentation.Screens.prioridades.IndexPrioridadesScreen
import edu.ucne.composedemo.presentation.component.NavigationDrawer

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Composable
fun NavigationNavHost(
    prioridadDao: PrioridadDao,
    PrioridadesLista: Flow<List<PrioridadEntity>>
) {
    val navController = rememberNavController()
    val isDrawerVisible = remember { mutableStateOf(false) }
    val currentScreen = remember { mutableStateOf<Screen>(Screen.ControlPanelPrioridades) }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = Screen.ControlPanelPrioridades) {

            composable<Screen.ControlPanelPrioridades> {
                IndexPrioridadesScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                    PrioridadesLista = PrioridadesLista,
                    navController = navController
                )
            }
            composable<Screen.CrearPrioridades> {
                CreatePrioridadesScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                    navController = navController,
                    prioridadDao = prioridadDao
                )
            }
            composable<Screen.EditarPrioridades> { backStackEntry ->
                val prioridadId = backStackEntry.arguments?.getInt("prioridadId")
                if (prioridadId != null) {
                    EditPrioridadesScreen(
                        onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                        navController = navController,
                        prioridadDao = prioridadDao,
                        prioridadId = prioridadId
                    )
                }
            }
            composable<Screen.EliminarPrioridades> { backStackEntry ->
                val prioridadId = backStackEntry.arguments?.getInt("prioridadId")
                if (prioridadId != null) {
                    DeletePrioridadesScreen(
                        onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                        navController = navController,
                        prioridadDao = prioridadDao,
                        prioridadId = prioridadId
                    )
                }
            }
        }

        NavigationDrawer(
            isVisible = isDrawerVisible.value,
            onItemClick = { itemTitle ->
                when (itemTitle) {
                    "Prioridades" -> navController.navigate(Screen.ControlPanelPrioridades)
                }
                isDrawerVisible.value = false
            }
        )
    }
}


sealed class Screen {
    //Prioridades
    @Serializable
    object ControlPanelPrioridades : Screen()
    @Serializable
    object CrearPrioridades : Screen()
    @Serializable
    data class EditarPrioridades(val prioridadId: Int) : Screen()
    @Serializable
    data class EliminarPrioridades(val prioridadId: Int) : Screen()
}
