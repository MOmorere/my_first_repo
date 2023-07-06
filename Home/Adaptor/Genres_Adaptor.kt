package com.example.moivesbox.Ui.Home.Adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moivesbox.Modlse.Home.Genres_Modle
import com.example.moivesbox.Modlse.Home.TopMovies_moviesapi
import com.example.moivesbox.databinding.InformationLayoutBinding
import javax.inject.Inject

class Genres_Adaptor @Inject constructor():RecyclerView.Adapter<Genres_Adaptor.MyViewHolder>() {
    //Binding
    lateinit var Binding:InformationLayoutBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Binding=InformationLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
   holder.setdata(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }
    inner class MyViewHolder():RecyclerView.ViewHolder(Binding.root){

        fun setdata(Item:Genres_Modle.Genres_ModleItem){
            Binding.apply {
                genres.text=Item.name
            }
        }
    }
private val DifferCallBack=object :DiffUtil.ItemCallback<Genres_Modle.Genres_ModleItem>(){

    override fun areItemsTheSame(oldItem: Genres_Modle.Genres_ModleItem, newItem: Genres_Modle.Genres_ModleItem): Boolean {
        return oldItem.id==newItem.id
    }


    override fun areContentsTheSame(oldItem: Genres_Modle.Genres_ModleItem, newItem:Genres_Modle.Genres_ModleItem): Boolean {
       return oldItem==newItem
    }
}
    val differ=AsyncListDiffer(this,DifferCallBack)
}