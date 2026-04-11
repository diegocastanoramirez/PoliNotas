package com.example.polinotas.ui.screens.notes
import NoteItem
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.polinotas.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController) {

    // 🔹 VARIABLES (estado / datos)
    val notes = listOf(
        Note("Matemáticas", "Estudiar derivadas", "Estudio", R.drawable.nota1),
        Note("Programación", "Practicar Kotlin", "Trabajo", R.drawable.nota2),
        Note("Base de datos", "Repasar joins", "Estudio", R.drawable.nota3),
        Note("Inglés", "Aprender verbos", "Personal", R.drawable.nota4)
    )

    // 🔹 UI
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Notas") }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {

            // 🔹 LISTA (como lo hace tu profesor)
            items(notes) { note ->
                NoteItem(note, navController)
            }
        }
    }
}