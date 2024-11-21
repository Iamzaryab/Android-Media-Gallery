package com.zaryabshakir.mediagallery.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaryabshakir.mediagallery.databinding.LayoutItemMediaBinding
import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel
import com.zaryabshakir.mediagallery.utils.hide
import com.zaryabshakir.mediagallery.utils.loadThumbnail
import com.zaryabshakir.mediagallery.utils.show

class MediaBucketAdapter(
    private val buckets: List<BucketUIDataModel>,
    private val onBucketSelected: (BucketUIDataModel) -> Unit
) : RecyclerView.Adapter<MediaBucketAdapter.MediaBucketViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaBucketViewHolder {
        return MediaBucketViewHolder(
            LayoutItemMediaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediaBucketViewHolder, position: Int) {
        holder.bind(buckets[position])
    }

    override fun getItemCount(): Int = buckets.size

    inner class MediaBucketViewHolder(private val binding: LayoutItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bucket: BucketUIDataModel) {
            with(binding) {
                if (bucket.isVideo())
                    videoIcon.show()
                else
                    videoIcon.hide()
                loadThumbnail(ivThumbnail, bucket.getThumbnailUri())
                txtTitle.text = bucket.getName()
                ivThumbnail.setOnClickListener {
                    onBucketSelected.invoke(bucket)
                }
            }
        }
    }
}
