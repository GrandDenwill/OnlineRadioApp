package com.example.onlineradioapp.test

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TestDao {
    // Other insertion/deletion/query operations

    @Query("SELECT count(id) FROM items") // items is the table in the @Entity tag of ItemsYouAreStoringInDB.kt, id is a primary key which ensures each entry in DB is unique
    suspend fun numberOfItemsInDB() : Int // suspend keyword to run in coroutine
}