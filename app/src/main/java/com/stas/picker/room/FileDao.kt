package com.stas.picker.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FileDao {

    @Insert
    fun insertFile(file: List<FileItem>)

    @Query("SELECT * from files order by uri ASC")
    fun getAllFile(): List<FileItem>
}