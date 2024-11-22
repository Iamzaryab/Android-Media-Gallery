package com.zaryabshakir.mediagallery.presentation.views

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.constants.Constants
import com.zaryabshakir.mediagallery.databinding.FragmentBucketsBinding
import com.zaryabshakir.mediagallery.presentation.adapters.MediaBucketAdapter
import com.zaryabshakir.mediagallery.presentation.views.base.BaseFragment
import com.zaryabshakir.mediagallery.presentation.events.BucketIntent
import com.zaryabshakir.mediagallery.presentation.events.BucketUIEvent
import com.zaryabshakir.mediagallery.presentation.viewmodels.BucketViewModel
import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel
import com.zaryabshakir.mediagallery.utils.hide
import com.zaryabshakir.mediagallery.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BucketFragment : BaseFragment<FragmentBucketsBinding>() {
    private val viewModel: BucketViewModel by viewModels()
    private lateinit var bucketsAdaptor: MediaBucketAdapter
    private var type: String = Constants.EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(Constants.MEDIA_TYPE).orEmpty()
        }
    }

    override val layoutId: Int get() = R.layout.fragment_buckets

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
                            with(getBinding()) {
                                if (event.buckets.isEmpty()) {
                                    lvNoMedia.root.show()
                                } else {
                                    lvNoMedia.root.hide()
                                    listBuckets.apply {
                                        bucketsAdaptor = MediaBucketAdapter(
                                            buckets = event.buckets,
                                            onBucketSelected = { onBucketSelected(it) })
                                        adapter = bucketsAdaptor
                                    }
                                }
                            }

                        }

                        is BucketUIEvent.Loading -> Unit
                    }
                }
            }
        }

    }

    private fun onBucketSelected(bucketUIDataModel: BucketUIDataModel) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMediaFragment(
                bucketUIDataModel
            )
        )
    }

    companion object {
        fun newInstance(type: String): BucketFragment {
            return BucketFragment().apply {
                arguments= bundleOf(Constants.MEDIA_TYPE to type)
            }
        }
    }
}