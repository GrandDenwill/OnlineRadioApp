package com.example.onlineradioapp.test

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    // Create your Player and MediaSession in the onCreate lifecycle event
    @OptIn(UnstableApi::class) override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        player.setMediaSource(ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(Uri.parse("http://pub0201.101.ru:8000/stream/pull/aac/64/301"))))
        mediaSession = MediaSession.Builder(this, player).build()
        (mediaSession!!.player as ExoPlayer).setMediaSource(ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(Uri.parse("http://pub0201.101.ru:8000/stream/pull/aac/64/301"))))
    }

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession? = mediaSession
}