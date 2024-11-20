package com.zaryabshakir.mediagallery.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.databinding.FragmentMediaBinding
import com.zaryabshakir.mediagallery.presentation.adaptors.MediaItemsAdaptor
import com.zaryabshakir.mediagallery.presentation.events.BucketUIEvent
import com.zaryabshakir.mediagallery.presentation.events.MediaIntent
import com.zaryabshakir.mediagallery.presentation.events.MediaUIEvent
import com.zaryabshakir.mediagallery.presentation.viewmodels.MediaViewModel
import com.zaryabshakir.mediagallery.uimodel.MediaUIDataModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MediaFragment : Fragment(R.layout.fragment_media) {
    private lateinit var binding: FragmentMediaBinding
    private val viewModel: MediaViewModel by viewModels()
    private lateinit var mediaAdaptor: MediaItemsAdaptor
    private val args: MediaFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_media, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.onEvent(MediaIntent.FetchMedia(args.bucket))
                viewModel.state.collect { event ->
                    when (event) {
                        is MediaUIEvent.OnFetchMedia -> {
                            binding.listBuckets.apply {
                                mediaAdaptor = MediaItemsAdaptor(
                                    media = event.media,
                                    onMediaSelected = { onMediaClicked(it) })
                                adapter = mediaAdaptor
                            }
                        }

                        is MediaUIEvent.Loading -> BucketUIEvent.Loading
                    }
                }
            }
        }

    }

    private fun onMediaClicked(mediaUIDataModel: MediaUIDataModel) {
        if (mediaUIDataModel.isVideo()) {
            findNavController().navigate(
                MediaFragmentDirections.actionMediaFragmentToPlayVideoFragment(
                    mediaUIDataModel
                )
            )
        }
        else{
            findNavController().navigate(
                MediaFragmentDirections.actionMediaFragmentToImageViewerFragment(
                    mediaUIDataModel
                )
            )
        }

    }
}