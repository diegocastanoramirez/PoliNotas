package com.example.polinotas.ui.screens.notes

data class Note(
    val title: String,
    val description: String,
    val category: String,
    val imageRes: Int
)

data class NoteDetailUi(
    val id: String,
    val title: String,
    val description: String,
    val createdAtLabel: String,
    val category: String,
    val isFavorite: Boolean,
    val imageRes: Int,
    val imageUrl: String = "",
    val markdownContent: String,
    val plainContent: String,
    val gallery: List<Int>,
    val hasVideo: Boolean,
    val videoUrl: String = ""
)