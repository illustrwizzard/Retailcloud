package com.example.retailcloudproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retailcloudproject.Room.ImageItem

class ImageAdapter(private val isGridView: Boolean) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private val imageItems = mutableListOf<ImageItem>()
    private lateinit var onItemClickListener: (ImageItem) -> Unit

    fun setOnItemClickListener(listener: (ImageItem) -> Unit) {
        onItemClickListener = listener
    }

    fun setImageItems(items: List<ImageItem>?) {
        imageItems.clear()
        if (items != null) {
            imageItems.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutRes = if (isGridView) R.layout.item_image_grid else R.layout.item_image_list
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = imageItems[position]
        holder.bind(imageItem)
    }

    override fun getItemCount(): Int {
        return imageItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(imageItem: ImageItem) {
            // Bind imageItem to the UI elements in the itemView

            // Example: Display image using Glide (or your preferred image loading library)
            Glide.with(itemView)
                .load(imageItem.download_url)
                .into(imageView)

            itemView.setOnClickListener {
                onItemClickListener(imageItem)
            }
        }
    }
}


