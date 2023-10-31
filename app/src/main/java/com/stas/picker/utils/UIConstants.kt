package com.stas.picker.utils

object UIConstants {
    val ALLOWABLE_MIMES_IMAGE: List<String> = listOf(
        "bmp", "gif", "jpg", "jpeg", "jp2", "png", "webp", "ico", "psd",
        "tiff", "raw", "arw", "x3f", "srw", "pef", "rw2", "nef",
        "nrw", "raf", "erf", "crw", "cr2", "orf", "dng", "ai",
        "eps"
    )
    val ALLOWABLE_MIMES_VIDEO: List<String> = listOf(
        "3g2", "3gp", "avi", "flv", "m4v", "wmv", "ogv", "mkv",
        "mov", "mp4", "swf", "mpg", "vob", "asf", "webm","gif"
    )
    val ALLOWABLE_MIME_AUDIO: List<String> = listOf(
        "aac", "midi", "m4a", "oga", "wav", "wma", "flac",
        "mka", "au", "ra", "amr", "ac3", "voc", "mp3", "ogg"
    )
    val ALLOWABLE_MIME_XML: List<String> = listOf(
        "xml", "xlsx"
    )
    val ALLOWABLE_MIME_DOCUMENT: List<String> = listOf("pdf", "docx", "txt", "doc")
    const val ALLOWABLE_MIME_TYPE_ZIP: String = "zip"
}