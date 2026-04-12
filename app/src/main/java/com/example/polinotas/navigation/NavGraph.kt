package com.example.polinotas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.polinotas.ui.screens.login.LoginScreen
import com.example.polinotas.ui.screens.notes.CreateNoteScreen
import com.example.polinotas.ui.screens.notes.NoteDetailScreen
import com.example.polinotas.ui.screens.notes.NotesScreen
import com.example.polinotas.ui.screens.profile.ProfileScreen

@Composable
fun PoliNotasApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(navController) }
        composable("notes") { NotesScreen(navController) }
        composable("profile/{title}") { backStackEntry ->

            val title = backStackEntry.arguments?.getString("title") ?: ""

            ProfileScreen(title, navController)
        }
        composable("noteCreate") { CreateNoteScreen(navController) }
        composable("noteDetail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            NoteDetailScreen(noteId = noteId, navController = navController)
        }
    }
}