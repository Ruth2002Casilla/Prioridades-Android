package edu.ucne.composedemo.presentation.prioridades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritiesTable(
    prioridadesList: List<PrioridadEntity>,
    onAddPriority: () -> Unit,
    onEditPriority: (PrioridadEntity) -> Unit,
    onDeletePriority: (PrioridadEntity) -> Unit
) {
    var expandedPriorityId by remember { mutableStateOf<Int?>(null) }
    var priorityToActUpon by remember { mutableStateOf<PrioridadEntity?>(null) }

    val colors = MaterialTheme.colorScheme

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Datos Prioridades",
                        style = MaterialTheme.typography.titleLarge.copy(color = colors.primary),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPriority) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar nueva prioridad")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                item {
                    // Header Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.surfaceVariant)
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            text = "ID",
                            modifier = Modifier.weight(1f),
                            color = colors.onSurfaceVariant,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "DESCRIPCIÓN",
                            modifier = Modifier.weight(3f),
                            color = colors.onSurfaceVariant,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "DÍAS COMPROMISO",
                            modifier = Modifier.weight(2f),
                            color = colors.onSurfaceVariant,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(prioridadesList) { prioridad ->
                    // Data Rows
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.surfaceVariant.copy(alpha = 0.7f))
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .clickable {
                                priorityToActUpon = prioridad
                                expandedPriorityId = prioridad.prioridadId
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = prioridad.prioridadId.toString(),
                            modifier = Modifier.weight(1f),
                            color = colors.onSurface,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = prioridad.descripcion ?: "",
                            modifier = Modifier.weight(3f),
                            color = colors.onSurface,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "${prioridad.diasCompromiso} días",
                            modifier = Modifier.weight(2f),
                            color = colors.onSurface,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        // Dropdown menu for actions
        priorityToActUpon?.let { prioridad ->
            DropdownMenu(
                expanded = expandedPriorityId == prioridad.prioridadId,
                onDismissRequest = { expandedPriorityId = null }
            ) {
                DropdownMenuItem(
                    onClick = {
                        onEditPriority(prioridad)
                        expandedPriorityId = null
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = colors.onSurface)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Editar", color = colors.onSurface)
                        }
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        onDeletePriority(prioridad)
                        expandedPriorityId = null
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = colors.onSurface)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Eliminar", color = colors.onSurface)
                        }
                    }
                )
            }
        }
    }
}



