package com.rejowan.ottmovies.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.rejowan.ottmovies.R
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.databinding.ItemMovieBannerBinding
import com.rejowan.ottmovies.utils.interfaces.OnMovieListener


class MoviePosterAdapter(
    private val movieList: ArrayList<MovieItem>, private val viewPager2: ViewPager2,
    private val onMovieListener: OnMovieListener
) : RecyclerView.Adapter<MoviePosterAdapter.ImageSliderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val binding = ItemMovieBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val movieItem = movieList[position]

        Glide.with(holder.binding.root.context).load(movieItem.poster).placeholder(R.drawable.img_placeholder_landscape)
            .error(R.drawable.img_placeholder_landscape).centerCrop().into(holder.binding.ivBanner)

        holder.binding.tvTitle.text = "${movieItem.title} (${movieItem.year})"

        holder.binding.rl.setOnClickListener {
            onMovieListener.onMovieClick(movieItem)
        }

        if (position == movieList.size - 2) {
            viewPager2.post {
                movieList.addAll(movieList)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = movieList.size

    inner class ImageSliderViewHolder(val binding: ItemMovieBannerBinding) : RecyclerView.ViewHolder(binding.root)

}
