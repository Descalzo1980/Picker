package com.stas.picker.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stas.picker.model.FileCategory

@Entity(tableName = "files")
data class FileItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val uri: String,
    val size: Float,
    val name: String,
    val extension: String
)
