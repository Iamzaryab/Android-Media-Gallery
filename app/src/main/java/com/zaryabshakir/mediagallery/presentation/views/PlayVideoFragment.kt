package com.zaryabshakir.mediagallery.presentation.views

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.databinding.FragmentPlayVideoBinding
import com.zaryabshakir.mediagallery.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayVideoFragment : BaseFragment<FragmentPlayVideoBinding>() {
    private val args: PlayVideoFragmentArgs by navArgs()
    override val layoutId: Int
        get() = R.layout.fragment_play_video


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(getBinding()) {
            videoView.setVideoURI(args.media.getUri())
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.start()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        with(getBinding()) {
            videoView.isPlaying.takeIf { it }?.let {
                videoView.pause()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        getBinding().videoView.stopPlayback()
    }

}