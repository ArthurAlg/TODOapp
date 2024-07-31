package com.example.apptodo.data

import kotlinx.coroutines.flow.Flow

interface TareaRepo {
    suspend fun getTODOs(): Flow<List<Tarea>>
    suspend fun getTODObyID(id:Long): Flow<Tarea>
    suspend fun addTODO(tarea: Tarea)
    suspend fun updateTODO(tarea: Tarea)
    suspend fun deleteTODO(tarea: Tarea)
}