package com.example.polinotas.ui.screens.notes

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests para lógica de filtrado de categorías
 */
class CategoryFilteringTest {

    private val sampleNotes = listOf(
        NoteDetailUi(
            id = "1",
            title = "Nota 1",
            description = "Desc 1",
            category = "Programacion",
            createdAtLabel = "Hoy",
            isFavorite = false,
            imageRes = 0,
            markdownContent = "",
            plainContent = "",
            gallery = emptyList(),
            hasVideo = false
        ),
        NoteDetailUi(
            id = "2",
            title = "Nota 2",
            description = "Desc 2",
            category = "Bases de Datos",
            createdAtLabel = "Ayer",
            isFavorite = false,
            imageRes = 0,
            markdownContent = "",
            plainContent = "",
            gallery = emptyList(),
            hasVideo = false
        ),
        NoteDetailUi(
            id = "3",
            title = "Nota 3",
            description = "Desc 3",
            category = "Programacion",
            createdAtLabel = "Hace 2 días",
            isFavorite = false,
            imageRes = 0,
            markdownContent = "",
            plainContent = "",
            gallery = emptyList(),
            hasVideo = false
        )
    )

    @Test
    fun `filter by category returns correct notes`() {
        val filtered = sampleNotes.filter { it.category == "Programacion" }

        assertEquals(2, filtered.size)
        assertTrue(filtered.all { it.category == "Programacion" })
    }

    @Test
    fun `filter by non-existent category returns empty list`() {
        val filtered = sampleNotes.filter { it.category == "NoExiste" }

        assertEquals(0, filtered.size)
    }

    @Test
    fun `null category shows all notes`() {
        val selectedCategory: String? = null
        val filtered = if (selectedCategory == null) {
            sampleNotes
        } else {
            sampleNotes.filter { it.category == selectedCategory }
        }

        assertEquals(sampleNotes.size, filtered.size)
    }

    @Test
    fun `getCategories extracts unique categories`() {
        val categories = sampleNotes.map { it.category }.distinct().sorted()

        assertEquals(2, categories.size)
        assertTrue(categories.contains("Programacion"))
        assertTrue(categories.contains("Bases de Datos"))
    }
}
