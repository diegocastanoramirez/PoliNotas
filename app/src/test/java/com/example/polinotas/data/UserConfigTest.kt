package com.example.polinotas.data

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests para UserConfig
 */
class UserConfigTest {

    @Test
    fun `default user config has correct values`() {
        val config = UserConfig.default

        assertEquals("María González", config.name)
        assertEquals("Ingeniería de Sistemas", config.role)
        assertNotNull(config.email)
    }

    @Test
    fun `user config can be created with custom values`() {
        val config = UserConfig(
            name = "Juan Pérez",
            role = "Ingeniería Industrial",
            email = "juan@test.com"
        )

        assertEquals("Juan Pérez", config.name)
        assertEquals("Ingeniería Industrial", config.role)
        assertEquals("juan@test.com", config.email)
    }

    @Test
    fun `email has default empty value`() {
        val config = UserConfig(
            name = "Test",
            role = "Test Role"
        )

        assertEquals("", config.email)
    }

    @Test
    fun `default user config has education and work experience`() {
        val config = UserConfig.default

        assertNotNull(config.education)
        assertNotNull(config.workExperience)

        // Verificar education
        assertEquals("Escuela Politécnica Nacional", config.education?.institution)
        assertEquals("Ingeniería de Sistemas", config.education?.degree)
        assertEquals("2020 - Presente", config.education?.period)
        assertNotNull(config.education?.description)

        // Verificar work experience
        assertEquals("Tech Solutions S.A.", config.workExperience?.company)
        assertEquals("Desarrolladora Android Junior", config.workExperience?.position)
        assertEquals("Ene 2024 - Presente", config.workExperience?.period)
        assertNotNull(config.workExperience?.description)
    }

    @Test
    fun `user config can be created with null education and experience`() {
        val config = UserConfig(
            name = "Test User",
            role = "Test Role",
            email = "test@test.com",
            about = "Test about",
            education = null,
            workExperience = null
        )

        assertNull(config.education)
        assertNull(config.workExperience)
    }

    @Test
    fun `user config can be created with custom education`() {
        val education = Education(
            institution = "Universidad Test",
            degree = "Ingeniería Test",
            period = "2021 - 2025",
            description = "Descripción test"
        )

        val config = UserConfig(
            name = "Test",
            role = "Test",
            education = education
        )

        assertEquals("Universidad Test", config.education?.institution)
        assertEquals("Ingeniería Test", config.education?.degree)
    }

    @Test
    fun `user config can be created with custom work experience`() {
        val workExp = WorkExperience(
            company = "Empresa Test",
            position = "Posición Test",
            period = "2023 - 2024",
            description = "Descripción test"
        )

        val config = UserConfig(
            name = "Test",
            role = "Test",
            workExperience = workExp
        )

        assertEquals("Empresa Test", config.workExperience?.company)
        assertEquals("Posición Test", config.workExperience?.position)
    }

    @Test
    fun `education data class has all required fields`() {
        val education = Education(
            institution = "Test Institution",
            degree = "Test Degree",
            period = "2020-2024",
            description = "Test Description"
        )

        assertEquals("Test Institution", education.institution)
        assertEquals("Test Degree", education.degree)
        assertEquals("2020-2024", education.period)
        assertEquals("Test Description", education.description)
    }

    @Test
    fun `work experience data class has all required fields`() {
        val workExp = WorkExperience(
            company = "Test Company",
            position = "Test Position",
            period = "2023-2024",
            description = "Test Description"
        )

        assertEquals("Test Company", workExp.company)
        assertEquals("Test Position", workExp.position)
        assertEquals("2023-2024", workExp.period)
        assertEquals("Test Description", workExp.description)
    }
}
