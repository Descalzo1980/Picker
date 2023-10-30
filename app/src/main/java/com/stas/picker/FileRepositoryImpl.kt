package com.stas.picker

import com.stas.picker.room.AppDatabase
import com.stas.picker.room.FileItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FileRepositoryImpl(private val appDatabase: AppDatabase) : FileRepository {

    override suspend fun getAllFiles(): Flow<List<FileItem>> = withContext(Dispatchers.IO) {
        flow { emit(appDatabase.fileDao().getAllFile()) }
    }

    override suspend fun insertItem(files: FileItem): Flow<Unit> =
        withContext(Dispatchers.IO) {
            flow { emit(appDatabase.fileDao().insertFile(files)) }
        }


    override suspend fun deleteFile(file: FileItem) {
        appDatabase.fileDao().deleteFile(file)
    }
}