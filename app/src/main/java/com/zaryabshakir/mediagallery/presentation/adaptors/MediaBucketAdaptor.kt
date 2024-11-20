package com.zaryabshakir.mediagallery.presentation.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaryabshakir.mediagallery.databinding.LayoutItemMediaBinding
import com.zaryabshakir.mediagallery.uimodel.BucketUIDataModel
import com.zaryabshakir.mediagallery.utils.loadThumbnail

class MediaBucketAdaptor(
    private val buckets: List<BucketUIDataModel>,
    val onBucketSelected: (BucketUIDataModel) -> Unit
) : RecyclerView.Adapter<MediaBucketAdaptor.BucketViewHolder>() {
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
        holder.bind(buckets[position])
    }

    override fun getItemCount(): Int = buckets.size

    inner class BucketViewHolder(private val binding: LayoutItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bucket: BucketUIDataModel) {
            with(binding){
                if (bucket.isVideo())
                    videoIcon.visibility=View.VISIBLE
                loadThumbnail(img, bucket.getThumbnailUri())
                txtTitle.visibility = View.VISIBLE
                txtTitle.text = bucket.getName()
                img.setOnClickListener {
                    onBucketSelected.invoke(bucket)
                }
            }
        }
    }
}
