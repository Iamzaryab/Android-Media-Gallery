package com.zaryabshakir.mediagallery.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.databinding.FragmentPlayVideoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayVideoFragment : Fragment(R.layout.fragment_play_video) {
    private lateinit var binding: FragmentPlayVideoBinding
    private val args: PlayVideoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_video, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            videoView.setVideoURI(args.media.getUri())
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.start()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            if (videoView.isPlaying)
                videoView.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.stopPlayback()
    }

}