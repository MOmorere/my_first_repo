package com.example.moivesbox.Modlse.Home.Adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moivesbox.Modlse.Home.TopMovies_moviesapi
import com.example.moivesbox.Modlse.Home.topmovies
import com.example.moivesbox.databinding.TopMoviesItemBinding
import javax.inject.Inject

class TopMoviesAdaptor @Inject constructor() :
    RecyclerView.Adapter<TopMoviesAdaptor.MyViewHolder>() {
    //Binding
    lateinit var Binding: TopMoviesItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Binding = TopMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setdata(Differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return Differ.currentList.size
    }

    inner class MyViewHolder() : RecyclerView.ViewHolder(Binding.root) {
        fun setdata(Item: TopMovies_moviesapi.Data) {

            Binding.apply {
                imgTopMovies.load(Item.poster)
                voteAverage.text = Item.imdbRating.toString()
                txtName.text=Item.title
            }

        }
    }

    private val differcallback = object : DiffUtil.ItemCallback<TopMovies_moviesapi.Data>() {
        override fun areItemsTheSame(
            oldItem: TopMovies_moviesapi.Data,
            newItem: TopMovies_moviesapi.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(
            oldItem: TopMovies_moviesapi.Data,
            newItem: TopMovies_moviesapi.Data
        ): Boolean {
            return oldItem == newItem
        }

    }
    val Differ = AsyncListDiffer(this, differcallback)
}




