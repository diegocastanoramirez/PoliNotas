package com.example.polinotas.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.polinotas.R
import com.example.polinotas.ui.theme.*
@Composable
fun LoginScreen(navController: NavController) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulPrincipal),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // 🔹 IMAGEN REDONDA
                Image(
                    painter = painterResource(id = R.drawable.imagenlogin),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🔹 TÍTULO
                Text(
                    "Poli Notas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                // 🔹 SUBTÍTULO
                Text(
                    "Politécnico Grancolombiano",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 EMAIL
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 🔹 PASSWORD
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 BOTÓN
                Button(
                    onClick = {
                        login(email.value, password.value, navController)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp), // cuadrado
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulPrincipal
                    )
                ) {
                    Text("Iniciar sesión", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 🔹 OLVIDÓ CONTRASEÑA
                Text(
                    "¿Olvidó contraseña?",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
fun login(email: String, password: String, navController: NavController) {

    if (email.isNotEmpty() && password.isNotEmpty()) {
        navController.navigate("notes")
    } else {
        println("Campos vacíos")
    }
}