package com.example.apptodo.ui

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptodo.data.Tarea
import com.example.apptodo.data.TareaRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaViewModel @Inject constructor(
    private val tareaRepoImpl: TareaRepoImpl
): ViewModel() {

    //Obtener lista
    private val _TODO: MutableStateFlow<List<Tarea>> = MutableStateFlow(emptyList())
    val TODO = _TODO.asStateFlow()

    //Iniciar las tareas ya creadas
    init {
        getTODOs()
    }

    //Instrucciones
    private fun getTODOs(){
        viewModelScope.launch(Dispatchers.IO) {
            tareaRepoImpl.getTODOs().collect(){
                data->
                _TODO.update { data }
            }
        }
    }

    suspend fun getTODObyID(id: Long): Flow<Tarea> {
        return tareaRepoImpl.getTODObyID(id)
    }

    fun addTODO(tarea: Tarea){
        viewModelScope.launch(Dispatchers.IO) {
            tareaRepoImpl.addTODO(tarea)
        }
    }

    fun updateTODO(tarea: Tarea){
        viewModelScope.launch(Dispatchers.IO) {
            tareaRepoImpl.updateTODO(tarea)
        }
    }

    fun deleteTODO(tarea: Tarea){
        viewModelScope.launch(Dispatchers.IO) {
            tareaRepoImpl.deleteTODO(tarea)
        }
    }

}