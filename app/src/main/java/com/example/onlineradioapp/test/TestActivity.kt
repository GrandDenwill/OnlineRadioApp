package com.example.onlineradioapp.test

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.net.Uri
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.session.MediaSession
import com.example.onlineradioapp.R
import com.example.onlineradioapp.databinding.ActivityTestBinding

const val CHANNEL_ID:String = "1488"
const val notificationId:Int = 1337
class TestActivity : AppCompatActivity() {
    var binding: ActivityTestBinding?=null
    var mediaSession:MediaSession?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.button.setOnClickListener {
            onButton()
        }

            // Create the NotificationChannel.
    }
    private fun createNotificationChannel(){
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }
    @OptIn(UnstableApi::class) @SuppressLint("MissingPermission")
    private fun createNotification(){
        val pauseIntent = Intent(this, TestActivity::class.java)
        pauseIntent.flags =FLAG_ACTIVITY_SINGLE_TOP
        pauseIntent.putExtra("fun", "stop")
        val pausePendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val playIntent = Intent(this, TestActivity::class.java)
        playIntent.flags =FLAG_ACTIVITY_SINGLE_TOP
        playIntent.putExtra("fun", "play")
        //val playPendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                var notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_about)
            .addAction(R.drawable.pause_icon, "Pause", pausePendingIntent)
            //.addAction(R.drawable.play_icon, "Play", playPendingIntent)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0 /* #1: pause button \*/)
                .setMediaSession(mediaSession?.sessionCompatToken)
            )
            .setContentTitle("Wonderful music")
            .setContentText("My Awesome Band")
            .build()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define.
            notify(notificationId, notification)
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val arguments = intent?.extras
        val str = arguments?.getString("fun")
        if (arguments != null&&arguments.getString("fun").equals("stop")) {
            //if (arguments.getString("fun").equals("stop")) stop()
            //if (arguments.getString("fun").equals("play")) play()
            stop()
        }
    }
    @OptIn(UnstableApi::class) override fun onStart() {
        super.onStart()
        /*val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        mediaController = MediaController.Builder(this, sessionToken).buildAsync()
        mediaController!!.addListener(
            {
                // Call controllerFuture.get() to retrieve the MediaController.
                // MediaController implements the Player interface, so it can be
                // attached to the PlayerView UI component.
                binding!!.playerView.player = mediaController!!.get()
            },
            MoreExecutors.directExecutor()
        )*/
    }
    /*fun foregroundMusic(){
        val controller = mediaSession?.controllerForCurrentRequest
        val mediaMetadata = controller.mediaController
        val description = mediaMetadata.description

        val builder = NotificationCompat.Builder(context, channelId).apply {
            // Add the metadata for the currently playing track
            setContentTitle(description.title)
            setContentText(description.subtitle)
            setSubText(description.description)
            setLargeIcon(description.iconBitmap)

            // Enable launching the player by clicking the notification
            setContentIntent(controller.sessionActivity)

            // Stop the service when the notification is swiped away
           setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_STOP
                )
            )

            // Make the transport controls visible on the lockscreen
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            // Add an app icon and set its accent color
            // Be careful about the color
            setSmallIcon(R.drawable.notification_icon)
            color = ContextCompat.getColor(context, R.color.primaryDark)

            // Add a pause button
            addAction(
                NotificationCompat.Action(
                    R.drawable.pause,
                    getString(R.string.pause),
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        context,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                    )
                )
            )

            // Take advantage of MediaStyle features
            setStyle(android.support.v4.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession.sessionToken)
                .setShowActionsInCompactView(0)

                // Add a cancel button
                .setShowCancelButton(true)
                .setCancelButtonIntent(
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this,
                        PlaybackStateCompat.ACTION_STOP
                    )
                )
            )
        }

// Display the notification and place the service in the foreground
        startForeground(id, builder.build())*/
    //}
    fun onButton(){
        //mediaSession?.player?.pause()
        //mediaController?.get()?.pause()
        play()
        createNotificationChannel()
        createNotification()
    }
    @OptIn(UnstableApi::class)
    fun play() {
        val player = ExoPlayer.Builder(this).build()
        player.setMediaSource(ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(Uri.parse("http://pub0201.101.ru:8000/stream/pull/aac/64/301"))))
        mediaSession = mediaSession?: MediaSession.Builder(this, player).build()
        mediaSession?.player?.prepare()
        mediaSession?.player?.play()
    }
    fun stop(){
        mediaSession?.player?.stop()
    }
}