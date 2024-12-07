package com.rejowan.ottmovies.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rejowan.ottmovies.R
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.databinding.ItemMovieHalfBinding
import com.rejowan.ottmovies.utils.interfaces.OnMovieListener


class MoviePaginationAdapter(
    private val movieList: MutableList<MovieItem> = mutableListOf(), private val onMovieListener: OnMovieListener
) : RecyclerView.Adapter<MoviePaginationAdapter.ImageSliderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val binding = ItemMovieHalfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val movieItem = movieList[position]

        Glide.with(holder.binding.root.context).load(movieItem.poster).placeholder(R.drawable.img_placeholder_landscape)
            .error(R.drawable.img_placeholder_landscape).centerCrop().into(holder.binding.ivBanner)

        holder.binding.tvTitle.text = movieItem.title

        holder.binding.rl.setOnClickListener {
            onMovieListener.onMovieClick(movieItem)
        }

        if (position == movieList.size - 2) {
            onMovieListener.onLastItemReach()
        }

    }

    fun addMovies(movies: List<MovieItem>) {
        val diffCallback = MovieDiffUtilCallback(movieList, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movieList.clear()
        movieList.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(movies: List<MovieItem>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movieList.size

    inner class ImageSliderViewHolder(val binding: ItemMovieHalfBinding) : RecyclerView.ViewHolder(binding.root)


    class MovieDiffUtilCallback(
        private val oldList: List<MovieItem>, private val newList: List<MovieItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].imdbId == newList[newItemPosition].imdbId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }



}
