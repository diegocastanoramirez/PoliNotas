package com.example.polinotas.ui.screens.notes

import androidx.compose.runtime.mutableStateListOf
import com.example.polinotas.R

object NotesMockRepository {

    private val notes = mutableStateListOf(
        NoteDetailUi(
            id = "1",
            title = "Calculo Integral",
            description = "Integracion por partes y sustitucion",
            createdAtLabel = "2 de abril de 2026",
            category = "Matematicas",
            isFavorite = true,
            imageRes = R.drawable.nota1,
            markdownContent = "# Calculo Integral\n\nMetodo por partes, sustitucion y ejercicios tipo parcial.",
            plainContent = "Calculo Integral\n\nMetodo por partes, sustitucion y ejercicios tipo parcial.",
            gallery = listOf(R.drawable.nota2),
            hasVideo = false
        ),
        NoteDetailUi(
            id = "2",
            title = "Programacion Kotlin",
            description = "Estados en Compose y buenas practicas",
            createdAtLabel = "3 de abril de 2026",
            category = "Programacion",
            isFavorite = false,
            imageRes = R.drawable.nota2,
            markdownContent = "# Kotlin y Compose: \n\nEstado de UI con remember y mutableStateOf.",
            plainContent = "Kotlin y Compose: \n\nEstado de UI con remember y mutableStateOf.",
            gallery = listOf(R.drawable.nota3),
            hasVideo = false
        ),
        NoteDetailUi(
            id = "3",
            title = "Bases de Datos",
            description = "Normalizacion hasta 3FN",
            createdAtLabel = "4 de abril de 2026",
            category = "Bases de Datos",
            isFavorite = false,
            imageRes = R.drawable.nota3,
            markdownContent = "# Normalizacion en bases de datos: \n\n1FN, 2FN y 3FN.",
            plainContent = "Normalizacion en bases de datos: \n\n1FN, 2FN y 3FN.",
            gallery = listOf(R.drawable.nota4),
            hasVideo = false
        ),
        NoteDetailUi(
            id = "4",
            title = "Ingles Tecnico",
            description = "Vocabulario para documentacion de software",
            createdAtLabel = "5 de abril de 2026",
            category = "Idiomas",
            isFavorite = true,
            imageRes = R.drawable.nota4,
            markdownContent = "# Ingles tecnico: \n\nRequirement, feature, bug, fix.",
            plainContent = "Ingles tecnico: \n\nRequirement, feature, bug, fix.",
            gallery = listOf(R.drawable.nota4),
            hasVideo = false
        ),
        NoteDetailUi(
            id = "5",
            title = "Arquitectura de Software",
            description = "Capas, responsabilidad unica y desacople",
            createdAtLabel = "6 de abril de 2026",
            category = "Arquitectura",
            isFavorite = false,
            imageRes = R.drawable.nota5,
            markdownContent = "# Arquitectura\n\nSeparar UI, logica y datos mejora el mantenimiento.",
            plainContent = "Arquitectura\n\nSeparar UI, logica y datos mejora el mantenimiento.",
            gallery = listOf(),
            hasVideo = false
        ),
        NoteDetailUi(
            id = "6",
            title = "Metodologias Agiles",
            description = "Scrum, roles y ceremonias",
            createdAtLabel = "7 de abril de 2026",
            category = "Metodologias",
            isFavorite = false,
            imageRes = R.drawable.nota6,
            markdownContent = "# Scrum\n\nSprint Planning, Daily, Review, Retro.",
            plainContent = "Scrum\n\nSprint Planning, Daily, Review, Retro.",
            gallery = listOf(),
            hasVideo = false
        ),
        NoteDetailUi(
            id = "7",
            title = "Investigacion",
            description = "Busqueda bibliografica y estado del arte",
            createdAtLabel = "8 de abril de 2026",
            category = "Investigacion",
            isFavorite = false,
            imageRes = R.drawable.nota1,
            markdownContent = "# Investigacion\n\nDefinir problema, objetivos y antecedentes.",
            plainContent = "Investigacion\n\nDefinir problema, objetivos y antecedentes.",
            gallery = emptyList(),
            hasVideo = false
        )
    )

    private var nextId = 8

    fun getAll(): List<NoteDetailUi> = notes

    fun getById(id: String): NoteDetailUi? = notes.find { it.id == id }

    fun add(note: NoteDetailUi) {
        val noteToInsert = if (note.id.isBlank()) note.copy(id = nextId++.toString()) else note
        notes.add(0, noteToInsert)
    }

    fun update(note: NoteDetailUi) {
        val index = notes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            notes[index] = note
        }
    }

    fun delete(id: String) {
        notes.removeAll { it.id == id }
    }
}

