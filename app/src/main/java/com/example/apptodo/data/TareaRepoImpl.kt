package com.example.apptodo.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TareaRepoImpl @Inject constructor(
    private val dao: TareaDAO
): TareaRepo {

    override suspend fun getTODOs(): Flow<List<Tarea>> = dao.getTODO()
    override suspend fun getTODObyID(id: Long): Flow<Tarea> = dao.getTODObyID(id)
    override suspend fun addTODO(tarea: Tarea){
        dao.addTODO(tarea)
    }
    override suspend fun updateTODO(tarea: Tarea){
        dao.updateTODO(tarea)
    }
    override suspend fun deleteTODO(tarea: Tarea){
        dao.deleteTODO(tarea)
    }

}