package com.example.polinotas.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.polinotas.R
import com.example.polinotas.ui.components.InfoCard
import com.example.polinotas.ui.theme.*

@Composable
fun ProfileScreen(title: String, navController: NavController) {

    // 🔹 DATOS (luego Firebase)
    val nombre = "María González"
    val carrera = "Ingeniería de Sistemas"
    val correo = "maria@email.com"
    val acerca = "Estudiante apasionada por el desarrollo de software."

    val totalNotas = 5
    val totalProyectos = 2
    val semestre = 6

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔹 HEADER PERSONALIZADO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.popBackStack()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "<- Volver"
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Volver a notas",
                color = AzulPrincipal
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Mi perfil",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 CONTENEDOR PRINCIPAL
        Card(
            modifier = Modifier
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                // 🔹 HEADER PERFIL
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AzulPrincipal)
                        .padding(16.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Foto perfil",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(nombre, color = Color.White, fontWeight = FontWeight.Bold)
                        Text(carrera, color = Color.White)
                        Text(correo, color = Color.White, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 ACERCA DE MI
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Acerca de mí", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(acerca)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 MÉTRICAS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    InfoCard("Notas", totalNotas.toString(), AzulClaro)
                    InfoCard("Proyectos", totalProyectos.toString(), Amarillo)
                    InfoCard("Semestre", semestre.toString(), AzulPrincipal)
                }
            }
        }
    }
}