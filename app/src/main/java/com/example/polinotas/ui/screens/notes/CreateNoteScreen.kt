package com.example.polinotas.ui.screens.notes

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.polinotas.R
import com.example.polinotas.data.ProfileImageManager
import com.example.polinotas.ui.theme.Amarillo
import com.example.polinotas.ui.theme.AzulPrincipal
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Delete

/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * PANTALLA: CreateNoteScreen
 * DESCRIPCIÓN: Formulario para crear una nueva nota con múltiples campos
 *
 * PUNTO C - IDENTIFICADORES Y PROPIEDADES:
 * Esta pantalla contiene un formulario completo con validación y preview de imagen
 * ═══════════════════════════════════════════════════════════════════════════════
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(navController: NavController) {
    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADORES - VARIABLES DE ESTADO DEL FORMULARIO
    // ═══════════════════════════════════════════════════════════════════════════

    // IDENTIFICADOR: title - Título de la nota
    var title by remember { mutableStateOf("") }

    // IDENTIFICADOR: description - Descripción breve de la nota
    var description by remember { mutableStateOf("") }

    // IDENTIFICADOR: category - Categoría seleccionada
    var category by remember { mutableStateOf("") }

    // IDENTIFICADOR: content - Contenido principal (markdown)
    var content by remember { mutableStateOf("") }

    // IDENTIFICADOR: isFavorite - Estado de favorito (true/false)
    var isFavorite by remember { mutableStateOf(false) }

    // IDENTIFICADOR: imageUrl - URL de imagen principal
    var imageUrl by remember { mutableStateOf("") }
    var videoUrl by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }

    // Foto de perfil compartida
    val profileImageUri = remember { ProfileImageManager.getProfileImageUri() }

    // IDENTIFICADOR: showNewCategoryDialog - Muestra diálogo de nueva categoría
    var showNewCategoryDialog by remember { mutableStateOf(false) }

    // IDENTIFICADOR: newCategoryName - Nombre de nueva categoría temporal
    var newCategoryName by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // IDENTIFICADOR: isValidRemoteUrl - Validación de URL
    val isValidRemoteUrl = imageUrl.startsWith("http://") || imageUrl.startsWith("https://")
    val canPreview =
        imageUrl.isNotBlank() && isValidRemoteUrl && Patterns.WEB_URL.matcher(imageUrl).matches()

    val pickVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        videoUrl = uri?.toString().orEmpty()
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: categories
    // TIPO: MutableStateList<String> (lista dinámica de categorías)
    // USO: Almacena las categorías disponibles para el dropdown
    // ═══════════════════════════════════════════════════════════════════════════
    val categories = remember {
        mutableStateListOf(
            "Programacion",
            "Bases de Datos",
            "Arquitectura",
            "Metodologias",
            "Investigacion"
        ).apply {
            NotesMockRepository
                .getAll()
                .map { it.category }
                .filter { it.isNotBlank() }
                .distinct()
                .forEach { existing ->
                    if (!contains(existing)) add(existing)
                }
        }
    }

    val formattedDate = remember {
        SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES")).format(Date())
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
                    if (profileImageUri != null) {
                        AsyncImage(
                            model = profileImageUri,
                            contentDescription = "Foto de perfil personalizada",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White, CircleShape)
                                .clip(CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.perfil),
                            contentDescription = "Perfil",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White, CircleShape)
                                .clip(CircleShape)
                        )
                    }

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
                                contentDescription = "Cancelar",
                                tint = AzulPrincipal
                            )
                        }
                        Text(
                            text = "Cancelar",
                            color = AzulPrincipal,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                            contentDescription = "Favorita",
                            tint = if (isFavorite) Amarillo else Color.Gray
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: titleLabel
                            // TIPO: Text (etiqueta)
                            // PROPIEDADES:
                            //   - text: "Titulo"
                            //   - style: labelLarge
                            //   - color: AzulPrincipal
                            //   - fontWeight: SemiBold
                            // ═══════════════════════════════════════════════════════════
                            Text(
                                text = "Titulo",
                                style = MaterialTheme.typography.labelLarge,
                                color = AzulPrincipal,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: titleTextField
                            // TIPO: OutlinedTextField (campo de texto)
                            // PROPIEDADES:
                            //   - value: title (vinculado a variable)
                            //   - onValueChange: Actualiza title
                            //   - placeholder: "Escribe el titulo de tu nota aquí..."
                            //   - modifier.fillMaxWidth(): Ancho completo
                            // VARIABLE VINCULADA: title (String)
                            // EVENTO: onValueChange actualiza title
                            // ═══════════════════════════════════════════════════════════
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                placeholder = {
                                    Text(
                                        text = "Escribe el titulo de tu nota aquí...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // ═══════════════════════════════════════════════════════════════
                    // IDENTIFICADOR: descriptionCard
                    // TIPO: Card
                    // PROPIEDADES:
                    //   - shape: RoundedCornerShape(16.dp)
                    //   - elevation: 6.dp
                    // ═══════════════════════════════════════════════════════════════
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = "Descripcion",
                                style = MaterialTheme.typography.labelLarge,
                                color = AzulPrincipal,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: descriptionTextField
                            // TIPO: OutlinedTextField
                            // PROPIEDADES:
                            //   - value: description (vinculado)
                            //   - onValueChange: Actualiza description
                            //   - placeholder: "Breve descripcion de la nota..."
                            //   - modifier.fillMaxWidth()
                            // VARIABLE VINCULADA: description (String)
                            // ═══════════════════════════════════════════════════════════
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                placeholder = {
                                    Text(
                                        text = "Breve descripcion de la nota...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // ═══════════════════════════════════════════════════════════════
                    // IDENTIFICADOR: categoryCard
                    // TIPO: Card
                    // ═══════════════════════════════════════════════════════════════
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Label,
                                    contentDescription = null,
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Categoria",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = AzulPrincipal,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: categoryDropdown
                            // TIPO: ExposedDropdownMenuBox (selector desplegable)
                            // PROPIEDADES:
                            //   - expanded: categoryExpanded (estado abierto/cerrado)
                            //   - onExpandedChange: Toggle categoryExpanded
                            // VARIABLE VINCULADA: categoryExpanded (Boolean)
                            // ═══════════════════════════════════════════════════════════
                            ExposedDropdownMenuBox(
                                expanded = categoryExpanded,
                                onExpandedChange = { categoryExpanded = !categoryExpanded }
                            ) {
                                // ═══════════════════════════════════════════════════════
                                // IDENTIFICADOR: categoryTextField
                                // TIPO: OutlinedTextField (campo de solo lectura)
                                // PROPIEDADES:
                                //   - value: category
                                //   - readOnly: true (no editable directamente)
                                //   - placeholder: "Selecciona una categoria"
                                //   - trailingIcon: Flecha del dropdown
                                //   - modifier.fillMaxWidth()
                                // VARIABLE VINCULADA: category (String)
                                // ═══════════════════════════════════════════════════════
                                OutlinedTextField(
                                    value = category,
                                    onValueChange = {},
                                    readOnly = true,
                                    placeholder = {
                                        Text(
                                            text = "Selecciona una categoria",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray,
                                        )
                                    },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor()
                                )

                                // ═══════════════════════════════════════════════════════
                                // IDENTIFICADOR: categoryMenu
                                // TIPO: ExposedDropdownMenu (menú desplegable)
                                // CONTENIDO: Lista de categorías + opción "Agregar nueva"
                                // EVENTO: onClick en item → Actualiza category
                                // ═══════════════════════════════════════════════════════
                                ExposedDropdownMenu(
                                    expanded = categoryExpanded,
                                    onDismissRequest = { categoryExpanded = false }
                                ) {
                                    categories.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(item) },
                                            onClick = {
                                                category = item
                                                categoryExpanded = false
                                            }
                                        )
                                    }

                                    DropdownMenuItem(
                                        text = { Text("+ Agregar nueva categoria") },
                                        onClick = {
                                            categoryExpanded = false
                                            showNewCategoryDialog = true
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = "Contenido",
                                style = MaterialTheme.typography.labelLarge,
                                color = AzulPrincipal,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: contentTextField
                            // TIPO: OutlinedTextField (campo multilínea)
                            // PROPIEDADES:
                            //   - value: content (vinculado)
                            //   - onValueChange: Actualiza content
                            //   - placeholder: "Escribe el contenido de tu nota aquí..."
                            //   - minLines: 6 (mínimo 6 líneas visibles)
                            //   - modifier.fillMaxWidth()
                            // VARIABLE VINCULADA: content (String)
                            // ═══════════════════════════════════════════════════════════
                            OutlinedTextField(
                                value = content,
                                onValueChange = { content = it },
                                placeholder = {
                                    Text(
                                        text = "Escribe el contenido de tu nota aquí...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                    )
                                },
                                minLines = 6,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // ═══════════════════════════════════════════════════════════════
                    // IDENTIFICADOR: imageCard
                    // TIPO: Card
                    // DESCRIPCIÓN: Card para URL de imagen con preview
                    // ═══════════════════════════════════════════════════════════════
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = null,
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Imagen Principal",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = AzulPrincipal,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: imageUrlTextField
                            // TIPO: OutlinedTextField
                            // PROPIEDADES:
                            //   - value: imageUrl (vinculado)
                            //   - onValueChange: Actualiza imageUrl
                            //   - placeholder: "https://ejemplo.com/imagen.jpg"
                            //   - singleLine: true
                            //   - shape: RoundedCornerShape(12.dp)
                            //   - colors.focusedBorderColor: AzulPrincipal
                            //   - colors.unfocusedBorderColor: Gray
                            // VARIABLE VINCULADA: imageUrl (String)
                            // VALIDACIÓN: Usado por canPreview para mostrar preview
                            // ═══════════════════════════════════════════════════════════
                            OutlinedTextField(
                                value = imageUrl,
                                onValueChange = { imageUrl = it },
                                placeholder = {
                                    Text(
                                        text = "https://ejemplo.com/imagen.jpg",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AzulPrincipal,
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                )
                            )

                            Text(
                                text = "URL de la imagen principal de la nota",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            // ═══════════════════════════════════════════════════════════
                            // IDENTIFICADOR: imagePreview
                            // TIPO: Card con AsyncImage (condicional)
                            // CONDICIÓN: Solo visible si canPreview == true
                            // PROPIEDADES:
                            //   - modifier.fillMaxWidth().height(160.dp)
                            //   - shape: RoundedCornerShape(12.dp)
                            //   - Image contentScale: Crop
                            // ═══════════════════════════════════════════════════════════
                            if (canPreview) {
                                val previewPainter = rememberAsyncImagePainter(model = imageUrl)
                                if (previewPainter.state !is AsyncImagePainter.State.Error) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(160.dp),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Image(
                                            painter = previewPainter,
                                            contentDescription = "Vista previa",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = null,
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Video para la nota (Opcional)",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = AzulPrincipal,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(onClick = { pickVideoLauncher.launch("video/*") }) {
                                    Icon(Icons.Default.UploadFile, contentDescription = null)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(if (videoUrl.isBlank()) "Cargar video" else "Cambiar video")
                                }

                                if (videoUrl.isNotBlank()) {
                                    Button(onClick = { videoUrl = "" }) {
                                        Icon(Icons.Default.Delete, contentDescription = null)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Quitar")
                                    }
                                }
                            }

                            if (shouldUseVideoView(videoUrl)) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(220.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    AndroidView(
                                        modifier = Modifier.fillMaxSize(),
                                        factory = { context ->
                                            android.widget.VideoView(context).apply {
                                                val mediaController = android.widget.MediaController(context)
                                                mediaController.setAnchorView(this)
                                                setMediaController(mediaController)
                                                setVideoURI(videoUrl.toUri())
                                                setOnPreparedListener {
                                                    mediaController.show()
                                                    start()
                                                    pause()
                                                }
                                            }
                                        },
                                        update = { videoView ->
                                            val uri = videoUrl.toUri()
                                            if (videoView.tag != uri.toString()) {
                                                videoView.tag = uri.toString()
                                                videoView.setVideoURI(uri)
                                                videoView.setOnPreparedListener {
                                                    videoView.start()
                                                    videoView.pause()
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ═══════════════════════════════════════════════════════════════════════
            // IDENTIFICADOR: saveButton
            // TIPO: Button (botón de acción primaria)
            // PROPIEDADES:
            //   - onClick: Crea nueva nota y navega atrás
            //   - modifier.fillMaxWidth(): Ancho completo
            //   - text: "Guardar nota"
            // VALIDACIÓN:
            //   - Verifica que title, description, content, imageUrl no estén vacíos
            // EVENTO: onClick → Crea NoteDetailUi, agrega a repositorio, navega atrás
            // MÉTODO: NotesMockRepository.add(newNote)
            // VARIABLES VINCULADAS: title, description, category, content, isFavorite, imageUrl
            // ═══════════════════════════════════════════════════════════════════════
            Button(
                onClick = {
                    if (title.isBlank() || description.isBlank() || content.isBlank() || imageUrl.isBlank()) return@Button

                    val newNote = NoteDetailUi(
                        id = "",
                        title = title,
                        description = description,
                        createdAtLabel = formattedDate,
                        category = if (category.isBlank()) "Sin categoria" else category,
                        isFavorite = isFavorite,
                        imageRes = 0,
                        imageUrl = imageUrl,
                        markdownContent = content,
                        plainContent = content,
                        gallery = emptyList(),
                        hasVideo = videoUrl.isNotBlank(),
                        videoUrl = videoUrl
                    )

                    NotesMockRepository.add(newNote)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar nota")
            }

            if (showNewCategoryDialog) {
                AlertDialog(
                    onDismissRequest = { showNewCategoryDialog = false },
                    title = { Text("Nueva categoria") },
                    text = {
                        OutlinedTextField(
                            value = newCategoryName,
                            onValueChange = { newCategoryName = it },
                            label = { Text("Nombre") },
                            singleLine = true
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val trimmed = newCategoryName.trim()
                                if (trimmed.isNotEmpty() && !categories.contains(trimmed)) {
                                    categories.add(trimmed)
                                }
                                if (trimmed.isNotEmpty()) {
                                    category = trimmed
                                }
                                newCategoryName = ""
                                showNewCategoryDialog = false
                            }
                        ) {
                            Text("Agregar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                newCategoryName = ""
                                showNewCategoryDialog = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

