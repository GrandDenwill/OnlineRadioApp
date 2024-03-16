package com.example.onlineradioapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.example.onlineradioapp.repo.RadioModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RadioPlayer @Inject constructor ( @ApplicationContext var context: Context) {
    private val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
    var mediaPlayer = ExoPlayer.Builder(context).build()
    @OptIn(UnstableApi::class) fun playMusic(radio: RadioModel, showMusic:()->Unit){
        try {
            Toast.makeText(context,"PlayMusic", Toast.LENGTH_SHORT).show()
            mediaPlayer.apply {
                setMediaSource(getProgressiveMediaSource(radio.radioURL))
                prepare()
                addListener(object: Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        when(playbackState){
                            Player.STATE_READY -> {
                                play()
                            }
                        }
                    }
                }
                )
                addAnalyticsListener(object : AnalyticsListener {
                    override fun onMediaMetadataChanged(
                        eventTime: AnalyticsListener.EventTime,
                        mediaMetadata: MediaMetadata
                    ) {
                        super.onMediaMetadataChanged(eventTime, mediaMetadata)
                        showMusic()
                    }
                })
            }
        } catch (e: IOException) {
            Toast.makeText(context, "Radio Station doesn't work", Toast.LENGTH_SHORT).show()
        }
    }
    @OptIn(UnstableApi::class)  fun getProgressiveMediaSource(url:String): MediaSource {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun pause(){
        mediaPlayer.playWhenReady = false
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.playWhenReady = true
        }
    }
    fun getMeta()= mediaPlayer.mediaMetadata
}