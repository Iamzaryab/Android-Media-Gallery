package com.zaryabshakir.mediagallery.presentation.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaryabshakir.mediagallery.databinding.LayoutItemMediaBinding
import com.zaryabshakir.mediagallery.uimodel.MediaUIDataModel
import com.zaryabshakir.mediagallery.utils.loadThumbnail

class MediaItemsAdaptor(
    private val media: List<MediaUIDataModel>,
    val onMediaSelected: (MediaUIDataModel) -> Unit
) : RecyclerView.Adapter<MediaItemsAdaptor.BucketViewHolder>() {
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
            with(binding){
                if (media.isVideo())
                    videoIcon.visibility=View.VISIBLE
                loadThumbnail(img, media.getUri())
                txtTitle.visibility = View.VISIBLE
                txtTitle.text = media.getDisplayName()
                img.setOnClickListener {
                    onMediaSelected.invoke(media)
                }
            }
        }
    }
}
