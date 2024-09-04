package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.ui.theme.ProjectPrioridadesTheme
import androidx.compose.ui.text.TextStyle


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectPrioridadesTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFECECEC))
                ) { innerPadding ->
                    PriorityScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PriorityScreen(modifier: Modifier = Modifier) {
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var prioridadesList by remember { mutableStateOf(listOf<Prioridad>()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC))
            .padding(top = 32.dp),  // Añade margen
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    textStyle = TextStyle(color = Color.Black)  // color
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
                    textStyle = TextStyle(color = Color.Black)  // color
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
                                    prioridadesList = prioridadesList + Prioridad(0, descripcion, diasInt)
                                    descripcion = ""
                                    diasCompromiso = ""
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

@Composable
fun PrioritiesTable(prioridadesList: List<Prioridad>) {
    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
        Text(
            text = "Datos Guardados",
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp, color = Color(0xFF6200EE)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF333333))
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                "ID",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                color = Color.White, // El color del texto
                fontSize = 12.sp,  // Ajusta el tamaño
                textAlign = TextAlign.Center  // Centraliza el texto
            )
            Text(
                "DESCRIPCIÓN",
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                "DÍAS COMPROMISO",
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth(),
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        prioridadesList.forEach { prioridad ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF444444))
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    prioridad.id.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),  // Asegura que el Text ocupe todo el ancho de su peso
                    color = Color.White,
                    fontSize = 11.sp,  // Tamaño de letra
                    textAlign = TextAlign.Center  // Centraliza el texto
                )
                Text(
                    prioridad.descripcion,
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth(),
                    color = Color.White,
                    fontSize = 11.sp,  // Tamaño de letra
                    textAlign = TextAlign.Center  // Centraliza el texto
                )
                Text(
                    "${prioridad.diasCompromiso} días",
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    color = Color.White,
                    fontSize = 11.sp,  // Tamaño de letra
                    textAlign = TextAlign.Center  // Centraliza el texto
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }

    }
}

data class Prioridad(
    val id: Int,
    val descripcion: String,
    val diasCompromiso: Int
)

@Preview(showBackground = true)
@Composable
fun PriorityScreenPreview() {
    ProjectPrioridadesTheme {
        PriorityScreen()
    }
}
