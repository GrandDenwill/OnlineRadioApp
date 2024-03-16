package com.example.onlineradioapp.test

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class TestEntity(var item:String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}