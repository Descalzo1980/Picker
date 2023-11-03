package com.stas.picker.room.repository

import com.stas.picker.room.FileItem

interface FileRepository {

    suspend fun getAllFiles(): List<FileItem>

    suspend fun insertItem(files: FileItem)

    suspend fun deleteFile(file: FileItem)

}