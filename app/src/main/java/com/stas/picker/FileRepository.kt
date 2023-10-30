package com.stas.picker

import com.stas.picker.room.FileItem
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    suspend fun getAllFiles(): List<FileItem>

    suspend fun insertItem(files: FileItem)

    suspend fun deleteFile(file: FileItem)

}