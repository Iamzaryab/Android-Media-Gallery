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
import androidx.navigation.fragment.navArgs
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.databinding.FragmentBucketsBinding
import com.zaryabshakir.mediagallery.presentation.adaptors.MediaBucketAdaptor
import com.zaryabshakir.mediagallery.presentation.events.BucketIntent
import com.zaryabshakir.mediagallery.presentation.events.BucketUIEvent
import com.zaryabshakir.mediagallery.presentation.viewmodels.BucketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BucketFragment : Fragment(R.layout.fragment_buckets) {
    private lateinit var binding: FragmentBucketsBinding
    private val viewModel: BucketViewModel by viewModels()
    private lateinit var bucketsAdaptor: MediaBucketAdaptor
    private val args: BucketFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_buckets, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.onEvent(BucketIntent.FetchBuckets(args.mediaType))
                viewModel.state.collect { event ->
                    when (event) {
                        is BucketUIEvent.OnFetchBuckets -> {
                            binding.listBuckets.apply {
                                bucketsAdaptor = MediaBucketAdaptor(
                                    buckets = event.buckets,
                                    onBucketSelected = { })
                                adapter = bucketsAdaptor
                            }
                        }
                    }
                }
            }
        }

    }
}