package com.example.onlineradioapp.test

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Toast
import java.io.IOException

class MediaPlayerModel(private var context: Context) {
    private lateinit var mediaPlayer: MediaPlayer
    fun playMusic(URL: String):Boolean{
        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
            }
            mediaPlayer.setDataSource(URL)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            Toast.makeText(context, "Radio Station doesn't work", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    fun stopMusic():Boolean{
        try {
            mediaPlayer.release()
        } catch (e: IOException) {
            Toast.makeText(context, "Something goes wrong", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun buttonReverse(state:Boolean){
        if(state){
            //binding.playButton.visibility = View.INVISIBLE
            //binding.pauseButton.visibility = View.VISIBLE
        }
        else{
            //binding.pauseButton.visibility = View.INVISIBLE
            //binding.playButton.visibility = View.VISIBLE
        }
    }
}