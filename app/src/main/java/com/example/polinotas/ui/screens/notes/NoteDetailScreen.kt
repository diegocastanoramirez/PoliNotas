package com.example.polinotas.ui.screens.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.polinotas.R
import com.example.polinotas.ui.theme.AzulPrincipal
import com.example.polinotas.ui.theme.Amarillo
import kotlinx.coroutines.launch


/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * PANTALLA: NoteDetailScreen
 * DESCRIPCIÓN: Visualización y edición de detalles completos de una nota
 *
 * PUNTO C - IDENTIFICADORES Y PROPIEDADES:
 * Incluye modo edición, tabs (Contenido/Fotos/Video), favoritos, eliminación
 * ═══════════════════════════════════════════════════════════════════════════════
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoteDetailScreen(noteId: String, navController: NavController) {
    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADORES - VARIABLES DE ESTADO
    // ═══════════════════════════════════════════════════════════════════════════

    // IDENTIFICADOR: selectedTab - Índice del tab activo (0=Contenido, 1=Fotos, 2=Video)
    var selectedTab by remember { mutableIntStateOf(0) }

    // IDENTIFICADOR: showMarkdown - Toggle entre vista Markdown/Texto plano
    var showMarkdown by remember { mutableStateOf(true) }

    // IDENTIFICADOR: isEditing - Estado de modo edición (true/false)
    var isEditing by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // IDENTIFICADOR: note - Objeto de nota cargado desde repositorio
    val note = NotesMockRepository.getById(noteId)

    if (note == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nota no encontrada")
        }
        return
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADORES - VARIABLES EDITABLES (para modo edición)
    // ═══════════════════════════════════════════════════════════════════════════

    // IDENTIFICADOR: editableTitle - Título editable (copia temporal de note.title)
    var editableTitle by remember(note.id) { mutableStateOf(note.title) }

    // IDENTIFICADOR: editableDescription - Descripción editable
    var editableDescription by remember(note.id) { mutableStateOf(note.description) }

    // IDENTIFICADOR: editableContent - Contenido editable
    var editableContent by remember(note.id) { mutableStateOf(note.markdownContent) }

    // Sincroniza variables editables cuando cambia la nota
    LaunchedEffect(note.id, note.title, note.description, note.markdownContent) {
        editableTitle = note.title
        editableDescription = note.description
        editableContent = note.markdownContent
    }

    Scaffold(
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


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(64.dp)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = AzulPrincipal
                            )
                        }
                        Text(
                            text = "Volver",
                            color = AzulPrincipal,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row {
                        // ═══════════════════════════════════════════════════════════
                        // IDENTIFICADOR: favoriteButton
                        // TIPO: IconButton
                        // PROPIEDADES:
                        //   - Icon: Star (llena) o StarOutline según note.isFavorite
                        //   - tint: Amarillo si favorito, AzulPrincipal si no
                        // EVENTO: onClick → Toggle favorito y actualiza en repositorio
                        // MÉTODO: NotesMockRepository.update()
                        // ═══════════════════════════════════════════════════════════
                        IconButton(onClick = {
                            NotesMockRepository.update(note.copy(isFavorite = !note.isFavorite))
                        }) {
                            Icon(
                                imageVector = if (note.isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                                contentDescription = "Favorita",
                                tint = if (note.isFavorite) Amarillo else AzulPrincipal
                            )
                        }

                        // ═══════════════════════════════════════════════════════════
                        // IDENTIFICADOR: shareButton
                        // TIPO: IconButton
                        // PROPIEDADES:
                        //   - Icon: Share
                        //   - tint: AzulPrincipal
                        // EVENTO: onClick → TODO (pendiente implementar)
                        // ═══════════════════════════════════════════════════════════
                        IconButton(onClick = { /* TODO compartir */ }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir",
                                tint = AzulPrincipal
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF6F7FB))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Fecha",
                                tint = Color.Gray,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = note.createdAtLabel,
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CategoryChip(note.category)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: editButton
                            // TIPO: IconButton
                            // PROPIEDADES:
                            //   - Icon: Edit
                            //   - tint: AzulPrincipal
                            // EVENTO: onClick → Toggle modo edición (isEditing)
                            // LÓGICA:
                            //   - Si estaba editando: restaura valores originales
                            //   - Si no: activa modo edición
                            // VARIABLES AFECTADAS: isEditing, editableTitle, editableDescription, editableContent
                            // ═══════════════════════════════════════════════════════════
                            IconButton(
                                onClick = {
                                    if (isEditing) {
                                        editableTitle = note.title
                                        editableDescription = note.description
                                        editableContent = note.markdownContent
                                    }
                                    isEditing = !isEditing
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = AzulPrincipal
                                )
                            }

                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: deleteButton
                            // TIPO: IconButton
                            // PROPIEDADES:
                            //   - Icon: Delete
                            //   - tint: Color(0xFFC62828) - Rojo de advertencia
                            // EVENTO: onClick → Elimina nota y navega atrás
                            // MÉTODO: NotesMockRepository.delete(noteId)
                            // ═══════════════════════════════════════════════════════════
                            IconButton(
                                onClick = {
                                    NotesMockRepository.delete(note.id)
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color(0xFFC62828)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (isEditing) {
                        OutlinedTextField(
                            value = editableTitle,
                            onValueChange = { editableTitle = it },
                            label = { Text("Titulo") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = editableDescription,
                            onValueChange = { editableDescription = it },
                            label = { Text("Descripcion") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = AzulPrincipal,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = note.description, color = Color.DarkGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                if (note.imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = note.imageUrl,
                        contentDescription = note.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = note.imageRes),
                        contentDescription = note.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ═══════════════════════════════════════════════════════════════════════
            // IDENTIFICADOR: tabs
            // TIPO: List<String> (lista de títulos de tabs)
            // VALOR: ["Contenido", "Fotos", "Video"]
            // ═══════════════════════════════════════════════════════════════════════
            val tabs = listOf("Contenido", "Fotos", "Video")

            // ═══════════════════════════════════════════════════════════════════════
            // IDENTIFICADOR: tabRow
            // TIPO: TabRow (barra de pestañas)
            // PROPIEDADES:
            //   - selectedTabIndex: selectedTab (índice activo)
            // EVENTO: onClick en Tab → Actualiza selectedTab
            // VARIABLE VINCULADA: selectedTab (Int)
            // ═══════════════════════════════════════════════════════════════════════
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(title) })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ═══════════════════════════════════════════════════════════════════════
            // IDENTIFICADOR: tabContent
            // TIPO: when (condicional según selectedTab)
            // CONTENIDO:
            //   - 0: Contenido (texto markdown/plano con modo edición)
            //   - 1: Fotos (galería de imágenes)
            //   - 2: Video (placeholder)
            // ═══════════════════════════════════════════════════════════════════════
            when (selectedTab) {
                0 -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                ContentToggleButton(
                                    text = "Markdown",
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Code,
                                            contentDescription = null,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    },
                                    selected = showMarkdown,
                                    onClick = { showMarkdown = true }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                ContentToggleButton(
                                    text = "Texto",
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Description,
                                            contentDescription = null,
                                            modifier = Modifier.size(15.dp)
                                        )
                                    },
                                    selected = !showMarkdown,
                                    onClick = { showMarkdown = false }
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            if (isEditing) {
                                OutlinedTextField(
                                    value = editableContent,
                                    onValueChange = { editableContent = it },
                                    minLines = 6,
                                    label = { Text("Contenido") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // ═══════════════════════════════════════════════════════
                                // IDENTIFICADOR: saveEditButton
                                // TIPO: Button
                                // PROPIEDADES:
                                //   - onClick: Guarda cambios y sale de modo edición
                                //   - modifier.fillMaxWidth()
                                //   - Icon: Save + Text "Guardar"
                                // EVENTO: onClick → Actualiza nota en repositorio
                                // MÉTODO: NotesMockRepository.update(updatedNote)
                                // VARIABLES VINCULADAS: editableTitle, editableDescription, editableContent, isEditing
                                // ═══════════════════════════════════════════════════════
                                Button(
                                    onClick = {
                                        val updatedNote = note.copy(
                                            title = editableTitle,
                                            description = editableDescription,
                                            markdownContent = editableContent,
                                            plainContent = editableContent
                                        )
                                        NotesMockRepository.update(updatedNote)
                                        isEditing = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.Save, contentDescription = null)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Guardar")
                                }
                            } else {
                                RenderNoteContent(
                                    content = if (showMarkdown) note.markdownContent else note.plainContent,
                                    markdownMode = showMarkdown
                                )
                            }
                        }
                    }
                }

                1 -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Galeria de imagenes", color = AzulPrincipal, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(10.dp))

                            if (note.gallery.isEmpty() && note.imageUrl.isBlank()) {
                                EmptyState(icon = Icons.Default.Image, message = "No hay imagenes en esta nota")
                            } else {
                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if (note.imageUrl.isNotBlank()) {
                                        AsyncImage(
                                            model = note.imageUrl,
                                            contentDescription = note.title,
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(120.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    note.gallery.forEach { imageRes ->
                                        Image(
                                            painter = painterResource(id = imageRes),
                                            contentDescription = "Imagen de galeria",
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(120.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                else -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (note.hasVideo)  {
                                /* TODO compartir */
                            } else {
                                EmptyState(icon = Icons.Default.PlayArrow, message = "No hay video en esta nota")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(category: String) {
    Surface(
        color = AzulPrincipal.copy(alpha = 0.10f),
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(start = 4.dp)
    ) {
        Text(
            text = category,
            color = AzulPrincipal,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Composable
private fun ContentToggleButton(
    text: String,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = if (selected) AzulPrincipal else Color(0xFFEAEAEA),
        contentColor = if (selected) Color.White else Color.DarkGray,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun EmptyState(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    message: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RenderNoteContent(
    content: String,
    markdownMode: Boolean
) {
    if (markdownMode) {
        Surface(
            color = Color(0xFFF3F4F6),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = content,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .horizontalScroll(rememberScrollState()),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Monospace,
                    lineHeight = 20.sp
                ),
                color = Color(0xFF374151)
            )
        }
    } else {
        val lines = content.lines()

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            lines.forEachIndexed { index, line ->
                when {
                    line.isBlank() -> {
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    index == 0 -> {
                        Text(
                            text = line,
                            style = MaterialTheme.typography.titleMedium,
                            color = AzulPrincipal,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    line.trim().startsWith("- ") -> {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "• ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF3A3A3A)
                            )
                            Text(
                                text = line.trim().removePrefix("- ").trim(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF3A3A3A)
                            )
                        }
                    }

                    else -> {
                        Text(
                            text = line,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF3A3A3A),
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }
    }
}