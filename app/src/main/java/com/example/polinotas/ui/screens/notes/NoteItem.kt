package com.example.polinotas.ui.screens.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.polinotas.ui.theme.AzulPrincipal
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun NoteItem(note: NoteDetailUi, navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(min = 90.dp) // 🔥 tamaño controlado
            .clickable { navController.navigate("noteDetail/${note.id}") },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // 🔥 fondo blanco
        )

    ) {

        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            // 🔹 IMAGEN IZQUIERDA (URL o recurso)
            if (note.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = note.imageUrl,
                    contentDescription = note.title,
                    modifier = Modifier
                        .size(60.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = note.imageRes),
                    contentDescription = note.title,
                    modifier = Modifier
                        .size(60.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 🔹 TEXTO DERECHA
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = note.title,
                        color = AzulPrincipal,
                        fontWeight = FontWeight.Bold,
                    )

                    if (note.isFavorite) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorita",
                            tint = Color(0xFFFFC107),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = note.description,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = note.createdAtLabel,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                Color(0xFFE8EEF8),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Label,
                            contentDescription = "Categoria",
                            modifier = Modifier.size(12.dp),
                            tint = AzulPrincipal
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = note.category,
                            fontSize = 11.sp,
                            color = AzulPrincipal
                        )
                    }
                }
            }
        }
    }
}