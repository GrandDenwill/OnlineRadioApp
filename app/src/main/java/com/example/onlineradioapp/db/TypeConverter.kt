package com.example.onlineradioapp.db

import androidx.room.TypeConverter
import java.time.Instant

class TypeConverter {
    @TypeConverter
    fun fromBlob(date: Instant?): Long? = date?.toEpochMilli()

    @TypeConverter
    fun toBlob(millisSinceEpoch: Long?): Instant? = millisSinceEpoch?.let {
        Instant.ofEpochMilli(it)
    }

}