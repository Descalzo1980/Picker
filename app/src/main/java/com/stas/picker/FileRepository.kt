package com.stas.picker

import com.stas.picker.room.FileItem
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    fun getFile(): Flow<List<FileItem>>

    fun insertAll(files: List<FileItem>): Flow<Unit>

}