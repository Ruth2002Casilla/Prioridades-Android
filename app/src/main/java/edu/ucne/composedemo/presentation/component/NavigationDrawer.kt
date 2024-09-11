package edu.ucne.composedemo.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NavigationDrawer(
    isVisible: Boolean,
    onItemClick: (String) -> Unit
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .width(280.dp)
                .background(Color.White)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
            )
            {
                Divider()
                Text(
                    text = "MENU PRINCIPAL",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
                Divider()
                SimpleDrawerBody(
                    items = listOf(
                        SimpleMenuItem("Prioridades", Icons.Default.DateRange),
                        SimpleMenuItem("Salir", Icons.Default.ExitToApp)
                    ),
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun SimpleDrawerBody(
    items: List<SimpleMenuItem>,
    onItemClick: (String) -> Unit
) {
    Column {
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item.title) }
                    .padding(16.dp)
            ) {
                Icon(imageVector = item.icon, contentDescription = item.title)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.title, fontSize = 16.sp)
            }
        }
    }
}

data class SimpleMenuItem(val title: String, val icon: ImageVector)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NavigationDrawerPreview() {
    NavigationDrawer(isVisible = true, onItemClick = {})
}
