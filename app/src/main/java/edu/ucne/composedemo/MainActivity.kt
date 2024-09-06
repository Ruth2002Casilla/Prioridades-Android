package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import edu.ucne.composedemo.ui.theme.ProjectPrioridadesTheme
import edu.ucne.composedemo.data.local.database.PrioridadDb
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import edu.ucne.composedemo.presentation.navigation.PrioridadNavHost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectPrioridadesTheme {
                val db = remember { PrioridadDb.getDatabase(applicationContext) } // Singleton para la base de datos
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFECECEC))
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val navController = rememberNavController()
                        PrioridadNavHost(navController, db)

                    }
                }
            }
        }

    }

    @Composable
    private fun PrioridadRow(it: PrioridadEntity) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = it.prioridadId.toString())
            Text(
                modifier = Modifier.weight(2f),
                text = it.descripcion ?: "Descripción no disponible",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(modifier = Modifier.weight(2f), text = it.diasCompromiso.toString())
        }
        HorizontalDivider()
    }

    private suspend fun savePrioridad(prioridad: PrioridadEntity) {
        val db = PrioridadDb.getDatabase(applicationContext)
        withContext(Dispatchers.IO) {
            db.prioridadDao().save(prioridad)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PriorityScreenPreview() {
    // No lo necesito
}
