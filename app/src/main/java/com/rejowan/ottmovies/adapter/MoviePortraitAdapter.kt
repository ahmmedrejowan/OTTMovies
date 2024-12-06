package com.rejowan.ottmovies.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rejowan.ottmovies.R
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.databinding.ItemMoviePortraitBinding


class MoviePortraitAdapter(
    private val movieList: List<MovieItem>
) : RecyclerView.Adapter<MoviePortraitAdapter.ImageSliderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val binding = ItemMoviePortraitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val movieItem = movieList[position]

        Glide.with(holder.binding.root.context).load(movieItem.poster).placeholder(R.drawable.img_placeholder_landscape)
            .error(R.drawable.img_placeholder_landscape).centerCrop().into(holder.binding.ivBanner)

        holder.binding.tvTitle.text = movieItem.title

    }

    override fun getItemCount(): Int = movieList.size

    inner class ImageSliderViewHolder(val binding: ItemMoviePortraitBinding) : RecyclerView.ViewHolder(binding.root)

}
