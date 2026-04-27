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
}
