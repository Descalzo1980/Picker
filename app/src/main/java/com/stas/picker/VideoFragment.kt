package com.stas.picker


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.stas.picker.PickerFragment.Companion.VIDEO_ITEM
import com.stas.picker.databinding.FragmentVideoBinding


class VideoFragment : Fragment() {


    private var binding: FragmentVideoBinding? = null

    private var videoFragment: String? = null

    private var exoPlayer: ExoPlayer? = null
    private val isPlaying get() = exoPlayer?.isPlaying ?: false
    private var playbackPosition = 0L
    private var playWhenReady = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoFragment = arguments?.getString(VIDEO_ITEM)
        initializePlayer()
    }

    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext())
            .build()
        val result = Uri.parse(videoFragment)
        val mediaItem = MediaItem.Builder()
            .setUri(result)
            .build()
        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(requireContext())
        )
            .createMediaSource(mediaItem)
        exoPlayer?.apply {
            setMediaSource(mediaSource)
            playWhenReady = true
            seekTo(0, 0L)
            prepare()
        }.also {
            binding?.playerView?.player = it
        }
    }

//    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
//    private fun preparePlayer() {
//        exoPlayer = ExoPlayer.Builder(requireContext()).build()
//        exoPlayer?.playWhenReady = true
//        binding?.playerView?.player = exoPlayer
//        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
//        val videoPath = arguments?.getString(videoFragment.toString())
//        val mediaItem = MediaItem.Builder().setUri(videoPath?.toUri()).build()
//        val mediaSource = ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
//            .createMediaSource(mediaItem)
//        exoPlayer?.setMediaSource(mediaSource)
//        exoPlayer?.seekTo(playbackPosition)
//        exoPlayer?.playWhenReady = playWhenReady
//        exoPlayer?.prepare()
//    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            exoPlayer = null
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
}