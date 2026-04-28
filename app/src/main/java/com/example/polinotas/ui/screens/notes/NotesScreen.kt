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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.polinotas.data.ProfileImageManager
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
private fun DrawerHeader(
    userConfig: UserConfig,
    selectedImageUri: String?,
    onImageClick: () -> Unit,
    onClose: () -> Unit
) {
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
            // Foto de perfil con borde amarillo (clickeable)
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Amarillo, CircleShape)
                    .padding(4.dp)
                    .clickable { onImageClick() }
            ) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Foto de perfil personalizada",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White, CircleShape)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.perfil_usuario),
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White, CircleShape)
                            .clip(CircleShape)
                    )
                }
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
    // ═══════════════════════════════════════════════════════════════════════════
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // ═══════════════════════════════════════════════════════════════════════════
    // ═══════════════════════════════════════════════════════════════════════════
    val scope = rememberCoroutineScope()

    // ═══════════════════════════════════════════════════════════════════════════
    // ═══════════════════════════════════════════════════════════════════════════
    val notes = NotesMockRepository.getAll()

    // ═══════════════════════════════════════════════════════════════════════════
    // ═══════════════════════════════════════════════════════════════════════════
    val userConfig = remember { UserConfig.default }

    // ═══════════════════════════════════════════════════════════════════════════
    // ═══════════════════════════════════════════════════════════════════════════
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    // ═══════════════════════════════════════════════════════════════════════════
    // ═══════════════════════════════════════════════════════════════════════════
    var isCategoriesExpanded by remember { mutableStateOf(false) }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADORES - ESTADOS PARA AMPLIAR Y CAMBIAR FOTO DE PERFIL
    // ═══════════════════════════════════════════════════════════════════════════
    var showImageDialog by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf(ProfileImageManager.getProfileImageUri()) }

    // Launcher para seleccionar imagen de la galería
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val uriString = uri.toString()
            selectedImageUri = uriString
            ProfileImageManager.saveProfileImageUri(uriString)
            showImageDialog = false
        }
    }

    // Recargar la imagen de perfil cuando se vuelve a esta pantalla
    LaunchedEffect(Unit) {
        selectedImageUri = ProfileImageManager.getProfileImageUri()
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // ═══════════════════════════════════════════════════════════════════════════
    val categories = remember(notes) { getCategories(notes) }

    // ═══════════════════════════════════════════════════════════════════════════
    // ═══════════════════════════════════════════════════════════════════════════
    val filteredNotes = remember(notes, selectedCategory) {
        if (selectedCategory == null) {
            notes
        } else {
            notes.filter { it.category == selectedCategory }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // ═══════════════════════════════════════════════════════════════════════════
    BackHandler(enabled = selectedCategory != null) {
        selectedCategory = null
    }

    // ═══════════════════════════════════════════════════════════════════════════
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
                        selectedImageUri = selectedImageUri,
                        onImageClick = { showImageDialog = true },
                        onClose = {
                            scope.launch { drawerState.close() }
                        }
                    )

                    // Sección "Mi Perfil" (primera opción - amarilla)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Amarillo)
                            .clickable {
                                navController.navigate("profile/Mi Perfil")
                                scope.launch { drawerState.close() }
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Mi Perfil",
                            tint = AzulPrincipal,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Mi Perfil",
                            color = AzulPrincipal,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Sección "Inicio"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
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
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Inicio",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
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
            //   - onClick: Navega a "noteCreate"
            //   - containerColor: Amarillo (#FFB300)
            //   - contentColor: AzulPrincipal (#003366) para el ícono
            //   - Icon: Icons.Default.Add (ícono de +)
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
            // PROPIEDADES (Row clickeable):
            //   - modifier.fillMaxWidth(): Ancho completo
            //   - modifier.background(AzulPrincipal): Fondo azul
            //   - modifier.clickable: Abre el drawer al tocar
            //   - modifier.padding(16.dp): Espaciado interno
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
                        // Foto de perfil en el banner (usa la imagen personalizada si existe)
                        // ═══════════════════════════════════════════════════════════
                        if (selectedImageUri != null) {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Foto de perfil personalizada",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White, CircleShape)
                                    .clip(CircleShape)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.perfil_usuario),
                                contentDescription = "Perfil",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.White, CircleShape)
                                    .clip(CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // ═══════════════════════════════════════════════════════════
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
                //   - text: "${filteredNotes.size} notas" (dinámico según cantidad)
                //   - color: Gray
                // ═══════════════════════════════════════════════════════════════════
                Text(
                    "${filteredNotes.size} notas",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ═══════════════════════════════════════════════════════════════════
                //   - items: filteredNotes (lista de notas filtradas por categoría)
                //   - key: note.id (identificador único para cada item)
                // COMPONENTE HIJO: NoteItem (composable para cada nota)
                // ═══════════════════════════════════════════════════════════════════
                LazyColumn {

                    items(items = filteredNotes, key = { it.id }) { note ->
                        NoteItem(note, navController)
                    }
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // DIÁLOGO: Ampliar imagen y cambiar foto de perfil
    // ═══════════════════════════════════════════════════════════════════════════
    if (showImageDialog) {
        Dialog(onDismissRequest = { showImageDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header con botón cerrar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Foto de perfil",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = { showImageDialog = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = AzulPrincipal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Imagen ampliada
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Foto de perfil ampliada",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(250.dp)
                                .background(Color.White, CircleShape)
                                .clip(CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.perfil_usuario),
                            contentDescription = "Foto de perfil ampliada",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(250.dp)
                                .background(Color.White, CircleShape)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón para cambiar foto
                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzulPrincipal
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Cambiar foto",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Cambiar foto de perfil",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}