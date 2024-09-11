package edu.ucne.composedemo.presentation.Screens.prioridades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.ucne.composedemo.data.local.dao.PrioridadDao
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun EditPrioridadesScreen(
    onDrawerToggle: () -> Unit,
    navController: NavController,
    prioridadDao: PrioridadDao?,
    prioridadId: Int
) {
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var validationMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(prioridadId) {
        prioridadDao?.find(prioridadId)?.let { prioridad ->
            descripcion = prioridad.descripcion!!
            diasCompromiso = prioridad.diasCompromiso.toString()
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
            Text(text = "Edit", fontSize = 16.sp)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            isError = descripcion.isBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = diasCompromiso,
            onValueChange = { diasCompromiso = it },
            label = { Text("Días de Compromiso") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = diasCompromiso.isBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        validationMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val dias = diasCompromiso.toIntOrNull()
                if (descripcion.isBlank() || dias == null || dias <= 0) {
                    validationMessage = "Por favor, completa todos los campos correctamente."
                } else {
                    coroutineScope.launch {
                        val updatedPrioridad = PrioridadEntity(
                            prioridadId = prioridadId,
                            descripcion = descripcion,
                            diasCompromiso = dias
                        )
                        prioridadDao?.save(updatedPrioridad)
                        navController.navigate(Screen.ControlPanelPrioridades)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                tint = Color.White
            )
            Text("Actualizar")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditPrioridadesScreenPreview() {
    val navController = rememberNavController()

    EditPrioridadesScreen(
        onDrawerToggle = { /* */ },
        navController = navController,
        prioridadDao = null,
        prioridadId = 1
    )
}
