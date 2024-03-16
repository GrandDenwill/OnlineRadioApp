package com.example.onlineradioapp.repo
import java.util.*
data class RadioModel(
    var radioURL:String,
    var radioName:String = "TestRadio",
    var radioDescription: String = "Description",
    val radioId:String = UUID.randomUUID().toString(),
    var radioGenre:Int = 1,
    var isFavorite:Boolean = false,
    var radioImage: ByteArray?
)
