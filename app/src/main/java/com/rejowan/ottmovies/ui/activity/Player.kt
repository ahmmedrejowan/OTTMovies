package com.rejowan.ottmovies.ui.activity

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.rejowan.ottmovies.data.local.PlayHistoryDBHelper
import com.rejowan.ottmovies.databinding.ActivityPlayerBinding
import com.rejowan.ottmovies.model.PlayHistory
import com.rejowan.ottmovies.utils.chooseDemoVideo

class Player : AppCompatActivity() {

    private val binding by lazy { ActivityPlayerBinding.inflate(layoutInflater) }

    private lateinit var exoPlayer: ExoPlayer

    private lateinit var movieID: String

    private val playHistoryDBHelper by lazy { PlayHistoryDBHelper(this) }

    private lateinit var playHistory: PlayHistory
    private var hasSeeked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        hideSystemUI()

        movieID = intent.getStringExtra("movie").toString()


        if (playHistoryDBHelper.isPlayHistoryExists(movieID)) {
            playHistory = playHistoryDBHelper.getPlayHistoryByMovieId(movieID)!!
        } else {
            val url = chooseDemoVideo()
            playHistory = PlayHistory(movieId = movieID, movieUrl = url, timestamp = "0")
            playHistoryDBHelper.insertPlayHistory(playHistory)
        }

        exoPlayer = ExoPlayer.Builder(this).build()

        binding.playerView.player = exoPlayer

        val uri = Uri.parse(playHistory.movieUrl)
        val mediaItem = MediaItem.fromUri(uri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.addListener(object : Player.Listener {

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY && !hasSeeked) {
                    val duration = exoPlayer.duration
                    val savedDuration = playHistory.timestamp.toLong()
                    if (duration * .95 > savedDuration) {
                        exoPlayer.seekTo(savedDuration)
                    } else {
                        exoPlayer.seekTo(0)
                    }
                    hasSeeked = true
                    exoPlayer.play()

                }
            }

        })


    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                hide(WindowInsets.Type.systemBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }


    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
        playHistory.timestamp = exoPlayer.currentPosition.toString()
        playHistoryDBHelper.updateTimeByMovieId(playHistory.movieId, playHistory.timestamp)
    }

    override fun onStop() {
        super.onStop()
        playHistory.timestamp = exoPlayer.currentPosition.toString()
        playHistoryDBHelper.updateTimeByMovieId(playHistory.movieId, playHistory.timestamp)
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        playHistory.timestamp = exoPlayer.currentPosition.toString()
        playHistoryDBHelper.updateTimeByMovieId(playHistory.movieId, playHistory.timestamp)
        exoPlayer.stop()
        exoPlayer.release()
    }

}