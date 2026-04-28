package com.example.polinotas.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Gestor para la imagen de perfil del usuario
 * Persiste la URI de la imagen seleccionada usando SharedPreferences
 */
object ProfileImageManager {
    private const val PREF_NAME = "profile_preferences"
    private const val KEY_PROFILE_IMAGE_URI = "profile_image_uri"

    private var sharedPreferences: SharedPreferences? = null

    /**
     * Inicializa el manager con el contexto de la aplicación
     */
    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    /**
     * Guarda la URI de la imagen de perfil
     */
    fun saveProfileImageUri(uri: String?) {
        sharedPreferences?.edit()?.apply {
            if (uri != null) {
                putString(KEY_PROFILE_IMAGE_URI, uri)
            } else {
                remove(KEY_PROFILE_IMAGE_URI)
            }
            apply()
        }
    }

    /**
     * Obtiene la URI de la imagen de perfil guardada
     * @return URI de la imagen o null si no hay ninguna guardada
     */
    fun getProfileImageUri(): String? {
        return sharedPreferences?.getString(KEY_PROFILE_IMAGE_URI, null)
    }

    /**
     * Elimina la imagen de perfil guardada
     */
    fun clearProfileImage() {
        saveProfileImageUri(null)
    }
}