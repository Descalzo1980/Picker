package com.stas.picker

import com.stas.picker.room.FileItem
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    suspend fun getAllFiles(): Flow<List<FileItem>>

    suspend fun insertItem(files: FileItem): Flow<Unit>

    suspend fun deleteFile(file: FileItem)

}