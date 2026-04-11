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
import com.example.polinotas.ui.screens.notes.Note
import com.example.polinotas.ui.theme.AzulPrincipal
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun NoteItem(note: Note, navController: NavController) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(min = 90.dp) // 🔥 tamaño controlado
            .clickable { navController.navigate("noteDetail")},
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // 🔥 fondo blanco
        )

    )  {

        Row(
            modifier = Modifier.padding(12.dp)
        ) {

            // 🔹 IMAGEN IZQUIERDA
            Image(
                painter = painterResource(id = note.imageRes),
                contentDescription = "Imagen nota",
                modifier = Modifier
                    .size(60.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 🔹 TEXTO DERECHA
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    note.title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    note.description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                Color.LightGray,
                                shape = RoundedCornerShape(50) // 🔥 forma tipo cápsula
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Label, // puedes cambiarlo luego
                            contentDescription = "Categoría",
                            modifier = Modifier.size(14.dp),
                            tint = AzulPrincipal
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = note.category,
                            fontSize = 12.sp,
                            color = AzulPrincipal
                        )
                    }
                }
            }
        }
    }
}