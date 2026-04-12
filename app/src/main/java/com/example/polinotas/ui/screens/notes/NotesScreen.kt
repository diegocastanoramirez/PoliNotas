package com.example.polinotas.ui.screens.notes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.polinotas.R
import com.example.polinotas.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 🔹 DATOS centralizados en el repositorio mock (fuente unica para lista y detalle)
    val notes = NotesMockRepository.getAll()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet {

                // 🔹 BOTÓN CERRAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch { drawerState.close() }
                        }
                        .padding(16.dp)
                ) {
                    Text("X Cerrar")
                }

                Divider()

                // 🔹 OPCIÓN PERFIL
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                drawerState.close()
                                navController.navigate("profile/nota")
                            }
                        }
                        .padding(16.dp)
                ) {
                    Text("Ir a Perfil")
                }
            }
        }
    ) {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("noteCreate") },
                    containerColor = Amarillo,
                    contentColor = AzulPrincipal
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Crear nota"
                    )
                }
            },

            topBar = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AzulPrincipal)
                            .clickable {
                                scope.launch { drawerState.open() }
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.perfil),
                            contentDescription = "Perfil",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            "Poli Notas",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {

                // 🔹 TÍTULO
                Text(
                    "Mis Notas",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulPrincipal
                )

                // 🔹 CONTADOR
                Text(
                    "${notes.size} notas",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 🔹 LISTA
                LazyColumn {

                    items(items = notes, key = { it.id }) { note ->
                        NoteItem(note, navController)
                    }
                }
            }
        }
    }
}