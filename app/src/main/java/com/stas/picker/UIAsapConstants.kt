package com.stas.picker

object UIAsapConstants {
    val ALLOWABLE_MIMES_IMAGE: List<String> = listOf(
        "bmp", "gif", "jpg", "jpeg", "jp2", "png", "webp", "ico", "psd",
        "tiff", "raw", "arw", "x3f", "srw", "pef", "rw2", "nef",
        "nrw", "raf", "erf", "crw", "cr2", "orf", "dng", "ai",
        "eps"
    )
    val ALLOWABLE_MIMES_VIDEO: List<String> = listOf(
        "3g2", "3gp", "avi", "flv", "m4v", "wmv", "ogv", "mkv",
        "mov", "mp4", "swf", "mpg", "vob", "asf", "webm"
    )
    val ALLOWABLE_MIME_AUDIO: List<String> = listOf(
        "aac", "midi", "m4a", "oga", "wav", "wma", "flac",
        "mka", "au", "ra", "amr", "ac3", "voc", "mp3", "ogg"
    )
    val ALLOWABLE_MIME_FILE: List<String> = listOf("pdf", "docx", "txt", "mp3")
    const val ALLOWABLE_MIME_TYPE_ZIP: String = "zip"

}