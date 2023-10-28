package com.stas.picker.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class FileItem(
    @PrimaryKey val uri: String,
    val size: Float,
    val name: String,
    val extension: String
)
