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
import androidx.compose.material.icons.filled.Person

@Composable
fun ProfileScreen(title: String, navController: NavController) {

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

        // 🔹 VOLVER
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

        // 🔹 CONTENEDOR
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column {

                // 🔥 NUEVO HEADER CON IMAGEN FLOTANTE
                Box {

                    Column {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(AzulPrincipal)
                        )

                        Spacer(modifier = Modifier.height(60.dp))
                    }

                    Image(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Foto perfil",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.TopCenter)
                            .offset(y = 60.dp)
                            .clip(CircleShape)
                    )
                }

                // 🔹 INFO USUARIO (DEBAJO DE LA IMAGEN)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    Text(nombre, fontWeight = FontWeight.Bold)

                    Text(carrera)

                    Text(
                        correo,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 ACERCA DE MI
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .height(100.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Acerca de mí",
                                tint = AzulPrincipal,
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Acerca de mí",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(acerca)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 MÉTRICAS
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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