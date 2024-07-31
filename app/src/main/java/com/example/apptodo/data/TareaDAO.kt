package com.example.apptodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDAO {

    @Query("SELECT * FROM tarea")
    fun getTODO(): Flow<List<Tarea>>

    @Query("SELECT * FROM tarea WHERE id = :id")
    fun getTODObyID(id: Long): Flow<Tarea>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTODO(tarea: Tarea)

    @Update
    suspend fun updateTODO(tarea: Tarea)

    @Delete
    suspend fun deleteTODO(tarea: Tarea)
}