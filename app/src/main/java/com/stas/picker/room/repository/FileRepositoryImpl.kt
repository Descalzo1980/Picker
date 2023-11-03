package com.stas.picker.room.repository

import com.stas.picker.room.AppDatabase
import com.stas.picker.room.FileItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FileRepositoryImpl(private val appDatabase: AppDatabase) : FileRepository {

    override suspend fun getAllFiles(): List<FileItem> = withContext(Dispatchers.IO) {
        appDatabase.fileDao().getAllFile()
    }

    override suspend fun insertItem(files: FileItem) {
        withContext(Dispatchers.IO) {
            appDatabase.fileDao().insertFile(files)
        }
    }


    override suspend fun deleteFile(file: FileItem) {
        appDatabase.fileDao().deleteFile(file)
    }
}