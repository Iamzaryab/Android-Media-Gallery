package com.zaryabshakir.mediagallery.presentation.views

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.zaryabshakir.mediagallery.R
import com.zaryabshakir.mediagallery.constants.Constants
import com.zaryabshakir.mediagallery.databinding.FragmentImageViewerBinding
import com.zaryabshakir.mediagallery.presentation.views.base.BaseFragment
import com.zaryabshakir.mediagallery.utils.loadThumbnail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerFragment : BaseFragment<FragmentImageViewerBinding>() {
    private val args: ImageViewerFragmentArgs by navArgs()
    override val layoutId: Int
        get() = R.layout.fragment_image_viewer


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(getBinding()) {
            loadThumbnail(imageView, args.media.getUri(),Constants.PHOTO_SIZE)
        }

    }


}