package edu.ucne.composedemo.presentation.Screens.prioridades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun IndexPrioridadesScreen(
    onDrawerToggle: () -> Unit,
    PrioridadesLista: Flow<List<PrioridadEntity>>,
    navController: NavController
) {
    var prioridades by remember { mutableStateOf(emptyList<PrioridadEntity>()) }

    LaunchedEffect(PrioridadesLista) {
        PrioridadesLista.collect { data ->
            prioridades = data
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        IconButton(
            onClick = onDrawerToggle,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menú",
                tint = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp)
                .align(Alignment.TopEnd),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = "Prioridades", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(text = "Index", fontSize = 16.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
        ) {
            if (prioridades.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron prioridades",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            } else {
                PrioridadesList(
                    prioridades = prioridades,
                    onEditClick = { prioridad ->
                        prioridad.prioridadId?.let { id ->
                            navController.navigate(Screen.EditarPrioridades(prioridadId = id))
                        }
                    },
                    onDeleteClick = { prioridad ->
                        prioridad.prioridadId?.let { id ->
                            navController.navigate(Screen.EliminarPrioridades(prioridadId = id))
                        }
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.CrearPrioridades)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            containerColor = Color.Blue,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar Prioridad"
            )
        }
    }
}


@Composable
fun PrioridadesList(
    prioridades: List<PrioridadEntity>,
    onEditClick: (PrioridadEntity) -> Unit,
    onDeleteClick: (PrioridadEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(prioridades) { prioridad ->
            PrioridadItem(
                prioridad = prioridad,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun PrioridadItem(
    prioridad: PrioridadEntity,
    onEditClick: (PrioridadEntity) -> Unit,
    onDeleteClick: (PrioridadEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = prioridad.descripcion!!, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "${prioridad.diasCompromiso} días", fontSize = 14.sp, color = Color.Gray)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = { onEditClick(prioridad) }) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "Editar")
            }
            IconButton(onClick = { onDeleteClick(prioridad) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IndexPrioridadesScreenPreview() {
    val navController = rememberNavController()
    IndexPrioridadesScreen(
        onDrawerToggle = {},
        PrioridadesLista = flowOf(),
        navController = navController
    )
}
