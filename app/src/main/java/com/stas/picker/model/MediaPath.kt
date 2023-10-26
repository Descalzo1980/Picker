package com.stas.picker.model

import java.util.Date

data class MediaPath(val uri: String, val isVideo: Boolean = false, val date: Date, val duration: Long = 0)
