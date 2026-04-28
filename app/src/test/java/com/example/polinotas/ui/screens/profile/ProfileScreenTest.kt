package com.example.polinotas.ui.screens.profile

import com.example.polinotas.data.Education
import com.example.polinotas.data.UserConfig
import com.example.polinotas.data.WorkExperience
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests para ProfileScreen y funcionalidades de edición de perfil
 */
class ProfileScreenTest {

    @Test
    fun `userConfig default contains all required fields`() {
        val config = UserConfig.default

        assertNotNull(config.name)
        assertNotNull(config.role)
        assertNotNull(config.email)
        assertNotNull(config.about)
        assertNotNull(config.education)
        assertNotNull(config.workExperience)

        assertTrue(config.name.isNotEmpty())
        assertTrue(config.role.isNotEmpty())
        assertTrue(config.email.isNotEmpty())
        assertTrue(config.about.isNotEmpty())
    }

    @Test
    fun `education data structure is valid`() {
        val education = Education(
            institution = "Test University",
            degree = "Computer Science",
            period = "2020-2024",
            description = "Test description"
        )

        assertEquals("Test University", education.institution)
        assertEquals("Computer Science", education.degree)
        assertEquals("2020-2024", education.period)
        assertEquals("Test description", education.description)
    }

    @Test
    fun `workExperience data structure is valid`() {
        val work = WorkExperience(
            company = "Test Corp",
            position = "Developer",
            period = "2024-Present",
            description = "Test work"
        )

        assertEquals("Test Corp", work.company)
        assertEquals("Developer", work.position)
        assertEquals("2024-Present", work.period)
        assertEquals("Test work", work.description)
    }

    @Test
    fun `userConfig with nullable fields works correctly`() {
        val configWithoutExtras = UserConfig(
            name = "Test User",
            role = "Tester",
            email = "test@example.com",
            about = "Testing",
            education = null,
            workExperience = null
        )

        assertEquals("Test User", configWithoutExtras.name)
        assertEquals("Tester", configWithoutExtras.role)
        assertNull(configWithoutExtras.education)
        assertNull(configWithoutExtras.workExperience)
    }

    @Test
    fun `userConfig can be updated with new values`() {
        val originalConfig = UserConfig.default

        val updatedConfig = originalConfig.copy(
            about = "Updated description"
        )

        assertEquals("Updated description", updatedConfig.about)
        assertNotEquals(originalConfig.about, updatedConfig.about)
        assertEquals(originalConfig.name, updatedConfig.name)
    }

    @Test
    fun `education can be updated independently`() {
        val originalEducation = Education(
            institution = "Old University",
            degree = "Old Degree",
            period = "2010-2014",
            description = "Old description"
        )

        val updatedEducation = originalEducation.copy(
            institution = "New University"
        )

        assertEquals("New University", updatedEducation.institution)
        assertEquals("Old Degree", updatedEducation.degree)
    }

    @Test
    fun `workExperience can be updated independently`() {
        val originalWork = WorkExperience(
            company = "Old Corp",
            position = "Junior Dev",
            period = "2020-2022",
            description = "Old job"
        )

        val updatedWork = originalWork.copy(
            position = "Senior Dev"
        )

        assertEquals("Senior Dev", updatedWork.position)
        assertEquals("Old Corp", updatedWork.company)
    }

    @Test
    fun `empty strings are valid for optional fields`() {
        val config = UserConfig(
            name = "Test",
            role = "Tester",
            email = "",
            about = ""
        )

        assertEquals("", config.email)
        assertEquals("", config.about)
    }

    @Test
    fun `userConfig email validation format`() {
        val config = UserConfig.default

        assertTrue(config.email.contains("@"))
        assertTrue(config.email.contains("."))
    }

    @Test
    fun `default userConfig has valid María González data`() {
        val config = UserConfig.default

        assertEquals("María González", config.name)
        assertEquals("Ingeniería de Sistemas", config.role)
        assertEquals("maria.gonzalez@poligran.edu.co", config.email)

        assertNotNull(config.education)
        assertEquals("Escuela Politécnica Nacional", config.education?.institution)

        assertNotNull(config.workExperience)
        assertEquals("Tech Solutions S.A.", config.workExperience?.company)
    }
}
