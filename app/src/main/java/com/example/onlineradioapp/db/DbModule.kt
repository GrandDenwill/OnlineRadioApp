package com.example.onlineradioapp.db

import android.content.Context
import androidx.room.Room
import com.example.onlineradioapp.utils.Constants.RADIO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context,
        RadioDatabase::class.java, RADIO_DATABASE )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    @Singleton
    @Provides
    fun provideDao(db: RadioDatabase)= db.todoStore()
}