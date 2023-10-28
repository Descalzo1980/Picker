package com.stas.picker

import com.stas.picker.room.AppDatabase
import com.stas.picker.room.FileItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FileRepositoryImpl(private val appDatabase: AppDatabase) : FileRepository {

    override fun getFile(): Flow<List<FileItem>> = flow {
        emit(appDatabase.fileDao().getAllFile())
    }

    override fun insertAll(files: List<FileItem>): Flow<Unit> = flow {
        appDatabase.fileDao().insertFile(files)
        emit(Unit)
    }
}