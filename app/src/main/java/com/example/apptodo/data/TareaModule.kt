package com.example.apptodo.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TareaModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context = context, TareaDB::class.java,"TODO"
    ).build()

    @Singleton
    @Provides
    fun provideDAO(database: TareaDB) = database.tareaDAO()

    @Singleton
    @Provides
    fun provideRepoImpl(tareaDAO: TareaDAO): TareaRepoImpl {
        return TareaRepoImpl(tareaDAO)
    }
}
