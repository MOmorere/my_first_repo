package com.example.moivesbox.Ui.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.moivesbox.Modlse.Home.Adaptor.TopMoviesAdaptor
import com.example.moivesbox.Modlse.Home.genres_list
import com.example.moivesbox.R
import com.example.moivesbox.Ui.Home.Adaptor.Genres_Adaptor
import com.example.moivesbox.databinding.FragmentHomeBinding
import com.example.moivesbox.viewmodl.Genres_ViewModle
import com.example.moivesbox.viewmodl.TopMovies_ViewModle
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout
import javax.inject.Inject
@AndroidEntryPoint
class Home : Fragment() {
    //Binding
    lateinit var Binding:FragmentHomeBinding
    //ViewModel
    val viewModle :  TopMovies_ViewModle by viewModels()
    val viewmodle_genres : Genres_ViewModle by viewModels()
    //adaptor
    @Inject
    lateinit var Adaptor:TopMoviesAdaptor
    @Inject
    lateinit var Genres_Adaptor:Genres_Adaptor
    //Other
    private val Pager_Helper: PagerSnapHelper by lazy {
        PagerSnapHelper()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //api_call_movies_list
        viewModle.get_Movies()
        //api_cal_Genres_List
        viewmodle_genres.get_genres_list()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        return Binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Binding.apply {
            //movies_view_model
            viewModle.Top_movies_responses.observe(viewLifecycleOwner){
                Adaptor.Differ.submitList(it.data)
                moviesRecyclerHome.apply {
                    adapter=Adaptor
                    Pager_Helper.attachToRecyclerView(moviesRecyclerHome)
                    recylerview(moviesRecyclerHome)
                }
            }
            viewModle.Top_movie_loading.observe(viewLifecycleOwner) { IsShown ->
                if (IsShown) {
                    progressBarTopMovies.visibility = android.view.View.VISIBLE
                    moviesRecyclerHome.visibility = android.view.View.INVISIBLE
                } else {
                    progressBarTopMovies.visibility = android.view.View.INVISIBLE
                    moviesRecyclerHome.visibility = android.view.View.VISIBLE
                }
            }
            viewmodle_genres.get_genres.observe(viewLifecycleOwner){
                Genres_Adaptor.differ.submitList(it)
        }
        GenresList.apply {
            adapter=Genres_Adaptor
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }

        }
    }
    fun recylerview(recylerview: RecyclerView){
        val linearLayoutManager = context?.let { ZoomRecyclerLayout(it) }
        linearLayoutManager!!.orientation=LinearLayoutManager.HORIZONTAL
        Binding.apply {
            recylerview.layoutManager=linearLayoutManager
        }
    }
}