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


/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * PANTALLA: NotesScreen
 * DESCRIPCIÓN: Pantalla principal con lista de notas, drawer lateral y FAB
 *
 * PUNTO C - IDENTIFICADORES Y PROPIEDADES:
 * Esta pantalla incluye múltiples componentes interactivos y variables de estado
 * ═══════════════════════════════════════════════════════════════════════════════
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController) {

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: drawerState
    // TIPO: DrawerState (estado del drawer lateral)
    // USO: Controla si el drawer está abierto o cerrado
    // VALOR INICIAL: DrawerValue.Closed
    // ═══════════════════════════════════════════════════════════════════════════
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: scope
    // TIPO: CoroutineScope
    // USO: Maneja operaciones asíncronas (abrir/cerrar drawer)
    // ═══════════════════════════════════════════════════════════════════════════
    val scope = rememberCoroutineScope()

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: notes
    // TIPO: List<NoteDetailUi>
    // USO: Lista de todas las notas obtenidas del repositorio
    // FUENTE: NotesMockRepository.getAll()
    // ═══════════════════════════════════════════════════════════════════════════
    val notes = NotesMockRepository.getAll()

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: modalNavigationDrawer
    // TIPO: ModalNavigationDrawer (drawer lateral deslizable)
    // PROPIEDADES:
    //   - drawerState: drawerState (estado del drawer)
    //   - drawerContent: ModalDrawerSheet con contenido del menú
    // ═══════════════════════════════════════════════════════════════════════════
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
            // ═══════════════════════════════════════════════════════════════════
            // IDENTIFICADOR: floatingActionButton (FAB)
            // TIPO: FloatingActionButton (botón flotante circular)
            // PROPIEDADES:
            //   - onClick: Navega a "noteCreate"
            //   - containerColor: Amarillo (#FFB300)
            //   - contentColor: AzulPrincipal (#003366) para el ícono
            //   - Icon: Icons.Default.Add (ícono de +)
            // EVENTO: onClick → navController.navigate("noteCreate")
            // ═══════════════════════════════════════════════════════════════════
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

            // ═══════════════════════════════════════════════════════════════════
            // IDENTIFICADOR: topBar (barra superior)
            // TIPO: Column con Row clickeable + imagen de perfil
            // PROPIEDADES (Row clickeable):
            //   - modifier.fillMaxWidth(): Ancho completo
            //   - modifier.background(AzulPrincipal): Fondo azul
            //   - modifier.clickable: Abre el drawer al tocar
            //   - modifier.padding(16.dp): Espaciado interno
            // EVENTO: onClick → scope.launch { drawerState.open() }
            // ═══════════════════════════════════════════════════════════════════
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
                        // ═══════════════════════════════════════════════════════════
                        // IDENTIFICADOR: profileImage
                        // TIPO: Image (imagen circular)
                        // PROPIEDADES:
                        //   - painter: painterResource(R.drawable.perfil)
                        //   - contentDescription: "Perfil"
                        //   - modifier.size(40.dp): Tamaño 40dp x 40dp
                        //   - modifier.clip(CircleShape): Forma circular
                        // ═══════════════════════════════════════════════════════════
                        Image(
                            painter = painterResource(id = R.drawable.perfil),
                            contentDescription = "Perfil",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // ═══════════════════════════════════════════════════════════
                        // IDENTIFICADOR: appNameText
                        // TIPO: Text
                        // PROPIEDADES:
                        //   - text: "Poli Notas"
                        //   - color: White
                        //   - fontWeight: Bold
                        // ═══════════════════════════════════════════════════════════
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

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: titleText
                // TIPO: Text
                // PROPIEDADES:
                //   - text: "Mis Notas"
                //   - fontSize: 28.sp
                //   - fontWeight: Bold
                //   - color: AzulPrincipal (#003366)
                // ═══════════════════════════════════════════════════════════════════
                Text(
                    "Mis Notas",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulPrincipal
                )

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: notesCountText
                // TIPO: Text (contador de notas)
                // PROPIEDADES:
                //   - text: "${notes.size} notas" (dinámico según cantidad)
                //   - color: Gray
                // VARIABLE VINCULADA: notes.size
                // ═══════════════════════════════════════════════════════════════════
                Text(
                    "${notes.size} notas",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: notesLazyColumn
                // TIPO: LazyColumn (lista vertical con lazy loading)
                // PROPIEDADES:
                //   - items: notes (lista de notas)
                //   - key: note.id (identificador único para cada item)
                // COMPONENTE HIJO: NoteItem (composable para cada nota)
                // EVENTO: Click en item → Navegación a detalle (en NoteItem)
                // ═══════════════════════════════════════════════════════════════════
                LazyColumn {

                    items(items = notes, key = { it.id }) { note ->
                        NoteItem(note, navController)
                    }
                }
            }
        }
    }
}