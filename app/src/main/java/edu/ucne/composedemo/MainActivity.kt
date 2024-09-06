package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import edu.ucne.composedemo.ui.theme.ProjectPrioridadesTheme
import edu.ucne.composedemo.data.local.database.PrioridadDb
import edu.ucne.composedemo.presentation.prioridades.PriorityScreen


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
                    PriorityScreen(modifier = Modifier.padding(innerPadding), db = db)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PriorityScreenPreview() {
    ProjectPrioridadesTheme {
        PriorityScreen( db = null)
    }
}
