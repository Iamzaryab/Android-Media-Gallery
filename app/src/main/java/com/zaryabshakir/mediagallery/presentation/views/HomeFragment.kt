package com.zaryabshakir.mediagallery.presentation.views

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.constants.MediaType
import com.zaryabshakir.mediagallery.databinding.FragmentHomeBinding
import com.zaryabshakir.mediagallery.presentation.adapters.BucketsPagerAdapter
import com.zaryabshakir.mediagallery.utils.checkMultiplePermissions
import com.zaryabshakir.mediagallery.utils.checkPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    private val requestGalleryPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        setupViewPagerBasedOnPermissions()
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
        checkIfPermissionsPermitted()
    }

    private fun checkIfPermissionsPermitted() {
        activity?.let {
            if (checkMultiplePermissions(it))
                setupViewPagerBasedOnPermissions()
            else
                showPermissionDialog()
        }
    }


    private fun requestMediaPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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


    private fun showPermissionDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Photo Access Required")
                .setMessage("This app needs access to your photos to display them in the gallery.")
                .setPositiveButton("Allow") { _, _ -> requestMediaPermissions() }
                .setNegativeButton("Cancel", null)
                .show()
        }

    }

    private fun setupViewPagerBasedOnPermissions() {
        val fragments = mutableListOf<Fragment>()
        val tabs = mutableListOf<String>()
        activity?.let { mActivity ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkPermission(mActivity, Manifest.permission.READ_MEDIA_IMAGES)) {
                    fragments.add(BucketFragment.newInstance(MediaType.IMAGE.name))
                    tabs.add(getString(R.string.images))
                }
                if (checkPermission(mActivity, Manifest.permission.READ_MEDIA_VIDEO)) {
                    fragments.add(BucketFragment.newInstance(MediaType.VIDEO.name))
                    tabs.add(getString(R.string.images))
                }
            } else {
                if (checkPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    fragments.add(BucketFragment.newInstance(MediaType.IMAGE.name))
                    fragments.add(BucketFragment.newInstance(MediaType.VIDEO.name))
                    tabs.add(getString(R.string.images))
                    tabs.add(getString(R.string.videos))
                }
            }
        }
        if (fragments.isEmpty()) {
            return
        }
        val adapter = BucketsPagerAdapter(this, fragments)

        with(binding) {
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }
    }

}