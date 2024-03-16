package com.example.onlineradioapp.test

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TestEntity::class], // Tell the database the entries will hold data of this type
    version = 1
)

abstract class TestBase : RoomDatabase() {

    abstract fun getDao(): TestDao
}