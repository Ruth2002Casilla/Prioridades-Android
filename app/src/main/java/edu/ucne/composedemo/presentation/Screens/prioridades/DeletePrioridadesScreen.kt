package edu.ucne.composedemo.presentation.Screens.prioridades

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun DeletePrioridadesScreen(
    onDrawerToggle: () -> Unit,
    navController: NavController,
    prioridadDao: PrioridadDao? = null,
    prioridadId: Int?
) {
    val coroutineScope = rememberCoroutineScope()
    var prioridad by remember { mutableStateOf<PrioridadEntity?>(null) }

    LaunchedEffect(prioridadId) {
        if (prioridadDao != null && prioridadId != null) {
            prioridadDao.find(prioridadId)?.let { p ->
                prioridad = p
            }
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
            Text(text = "Delete", fontSize = 16.sp)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿Deseas eliminar esta prioridad?",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (prioridad != null) {
                            prioridadDao?.delete(prioridad!!)
                            navController.navigate(Screen.ControlPanelPrioridades)
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.White
                )
                Text(text = "Eliminar")
            }

            Button(
                onClick = {
                    navController.navigate(Screen.ControlPanelPrioridades)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Cancelar",
                    tint = Color.White
                )
                Text(text = "Cancelar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeletePrioridadesScreenPreview() {
    val navController = rememberNavController()

    DeletePrioridadesScreen(
        onDrawerToggle = {},
        navController = navController,
        prioridadDao = null,
        prioridadId = 1
    )
}
