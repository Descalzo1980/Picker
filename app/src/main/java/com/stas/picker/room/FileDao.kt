package com.stas.picker.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFile(file: FileItem)

    @Delete
    fun deleteFile(file: FileItem)

    @Query("SELECT * FROM files order by uri ASC")
    fun getAllFile(): List<FileItem>
}