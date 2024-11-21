package com.zaryabshakir.mediagallery.presentation.views

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.constants.MediaType
import com.zaryabshakir.mediagallery.databinding.FragmentHomeBinding
import com.zaryabshakir.mediagallery.presentation.adapters.BucketsPagerAdapter
import com.zaryabshakir.mediagallery.presentation.views.base.BaseFragment
import com.zaryabshakir.mediagallery.utils.checkMultiplePermissions
import com.zaryabshakir.mediagallery.utils.checkPermission
import com.zaryabshakir.mediagallery.utils.hide
import com.zaryabshakir.mediagallery.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val requestGalleryPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        setupViewPagerBasedOnPermissions()
    }
    override val layoutId: Int get() = R.layout.fragment_home


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initClickListeners()
        checkIfPermissionsPermitted()
    }

    private fun initClickListeners() {
        with(getBinding()) {
            lvPermissionNotGranted.btnAllow.setOnClickListener {
                showPermissionDialog()
            }
        }
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
            AlertDialog.Builder(it, R.style.CustomAlertDialogTheme)
                .setTitle(getString(R.string.permission_title))
                .setMessage(getString(R.string.permission_description))
                .setPositiveButton(getString(R.string.allow)) { _, _ -> requestMediaPermissions() }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> showPermissionRequiredView() }
                .setCancelable(false)
                .show()
        }

    }

    private fun showPermissionRequiredView() {
        with(getBinding()) {
            lvPermissionNotGranted.root.show()
            bucketsTab.hide()
            bucketPager.hide()
        }
    }

    private fun showBuckets() {
        with(getBinding()) {
            lvPermissionNotGranted.root.hide()
            bucketsTab.show()
            bucketPager.show()
        }
    }

    private fun initViews() {
        with(getBinding()) {
            lvPermissionNotGranted.root.hide()
            bucketsTab.show()
            bucketPager.show()
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
                    tabs.add(getString(R.string.videos))
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
            showPermissionRequiredView()
            return
        }
        showBuckets()
        val adapter = BucketsPagerAdapter(this, fragments)
        with(getBinding()) {
            bucketPager.adapter = adapter
            TabLayoutMediator(bucketsTab, bucketPager) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }
    }

}