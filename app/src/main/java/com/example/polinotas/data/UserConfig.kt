package com.example.polinotas.data

/**
 * Datos de educación del usuario
 */
data class Education(
    val institution: String,
    val degree: String,
    val period: String,
    val description: String
)

/**
 * Datos de experiencia laboral del usuario
 */
data class WorkExperience(
    val company: String,
    val position: String,
    val period: String,
    val description: String
)

/**
 * Configuración de datos del usuario para el perfil en el menú
 */
data class UserConfig(
    val name: String,
    val role: String,
    val email: String = "",
    val about: String = "",
    val education: Education? = null,
    val workExperience: WorkExperience? = null
) {
    companion object {
        /**
         * Configuración por defecto del usuario
         * Modifica estos valores para personalizar el perfil
         */
        val default = UserConfig(
            name = "María González",
            role = "Ingeniería de Sistemas",
            email = "maria.gonzalez@poligran.edu.co",
            about = "Estudiante apasionada por el desarrollo de software.",
            education = Education(
                institution = "Escuela Politécnica Nacional",
                degree = "Ingeniería de Sistemas",
                period = "2020 - Presente",
                description = "Especialización en desarrollo de software y arquitectura de sistemas"
            ),
            workExperience = WorkExperience(
                company = "Tech Solutions S.A.",
                position = "Desarrolladora Android Junior",
                period = "Ene 2024 - Presente",
                description = "Desarrollo de aplicaciones móviles con Kotlin y Jetpack Compose"
            )
        )

        // Alternativa: Cargar desde SharedPreferences
        // fun loadFromPreferences(context: Context): UserConfig { ... }
    }
}
