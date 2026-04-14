package com.example.polinotas.ui.screens.notes

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.polinotas.ui.theme.PoliNotasTheme
import org.junit.Rule
import org.junit.Test

/**
 * UI tests para NotesScreen
 */
class NotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun notesScreen_displaysUserName() {
        composeTestRule.setContent {
            PoliNotasTheme {
                NotesScreen(navController = rememberNavController())
            }
        }

        // Abrir drawer
        composeTestRule.onNodeWithContentDescription("Perfil")
            .performClick()

        // Verificar que se muestra el nombre del usuario
        composeTestRule.onNodeWithText("María González")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun notesScreen_displaysUserRole() {
        composeTestRule.setContent {
            PoliNotasTheme {
                NotesScreen(navController = rememberNavController())
            }
        }

        // Abrir drawer
        composeTestRule.onNodeWithContentDescription("Perfil")
            .performClick()

        // Verificar que se muestra el rol
        composeTestRule.onNodeWithText("Ingeniería de Sistemas")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun notesScreen_categoryFilteringWorks() {
        composeTestRule.setContent {
            PoliNotasTheme {
                NotesScreen(navController = rememberNavController())
            }
        }

        // Abrir drawer
        composeTestRule.onNodeWithContentDescription("Perfil")
            .performClick()

        // Expandir categorías
        composeTestRule.onNodeWithText("Categorías")
            .performClick()

        // Seleccionar una categoría
        composeTestRule.onNodeWithText("Programacion")
            .performClick()

        // Verificar que el título cambió
        composeTestRule.onNodeWithText("Mis Notas - Programacion")
            .assertExists()
    }
}
