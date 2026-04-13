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

/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * PANTALLA: ProfileScreen
 * DESCRIPCIÓN: Perfil del usuario con información personal y métricas
 *
 * PUNTO C - IDENTIFICADORES Y PROPIEDADES:
 * Muestra datos del usuario, foto de perfil, y métricas (notas, proyectos, semestre)
 * ═══════════════════════════════════════════════════════════════════════════════
 */
@Composable
fun ProfileScreen(title: String, navController: NavController) {

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADORES - DATOS DEL USUARIO (hardcoded - en producción vendrían de DB)
    // ═══════════════════════════════════════════════════════════════════════════
    val nombre = "María González"
    val carrera = "Ingeniería de Sistemas"
    val correo = "maria@email.com"
    val acerca = "Estudiante apasionada por el desarrollo de software."

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADORES - MÉTRICAS DEL USUARIO
    // ═══════════════════════════════════════════════════════════════════════════
    val totalNotas = 5
    val totalProyectos = 2
    val semestre = 6

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: mainColumn
    // TIPO: Column (contenedor vertical principal)
    // PROPIEDADES:
    //   - modifier.fillMaxSize(): Ocupa todo el espacio
    //   - modifier.padding(16.dp): Margen exterior
    // ═══════════════════════════════════════════════════════════════════════════
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ═══════════════════════════════════════════════════════════════════════
        // IDENTIFICADOR: backButton
        // TIPO: Row clickeable (botón de volver)
        // PROPIEDADES:
        //   - modifier.fillMaxWidth()
        //   - modifier.clickable: Navega atrás
        //   - Icon: ArrowBack
        //   - Text: "Volver a notas" (color: AzulPrincipal)
        // EVENTO: onClick → navController.popBackStack()
        // ═══════════════════════════════════════════════════════════════════════
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

        // ═══════════════════════════════════════════════════════════════════════
        // IDENTIFICADOR: titleText
        // TIPO: Text
        // PROPIEDADES:
        //   - text: "Mi perfil"
        //   - fontSize: 26.sp
        //   - fontWeight: Bold
        // ═══════════════════════════════════════════════════════════════════════
        Text(
            text = "Mi perfil",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ═══════════════════════════════════════════════════════════════════════
        // IDENTIFICADOR: profileCard
        // TIPO: Card (contenedor principal del perfil)
        // PROPIEDADES:
        //   - modifier.fillMaxSize()
        //   - elevation: 6.dp
        // ═══════════════════════════════════════════════════════════════════════
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column {

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: headerBox
                // TIPO: Box (contenedor con superposición)
                // DESCRIPCIÓN: Header azul con imagen de perfil flotante
                // ═══════════════════════════════════════════════════════════════════
                Box {

                    Column {
                        // ═══════════════════════════════════════════════════════════
                        // IDENTIFICADOR: headerBackground
                        // TIPO: Box (fondo del header)
                        // PROPIEDADES:
                        //   - modifier.fillMaxWidth()
                        //   - modifier.height(120.dp)
                        //   - modifier.background(AzulPrincipal)
                        // ═══════════════════════════════════════════════════════════
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(AzulPrincipal)
                        )

                        Spacer(modifier = Modifier.height(60.dp))
                    }

                    // ═══════════════════════════════════════════════════════════════
                    // IDENTIFICADOR: profileImage
                    // TIPO: Image (imagen circular flotante)
                    // PROPIEDADES:
                    //   - painter: painterResource(R.drawable.perfil)
                    //   - modifier.size(100.dp)
                    //   - modifier.align(Alignment.TopCenter): Centrado horizontal
                    //   - modifier.offset(y = 60.dp): Desplazamiento vertical
                    //   - modifier.clip(CircleShape): Forma circular
                    // ═══════════════════════════════════════════════════════════════
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

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: userInfoColumn
                // TIPO: Column (información del usuario)
                // PROPIEDADES:
                //   - horizontalAlignment: CenterHorizontally
                //   - modifier.fillMaxWidth()
                //   - modifier.padding(horizontal = 16.dp)
                // CONTENIDO: nombre, carrera, correo
                // ═══════════════════════════════════════════════════════════════════
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    // IDENTIFICADOR: nombreText - Nombre del usuario
                    Text(nombre, fontWeight = FontWeight.Bold)

                    // IDENTIFICADOR: carreraText - Carrera del usuario
                    Text(carrera)

                    // IDENTIFICADOR: correoText - Email del usuario
                    Text(
                        correo,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: aboutCard
                // TIPO: Card (sección "Acerca de mí")
                // PROPIEDADES:
                //   - modifier.fillMaxWidth()
                //   - modifier.padding(horizontal = 8.dp)
                //   - modifier.height(100.dp)
                //   - elevation: 4.dp
                //   - containerColor: White
                // ═══════════════════════════════════════════════════════════════════
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

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: metricsRow
                // TIPO: Row (fila de métricas)
                // PROPIEDADES:
                //   - modifier.fillMaxWidth()
                //   - modifier.padding(horizontal = 16.dp)
                //   - horizontalArrangement: SpaceBetween (distribuidos uniformemente)
                // COMPONENTES HIJOS: 3x InfoCard
                // ═══════════════════════════════════════════════════════════════════
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // ═══════════════════════════════════════════════════════════════
                    // IDENTIFICADOR: notesInfoCard
                    // TIPO: InfoCard (componente reutilizable)
                    // PROPIEDADES:
                    //   - label: "Notas"
                    //   - value: totalNotas (5)
                    //   - color: AzulClaro
                    // VARIABLE VINCULADA: totalNotas (Int)
                    // ═══════════════════════════════════════════════════════════════
                    InfoCard("Notas", totalNotas.toString(), AzulClaro)

                    // ═══════════════════════════════════════════════════════════════
                    // IDENTIFICADOR: projectsInfoCard
                    // TIPO: InfoCard
                    // PROPIEDADES:
                    //   - label: "Proyectos"
                    //   - value: totalProyectos (2)
                    //   - color: Amarillo
                    // VARIABLE VINCULADA: totalProyectos (Int)
                    // ═══════════════════════════════════════════════════════════════
                    InfoCard("Proyectos", totalProyectos.toString(), Amarillo)

                    // ═══════════════════════════════════════════════════════════════
                    // IDENTIFICADOR: semesterInfoCard
                    // TIPO: InfoCard
                    // PROPIEDADES:
                    //   - label: "Semestre"
                    //   - value: semestre (6)
                    //   - color: AzulPrincipal
                    // VARIABLE VINCULADA: semestre (Int)
                    // ═══════════════════════════════════════════════════════════════
                    InfoCard("Semestre", semestre.toString(), AzulPrincipal)
                }
            }
        }
    }
}