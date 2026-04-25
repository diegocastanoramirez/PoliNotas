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
/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * PANTALLA: LoginScreen
 * DESCRIPCIÓN: Pantalla de inicio de sesión con email y contraseña
 *
 * PUNTO C - IDENTIFICADORES Y PROPIEDADES EN JETPACK COMPOSE:
 * En Compose no se usan IDs tradicionales (android:id), sino variables de estado
 * y nombres de composables. Las propiedades se asignan mediante Modifier y
 * parámetros de los composables.
 * ═══════════════════════════════════════════════════════════════════════════════
 */
@Composable
fun LoginScreen(navController: NavController) {

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: email
    // TIPO: MutableState<String> (variable de estado para campo de texto)
    // USO: Almacena el valor del email ingresado por el usuario
    // ═══════════════════════════════════════════════════════════════════════════
    val email = remember { mutableStateOf("") }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: password
    // TIPO: MutableState<String> (variable de estado para campo de texto)
    // USO: Almacena el valor de la contraseña ingresada por el usuario
    // ═══════════════════════════════════════════════════════════════════════════
    val password = remember { mutableStateOf("") }

    // ═══════════════════════════════════════════════════════════════════════════
    // IDENTIFICADOR: mainContainer
    // TIPO: Column (contenedor vertical)
    // PROPIEDADES:
    //   - modifier.fillMaxSize(): Ocupa todo el espacio disponible
    //   - modifier.background(AzulPrincipal): Fondo azul institucional
    //   - verticalArrangement: Center (contenido centrado verticalmente)
    //   - horizontalAlignment: CenterHorizontally (contenido centrado horizontalmente)
    // ═══════════════════════════════════════════════════════════════════════════
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulPrincipal),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ═══════════════════════════════════════════════════════════════════════════
        // IDENTIFICADOR: loginCard
        // TIPO: Card (contenedor elevado con sombra)
        // PROPIEDADES:
        //   - modifier.padding(16.dp): Margen exterior de 16dp
        //   - modifier.fillMaxWidth(0.9f): Ocupa el 90% del ancho disponible
        //   - shape: RoundedCornerShape(12.dp) (bordes redondeados de 12dp)
        // ═══════════════════════════════════════════════════════════════════════════
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

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: logoImage
                // TIPO: Image (componente de imagen)
                // PROPIEDADES:
                //   - painter: painterResource(R.drawable.imagenlogin) (recurso)
                //   - contentDescription: "Logo" (accesibilidad)
                //   - modifier.size(120.dp): Tamaño de 120dp x 120dp
                //   - modifier.clip(CircleShape): Forma circular
                // ═══════════════════════════════════════════════════════════════════
                Image(
                    painter = painterResource(id = R.drawable.imagenlogin),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: titleText
                // TIPO: Text (componente de texto)
                // PROPIEDADES:
                //   - text: "Poli Notas"
                //   - fontSize: 22.sp (tamaño de fuente)
                //   - fontWeight: Bold (negrita)
                //   - color: AzulPrincipal (#003366)
                // ═══════════════════════════════════════════════════════════════════
                Text(
                    "Poli Notas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulPrincipal
                )

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: subtitleText
                // TIPO: Text (componente de texto)
                // PROPIEDADES:
                //   - text: "Politécnico Grancolombiano"
                //   - fontSize: 14.sp
                //   - fontWeight: SemiBold
                //   - color: Color.DarkGray
                // ═══════════════════════════════════════════════════════════════════
                Text(
                    "Politécnico Grancolombiano",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: emailTextField
                // TIPO: OutlinedTextField (campo de texto con borde)
                // PROPIEDADES:
                //   - value: email.value (vinculado a variable de estado)
                //   - onValueChange: Actualiza email.value
                //   - label: Text("Email")
                //   - modifier.fillMaxWidth(): Ocupa todo el ancho
                //   - shape: RoundedCornerShape(12.dp) (bordes redondeados)
                //   - colors.focusedBorderColor: Gray (borde al enfocar)
                //   - colors.unfocusedBorderColor: Gray (borde sin enfocar)
                //   - colors.cursorColor: Gray (color del cursor)
                // VARIABLE VINCULADA: email (MutableState<String>)
                // EVENTO: onValueChange actualiza el estado
                // ═══════════════════════════════════════════════════════════════════
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp), // 🔥 bordes redondos
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        cursorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: passwordTextField
                // TIPO: OutlinedTextField (campo de texto con borde)
                // PROPIEDADES:
                //   - value: password.value (vinculado a variable de estado)
                //   - onValueChange: Actualiza password.value
                //   - label: Text("Contraseña")
                //   - modifier.fillMaxWidth(): Ocupa todo el ancho
                //   - shape: RoundedCornerShape(12.dp)
                //   - colors.focusedBorderColor: Gray
                //   - colors.unfocusedBorderColor: Gray
                //   - colors.cursorColor: Gray
                // VARIABLE VINCULADA: password (MutableState<String>)
                // EVENTO: onValueChange actualiza el estado
                // ═══════════════════════════════════════════════════════════════════
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        cursorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: loginButton
                // TIPO: Button (botón de acción)
                // PROPIEDADES:
                //   - onClick: Ejecuta función login() con email y password
                //   - modifier.fillMaxWidth(): Ocupa todo el ancho
                //   - shape: RoundedCornerShape(4.dp) (bordes ligeramente redondeados)
                //   - colors.containerColor: AzulPrincipal (fondo azul)
                //   - text: "Iniciar sesión" (color blanco)
                // EVENTO: onClick → login(email, password, navController)
                // MÉTODO VINCULADO: login()
                // ═══════════════════════════════════════════════════════════════════
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

                // ═══════════════════════════════════════════════════════════════════
                // IDENTIFICADOR: forgotPasswordText
                // TIPO: Text (componente de texto)
                // PROPIEDADES:
                //   - text: "¿Olvidaste tu contraseña?"
                //   - fontSize: 12.sp
                //   - color: AzulClaro (#4A90E2)
                // ═══════════════════════════════════════════════════════════════════
                Text(
                    "¿Olvidaste tu contraseña?",
                    fontSize = 12.sp,
                    color = AzulClaro
                )
            }
        }
    }
}

/*
 * ═══════════════════════════════════════════════════════════════════════════════
 * FUNCIÓN/MÉTODO: login
 * DESCRIPCIÓN: Valida credenciales y navega a la pantalla de notas
 *
 * PARÁMETROS:
 *   - email: String (email ingresado)
 *   - password: String (contraseña ingresada)
 *   - navController: NavController (controlador de navegación)
 *
 * LÓGICA:
 *   1. Valida que email y password no estén vacíos
 *   2. Si son válidos: navega a "notes"
 *   3. Si no: imprime mensaje de error en consola
 *
 * VARIABLES VINCULADAS: email, password (de LoginScreen)
 * ═══════════════════════════════════════════════════════════════════════════════
 */
fun login(email: String, password: String, navController: NavController) {

    if (email.isNotEmpty() && password.isNotEmpty()) {
        navController.navigate("notes")
    } else {
        println("Campos vacíos")
    }
}