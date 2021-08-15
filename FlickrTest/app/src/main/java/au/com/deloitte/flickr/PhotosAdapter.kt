package au.com.deloitte.flickr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import au.com.deloitte.flickr.databinding.FlickrRowBinding
import au.com.deloitte.flickr.network.model.Photo
import com.squareup.picasso.Picasso

class PhotosAdapter(val photos: MutableList<Photo> = mutableListOf()) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {

        return PhotosViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.flickr_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    inner class PhotosViewHolder(val binding: FlickrRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            Picasso.get().load(photo.url)
                .resize(IMAGE_SIDE_PX, IMAGE_SIDE_PX)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(binding.imageView)
        }

    }
}

const val IMAGE_SIDE_PX = 400
