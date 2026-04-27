package com.example.polinotas.ui.screens.notes

import androidx.core.net.toUri
import java.util.Locale

fun isLocalVideoSource(videoUrl: String): Boolean {
    if (videoUrl.isBlank()) return false

    val scheme = runCatching { videoUrl.toUri().scheme?.lowercase(Locale.US) }.getOrNull()

    return scheme == "content" || scheme == "file"
}

fun shouldUseVideoView(videoUrl: String): Boolean {
    return isLocalVideoSource(videoUrl)
}
