package com.zaryabshakir.mediagallery.presentation.views

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.constants.MediaType
import com.zaryabshakir.mediagallery.databinding.FragmentHomeBinding
import com.zaryabshakir.mediagallery.utils.checkAndRequestGalleryPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val requestGalleryPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            val isGranted = it.value
            if (isGranted) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            btnImages.setOnClickListener {
                activity?.let {mActivity->
                    if (checkAndRequestGalleryPermission(mActivity)) {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBucketFragment(MediaType.IMAGE.name))
                    } else {
                        startGalleryPermissionRequest()
                    }
                }

            }
            btnVideos.setOnClickListener {
                activity?.let {mActivity->
                    if (checkAndRequestGalleryPermission(mActivity)) {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBucketFragment(MediaType.VIDEO.name))
                    } else {
                        startGalleryPermissionRequest()
                    }
                }

            }

        }
    }

    private fun startGalleryPermissionRequest() {
        if (Build.VERSION.SDK_INT >= 33) {
            requestGalleryPermission.launch(
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            )

        } else {
            requestGalleryPermission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }
}