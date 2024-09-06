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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.composedemo.data.local.entities.PrioridadEntity

@Composable
fun PrioritiesTable(prioridadesList: List<PrioridadEntity>) {
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
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                "DESCRIPCIÓN",
                modifier = Modifier.weight(3f),
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                "DÍAS COMPROMISO",
                modifier = Modifier.weight(2f),
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
                    prioridad.prioridadId.toString(),
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    prioridad.descripcion ?: "",
                    modifier = Modifier.weight(3f),
                    color = Color.White,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "${prioridad.diasCompromiso} días",
                    modifier = Modifier.weight(2f),
                    color = Color.White,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}