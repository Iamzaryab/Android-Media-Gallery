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
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.constants.Constants
import com.zaryabshakir.mediagallery.databinding.FragmentBucketsBinding
import com.zaryabshakir.mediagallery.presentation.adaptors.MediaBucketAdaptor
import com.zaryabshakir.mediagallery.presentation.events.BucketIntent
import com.zaryabshakir.mediagallery.presentation.events.BucketUIEvent
import com.zaryabshakir.mediagallery.presentation.viewmodels.BucketViewModel
import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BucketFragment : Fragment(R.layout.fragment_buckets) {
    private lateinit var binding: FragmentBucketsBinding
    private val viewModel: BucketViewModel by viewModels()
    private lateinit var bucketsAdaptor: MediaBucketAdaptor
    private var type: String = Constants.EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type= it.getString(Constants.MEDIA_TYPE)?:Constants.EMPTY_STRING
        }
    }

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
                viewModel.onEvent(BucketIntent.FetchBuckets(type))
                viewModel.state.collect { event ->
                    when (event) {
                        is BucketUIEvent.OnFetchBuckets -> {
                            binding.listBuckets.apply {
                                bucketsAdaptor = MediaBucketAdaptor(
                                    buckets = event.buckets,
                                    onBucketSelected = {onBucketSelected(it) })
                                adapter = bucketsAdaptor
                            }
                        }

                        is BucketUIEvent.Loading -> Unit
                    }
                }
            }
        }

    }

    private fun onBucketSelected(bucketUIDataModel: BucketUIDataModel){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMediaFragment(bucketUIDataModel))
    }

    companion object {
        fun newInstance(type: String): BucketFragment {
            val fragment = BucketFragment()
            val args = Bundle().apply {
                putString(Constants.MEDIA_TYPE, type)
            }
            fragment.arguments = args
            return fragment

        }
    }
}