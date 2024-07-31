package com.example.apptodo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tarea::class], version = 1)
abstract class TareaDB: RoomDatabase() {
    abstract fun tareaDAO(): TareaDAO
}