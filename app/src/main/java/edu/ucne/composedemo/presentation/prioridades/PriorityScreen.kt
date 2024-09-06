package edu.ucne.composedemo.presentation.prioridades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.ucne.composedemo.data.local.database.PrioridadDb
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityScreen(modifier: Modifier = Modifier, db: PrioridadDb? = null, goPrioridadList: () -> Unit) {
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var prioridadesList by remember { mutableStateOf(listOf<PrioridadEntity>()) }
    val coroutineScope = rememberCoroutineScope()

    // Cargar prioridades desde la base de datos
    LaunchedEffect(Unit) {
        if (db != null) {
            coroutineScope.launch(Dispatchers.IO) {
                db.prioridadDao().getAll().collect { list ->
                    withContext(Dispatchers.Main) {
                        prioridadesList = list
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC))
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBar(
            title = { Text("Registro de Prioridades") },
            navigationIcon = {
                IconButton(onClick = goPrioridadList) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menú")
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Registro de Prioridades",
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF6200EE)),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(color = Color.Black)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = diasCompromiso,
                    onValueChange = { diasCompromiso = it.filter { it.isDigit() } },
                    label = { Text("Días de Compromiso") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(color = Color.Black)
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val diasInt = diasCompromiso.toIntOrNull()
                            when {
                                descripcion.isEmpty() || !descripcion[0].isUpperCase() -> {
                                    errorMessage = "La descripción debe comenzar con una letra mayúscula"
                                }
                                diasInt == null || diasInt !in 1..365 -> {
                                    errorMessage = "Por favor ingrese un número válido entre 1 y 365"
                                }
                                else -> {
                                    errorMessage = null
                                    coroutineScope.launch(Dispatchers.IO) {
                                        val existingPrioridades = db!!.prioridadDao().getAll().first()
                                        if (existingPrioridades.any { it.descripcion == descripcion }) {
                                            withContext(Dispatchers.Main) {
                                                errorMessage = "Ya existe una prioridad con esta descripción."
                                            }
                                        } else {
                                            val prioridad = PrioridadEntity(
                                                descripcion = descripcion,
                                                diasCompromiso = diasInt
                                            )
                                            db.prioridadDao().save(prioridad)

                                            withContext(Dispatchers.Main) {
                                                descripcion = ""
                                                diasCompromiso = ""
                                                errorMessage = null
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Guardar")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            descripcion = ""
                            diasCompromiso = ""
                            errorMessage = null
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Nuevo")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        PrioritiesTable(prioridadesList)
    }
}
