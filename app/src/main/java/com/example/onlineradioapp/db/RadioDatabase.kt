package com.example.onlineradioapp.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [RadioEntity::class, GenreEntity::class], version = 1)
//@TypeConverters(TypeTransmogrifier::class)
abstract class RadioDatabase : RoomDatabase() {
    abstract fun todoStore(): RadioEntity.Store

    /*companion object {
        fun newInstance(context: Context) =
            Room.databaseBuilder(context, RadioDatabase::class.java, Constants.Radio_Database).build()

        fun newTestInstance(context: Context) =
            Room.inMemoryDatabaseBuilder(context, RadioDatabase::class.java).build()
    }*/
}