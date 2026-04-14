package com.example.polinotas.ui.screens.notes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Description
import androidx.compose.ui.res.painterResource
import androidx.activity.compose.BackHandler
import com.example.polinotas.data.UserConfig
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.polinotas.R
import com.example.polinotas.ui.theme.*


/**
 * Extrae categorías únicas de las notas
 */
private fun getCategories(notes: List<NoteDetailUi>): List<String> {
    return notes
        .map { it.category }
        .distinct()
        .sorted()
}

/**
 * Encabezado del drawer con perfil de usuario
 */
@Composable
private fun DrawerHeader(userConfig: UserConfig, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AzulPrincipal)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Foto de perfil con borde amarillo
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Amarillo, CircleShape)
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre del usuario
            Text(
                text = userConfig.name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Rol/Descripción
            Text(
                text = userConfig.role,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }

        // Botón cerrar en esquina superior derecha
        IconButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = Color.White
            )
        }
    }
}

/**
 * Item de menú para categoría individual
 */
@Composable
private fun CategoryItem(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelected) AzulClaro else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 32.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

/**
 * Footer del menú con copyright
 */
@Composable
private fun DrawerFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AzulPrincipal)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Poli Notas © 2026",
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Grupo B02 Programación Móvil",
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 11.sp
        )
    }
}

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
    // IDENTIFICADOR: userConfig
    // TIPO: UserConfig
    // USO: Configuración del usuario para mostrar en el drawer
    // ═══════════════════════════════════════════════════════════════════════════
    val userConfig = remember { UserConfig.default }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: selectedCategory
    // TIPO: String? (nullable)
    // USO: Categoría seleccionada para filtrar notas (null = todas las notas)
    // ═══════════════════════════════════════════════════════════════════════════
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: isCategoriesExpanded
    // TIPO: Boolean
    // USO: Controla si el dropdown de categorías está expandido o colapsado
    // ═══════════════════════════════════════════════════════════════════════════
    var isCategoriesExpanded by remember { mutableStateOf(false) }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: categories
    // TIPO: List<String>
    // USO: Lista de categorías únicas extraídas de las notas
    // ═══════════════════════════════════════════════════════════════════════════
    val categories = remember(notes) { getCategories(notes) }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: filteredNotes
    // TIPO: List<NoteDetailUi>
    // USO: Lista de notas filtradas según la categoría seleccionada
    // ═══════════════════════════════════════════════════════════════════════════
    val filteredNotes = remember(notes, selectedCategory) {
        if (selectedCategory == null) {
            notes
        } else {
            notes.filter { it.category == selectedCategory }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: BackHandler
    // TIPO: Composable
    // USO: Intercepta el botón "Atrás" del sistema para limpiar filtro antes de salir
    // COMPORTAMIENTO: Si hay categoría seleccionada, la limpia. Si no, sale de la app
    // ═══════════════════════════════════════════════════════════════════════════
    BackHandler(enabled = selectedCategory != null) {
        selectedCategory = null
    }

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

            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AzulPrincipal)
                ) {
                    // Encabezado con perfil
                    DrawerHeader(
                        userConfig = userConfig,
                        onClose = {
                            scope.launch { drawerState.close() }
                        }
                    )

                    // Sección "Inicio" (amarilla)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Amarillo)
                            .clickable {
                                selectedCategory = null
                                scope.launch { drawerState.close() }
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = AzulPrincipal,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Inicio",
                            color = AzulPrincipal,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Sección "Categorías" (expandible)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isCategoriesExpanded = !isCategoriesExpanded
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Folder,
                                contentDescription = "Categorías",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Categorías",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Icon(
                            imageVector = if (isCategoriesExpanded)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = if (isCategoriesExpanded) "Colapsar" else "Expandir",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Lista de categorías (visible solo si está expandido)
                    if (isCategoriesExpanded) {
                        categories.forEach { category ->
                            CategoryItem(
                                category = category,
                                isSelected = selectedCategory == category,
                                onClick = {
                                    selectedCategory = category
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                    }

                    // Opción "Sin Categorizar"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCategory = "Sin categoria"
                                scope.launch { drawerState.close() }
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Sin Categorizar",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Sin Categorizar",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Footer
                    DrawerFooter()
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
                //   - text: "Mis Notas" o "Mis Notas - [Categoría]"
                //   - fontSize: 28.sp
                //   - fontWeight: Bold
                //   - color: AzulPrincipal (#003366)
                // ═══════════════════════════════════════════════════════════════════
                Text(
                    text = if (selectedCategory == null) {
                        "Mis Notas"
                    } else {
                        "Mis Notas - $selectedCategory"
                    },
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulPrincipal
                )

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: notesCountText
                // TIPO: Text (contador de notas)
                // PROPIEDADES:
                //   - text: "${filteredNotes.size} notas" (dinámico según cantidad)
                //   - color: Gray
                // VARIABLE VINCULADA: filteredNotes.size
                // ═══════════════════════════════════════════════════════════════════
                Text(
                    "${filteredNotes.size} notas",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: notesLazyColumn
                // TIPO: LazyColumn (lista vertical con lazy loading)
                // PROPIEDADES:
                //   - items: filteredNotes (lista de notas filtradas por categoría)
                //   - key: note.id (identificador único para cada item)
                // COMPONENTE HIJO: NoteItem (composable para cada nota)
                // EVENTO: Click en item → Navegación a detalle (en NoteItem)
                // ═══════════════════════════════════════════════════════════════════
                LazyColumn {

                    items(items = filteredNotes, key = { it.id }) { note ->
                        NoteItem(note, navController)
                    }
                }
            }
        }
    }
}