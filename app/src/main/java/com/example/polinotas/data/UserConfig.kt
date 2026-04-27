package com.example.polinotas.data

/**
 * Configuración de datos del usuario para el perfil en el menú
 */
data class UserConfig(
    val name: String,
    val role: String,
    val email: String = ""
) {
    companion object {
        /**
         * Configuración por defecto del usuario
         * Modifica estos valores para personalizar el perfil
         */
        val default = UserConfig(
            name = "María González",
            role = "Ingeniería de Sistemas",
            email = "maria.gonzalez@poligran.edu.co"
        )

        // Alternativa: Cargar desde SharedPreferences
        // fun loadFromPreferences(context: Context): UserConfig { ... }
    }
}
