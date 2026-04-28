package com.example.polinotas.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.polinotas.R
import com.example.polinotas.data.ProfileImageManager
import com.example.polinotas.data.UserConfig
import com.example.polinotas.ui.components.InfoCard
import com.example.polinotas.ui.theme.*

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
    // IDENTIFICADORES - DATOS DEL USUARIO (desde UserConfig)
    // ═══════════════════════════════════════════════════════════════════════════
    val userConfig = UserConfig.default
    val nombre = userConfig.name
    val carrera = userConfig.role
    val correo = userConfig.email
    val acerca = userConfig.about

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADORES - MÉTRICAS DEL USUARIO
    // ═══════════════════════════════════════════════════════════════════════════
    val totalNotas = 5
    val totalProyectos = 2
    val semestre = 6

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADORES - ESTADOS PARA AMPLIAR Y CAMBIAR FOTO
    // ═══════════════════════════════════════════════════════════════════════════
    var showImageDialog by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf(ProfileImageManager.getProfileImageUri()) }

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

    // Estados para edición de campos
    var isEditingAbout by remember { mutableStateOf(false) }
    var editableAbout by remember { mutableStateOf(acerca) }

    var isEditingEducation by remember { mutableStateOf(false) }
    var editableEduInstitution by remember { mutableStateOf(userConfig.education?.institution ?: "") }
    var editableEduDegree by remember { mutableStateOf(userConfig.education?.degree ?: "") }
    var editableEduPeriod by remember { mutableStateOf(userConfig.education?.period ?: "") }
    var editableEduDescription by remember { mutableStateOf(userConfig.education?.description ?: "") }

    var isEditingWork by remember { mutableStateOf(false) }
    var editableWorkCompany by remember { mutableStateOf(userConfig.workExperience?.company ?: "") }
    var editableWorkPosition by remember { mutableStateOf(userConfig.workExperience?.position ?: "") }
    var editableWorkPeriod by remember { mutableStateOf(userConfig.workExperience?.period ?: "") }
    var editableWorkDescription by remember { mutableStateOf(userConfig.workExperience?.description ?: "") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header azul con foto de perfil + "Poli Notas"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AzulPrincipal)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { showImageDialog = true }
                    ) {
                        if (selectedImageUri != null) {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Perfil personalizado",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White, CircleShape)
                                    .clip(CircleShape)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.perfil_usuario),
                                contentDescription = "Perfil",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White, CircleShape)
                                    .clip(CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "Poli Notas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Barra blanca con botón Volver
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(64.dp)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
            }
        }
    ) { paddingValues ->

        // ═══════════════════════════════════════════════════════════════════════
        // ═══════════════════════════════════════════════════════════════════════
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

        // ═══════════════════════════════════════════════════════════════════════
        //   - modifier.fillMaxWidth(): Ancho completo (permite scroll vertical)
        //   - elevation: 6.dp
        // ═══════════════════════════════════════════════════════════════════════
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column {

                // ═══════════════════════════════════════════════════════════════════
                // ═══════════════════════════════════════════════════════════════════
                Box {

                    Column {
                        // ═══════════════════════════════════════════════════════════
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
                    // ═══════════════════════════════════════════════════════════════
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.TopCenter)
                            .offset(y = 60.dp)
                            .clickable { showImageDialog = true }
                    ) {
                        if (selectedImageUri != null) {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Foto perfil personalizada",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White, CircleShape)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.perfil_usuario),
                                contentDescription = "Foto perfil",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White, CircleShape)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }

                // ═══════════════════════════════════════════════════════════════════
                //   - horizontalAlignment: CenterHorizontally
                //   - modifier.fillMaxWidth()
                //   - modifier.padding(horizontal = 16.dp)
                // ═══════════════════════════════════════════════════════════════════
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

                // ═══════════════════════════════════════════════════════════════════
                //   - modifier.fillMaxWidth()
                //   - modifier.padding(horizontal = 8.dp)
                //   - elevation: 4.dp
                //   - containerColor: White
                //   - Altura automática (permite texto completo sin truncar)
                // ═══════════════════════════════════════════════════════════════════
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
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

                            IconButton(onClick = {
                                if (isEditingAbout) {
                                    isEditingAbout = false
                                } else {
                                    isEditingAbout = true
                                }
                            }) {
                                Icon(
                                    imageVector = if (isEditingAbout) Icons.Default.Save else Icons.Default.Edit,
                                    contentDescription = if (isEditingAbout) "Guardar" else "Editar",
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        if (isEditingAbout) {
                            OutlinedTextField(
                                value = editableAbout,
                                onValueChange = { editableAbout = it },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3,
                                maxLines = 5
                            )
                        } else {
                            Text(editableAbout)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
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
                                    imageVector = Icons.Default.School,
                                    contentDescription = "Educación",
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Educación", fontWeight = FontWeight.Bold)
                            }

                            IconButton(onClick = {
                                isEditingEducation = !isEditingEducation
                            }) {
                                Icon(
                                    imageVector = if (isEditingEducation) Icons.Default.Save else Icons.Default.Edit,
                                    contentDescription = if (isEditingEducation) "Guardar" else "Editar",
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (isEditingEducation) {
                            OutlinedTextField(
                                value = editableEduInstitution,
                                onValueChange = { editableEduInstitution = it },
                                label = { Text("Institución") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editableEduDegree,
                                onValueChange = { editableEduDegree = it },
                                label = { Text("Título") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editableEduPeriod,
                                onValueChange = { editableEduPeriod = it },
                                label = { Text("Período") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editableEduDescription,
                                onValueChange = { editableEduDescription = it },
                                label = { Text("Descripción") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 2
                            )
                        } else {
                            Text(
                                text = editableEduInstitution,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = AzulPrincipal
                            )
                            Text(text = editableEduDegree, fontSize = 13.sp, color = Color.DarkGray)
                            Text(
                                text = editableEduPeriod,
                                fontSize = 11.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            if (editableEduDescription.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = editableEduDescription,
                                    fontSize = 12.sp,
                                    color = Color(0xFF555555),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
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
                                    imageVector = Icons.Default.Work,
                                    contentDescription = "Experiencia Laboral",
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Experiencia Laboral", fontWeight = FontWeight.Bold)
                            }

                            IconButton(onClick = {
                                isEditingWork = !isEditingWork
                            }) {
                                Icon(
                                    imageVector = if (isEditingWork) Icons.Default.Save else Icons.Default.Edit,
                                    contentDescription = if (isEditingWork) "Guardar" else "Editar",
                                    tint = AzulPrincipal,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (isEditingWork) {
                            OutlinedTextField(
                                value = editableWorkCompany,
                                onValueChange = { editableWorkCompany = it },
                                label = { Text("Empresa") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editableWorkPosition,
                                onValueChange = { editableWorkPosition = it },
                                label = { Text("Cargo") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editableWorkPeriod,
                                onValueChange = { editableWorkPeriod = it },
                                label = { Text("Período") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = editableWorkDescription,
                                onValueChange = { editableWorkDescription = it },
                                label = { Text("Descripción") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 2
                            )
                        } else {
                            Text(
                                text = editableWorkCompany,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = AzulPrincipal
                            )
                            Text(text = editableWorkPosition, fontSize = 13.sp, color = Color.DarkGray)
                            Text(
                                text = editableWorkPeriod,
                                fontSize = 11.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            if (editableWorkDescription.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = editableWorkDescription,
                                    fontSize = 12.sp,
                                    color = Color(0xFF555555),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ═══════════════════════════════════════════════════════════════════
                //   - modifier.fillMaxWidth()
                //   - modifier.padding(horizontal = 16.dp)
                //   - horizontalArrangement: SpaceBetween (distribuidos uniformemente)
                // ═══════════════════════════════════════════════════════════════════
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // ═══════════════════════════════════════════════════════════════
                    //   - label: "Notas"
                    //   - value: totalNotas (5)
                    //   - color: AzulClaro
                    // ═══════════════════════════════════════════════════════════════
                    InfoCard("Notas", totalNotas.toString(), AzulClaro)

                    // ═══════════════════════════════════════════════════════════════
                    //   - label: "Proyectos"
                    //   - value: totalProyectos (2)
                    //   - color: Amarillo
                    // ═══════════════════════════════════════════════════════════════
                    InfoCard("Proyectos", totalProyectos.toString(), Amarillo)

                    // ═══════════════════════════════════════════════════════════════
                    //   - label: "Semestre"
                    //   - value: semestre (6)
                    //   - color: AzulPrincipal
                    // ═══════════════════════════════════════════════════════════════
                    InfoCard("Semestre", semestre.toString(), AzulPrincipal)
                }
            }
        }
        } // Cierra Column principal
    } // Cierra Scaffold

    // ═══════════════════════════════════════════════════════════════════════════
    // DIÁLOGO: Ampliar imagen y cambiar foto
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

/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMPONENTE: ProfileSectionCard
 * DESCRIPCIÓN: Tarjeta reutilizable para secciones del perfil (Educación, Experiencia)
 * ═══════════════════════════════════════════════════════════════════════════════
 */
@Composable
private fun ProfileSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = AzulPrincipal,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMPONENTE: ProfileEntryItem
 * DESCRIPCIÓN: Item individual para mostrar entrada de educación o experiencia
 * ═══════════════════════════════════════════════════════════════════════════════
 */
@Composable
private fun ProfileEntryItem(
    title: String,
    subtitle: String,
    period: String,
    description: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = AzulPrincipal
        )
        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = Color.DarkGray
        )
        Text(
            text = period,
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 2.dp)
        )
        if (description.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xFF555555),
                lineHeight = 16.sp
            )
        }
    }
}