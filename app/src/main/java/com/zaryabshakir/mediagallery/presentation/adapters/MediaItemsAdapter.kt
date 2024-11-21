package com.zaryabshakir.mediagallery.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaryabshakir.mediagallery.databinding.LayoutItemMediaBinding
import com.zaryabshakir.mediagallery.uimodel.MediaUIDataModel
import com.zaryabshakir.mediagallery.utils.hide
import com.zaryabshakir.mediagallery.utils.loadThumbnail
import com.zaryabshakir.mediagallery.utils.show

class MediaItemsAdapter(
    private val media: List<MediaUIDataModel>,
    private val onMediaSelected: (MediaUIDataModel) -> Unit
) : RecyclerView.Adapter<MediaItemsAdapter.BucketViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BucketViewHolder {
        return BucketViewHolder(
            LayoutItemMediaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BucketViewHolder, position: Int) {
        holder.bind(media[position])
    }

    override fun getItemCount(): Int = media.size

    inner class BucketViewHolder(private val binding: LayoutItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(media: MediaUIDataModel) {
            with(binding) {
                if (media.isVideo())
                    videoIcon.show()
                else
                    videoIcon.hide()
                loadThumbnail(ivThumbnail, media.getUri())
                txtTitle.text = media.getDisplayName()
                ivThumbnail.setOnClickListener {
                    onMediaSelected.invoke(media)
                }
            }
        }
    }
}
