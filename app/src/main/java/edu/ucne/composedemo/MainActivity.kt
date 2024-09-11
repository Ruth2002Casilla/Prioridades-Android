package edu.ucne.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import edu.ucne.composedemo.data.local.database.PrioridadDb
import edu.ucne.composedemo.presentation.navigation.NavigationNavHost
import edu.ucne.composedemo.ui.theme.ProjectPrioridadesTheme

class MainActivity : ComponentActivity() {

    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).build()

        enableEdgeToEdge()

        val prioridadDao = prioridadDb.prioridadDao()

        setContent {
            ProjectPrioridadesTheme {
                NavigationNavHost(
                    PrioridadesLista = prioridadDao.getAll(),
                    prioridadDao = prioridadDao
                )
            }
        }
    }
}
