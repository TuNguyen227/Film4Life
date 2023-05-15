package com.nmt.film4life.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseFragment
import com.nmt.film4life.config.Config
import com.nmt.film4life.constant.Constant
import com.nmt.film4life.data.Categories
import com.nmt.film4life.data.Movie
import com.nmt.film4life.databinding.FragmentHomeBinding
import com.nmt.film4life.databinding.MovieItemDesignBinding
import com.nmt.film4life.listener.OnItemClickListener
import com.nmt.film4life.listener.PaginationListener
import com.nmt.film4life.utility.StartSnaper
import com.nmt.film4life.viewmodel.HomeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import org.koin.android.ext.android.inject
import java.util.*

class HomeFragment : BaseFragment(){
    private val homeViewModel: HomeViewModel by inject()
    private lateinit var binding: FragmentHomeBinding
    private  var categories = mutableListOf<Categories>()
    private var isLoading:Boolean=true
    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun init() {

        if(isLoading){
            setUpCategories()
            homeViewModel.getTrending()
            homeViewModel.getMovieByGenreId(Constant.ACTION,1)
            homeViewModel.getMovieByGenreId(Constant.ANIMATION,1)
            homeViewModel.getMovieByGenreId(Constant.ADVENTURE,1)
            homeViewModel.getMovieByGenreId(Constant.ANIMATION,1)
            homeViewModel.getMovieByGenreId(Constant.COMEDY,1)
            homeViewModel.getMovieByGenreId(Constant.CRIME,1)
            homeViewModel.getMovieByGenreId(Constant.DOCUMENTARY,1)
            homeViewModel.getMovieByGenreId(Constant.DRAMA,1)
            homeViewModel.getMovieByGenreId(Constant.HORROR,1)
            homeViewModel.getMovieByGenreId(Constant.ROMANCE,1)
            homeViewModel.getMovieByGenreId(Constant.SCIENCE,1)
            homeViewModel.getMovieByGenreId(Constant.TV,1)
        }
    }

    override fun setUpUI() {

    }


    override fun setListener() {

    }

    override fun setObserver() {
        with(homeViewModel) {
            isLoadingObserver.observe(this@HomeFragment){
                with(binding){
                    when{
                        isLoading && it==true -> {
                            layoutCover.visibility=View.VISIBLE
                            progressCircular.visibility=View.VISIBLE
                            isLoading=false
                        }
                        isLoading==false && it==false -> {
                            layoutCover.visibility=View.GONE
                            progressCircular.visibility=View.GONE
                        }
                    }
                }
            }
            trendingDataObserver.observe(this@HomeFragment) { item->
                categories.forEach {
                    when(it.title){
                        "Trending" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }

            }
            actionDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Action" -> {
                            it.adapter?.setData(item.results)

                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            animationDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Animation" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            adventureDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Adventure" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            comedyDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Comedy" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            crimeDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Crime" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            documentaryDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Documentary" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            dramaDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Drama" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            horrorDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Horror" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            scienceDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Science Fiction" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            romanceDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "Romance" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
            tvDataObserver.observe(this@HomeFragment){ item ->
                categories.forEach {
                    when(it.title){
                        "TV" -> {
                            it.adapter?.setData(item.results)
                            it.adapter?.isLoading=false
                        }
                    }
                }
            }
        }

    }

    override fun setAnimation() {

    }

    private fun setUpCategories()
    {
        with(binding){
            categories.add(Categories("Trending",Constant.TRENDING,rvTrendingItem,null))
            categories.add(Categories("Action",Constant.ACTION,rvActionItem,null))
            categories.add(Categories("Adventure",Constant.ADVENTURE,rvAdventureItem,null))
            categories.add(Categories("Animation",Constant.ANIMATION,rvAnimationItem,null))
            categories.add(Categories("Comedy",Constant.COMEDY,rvComedyItem,null))
            categories.add(Categories("Crime",Constant.CRIME,rvCrimeItem,null))
            categories.add(Categories("Documentary",Constant.DOCUMENTARY,rvDocumentaryItem,null))
            categories.add(Categories("Drama",Constant.DRAMA,rvDramaItem,null))
            categories.add(Categories("Horror",Constant.HORROR,rvHorrorItem,null))
            categories.add(Categories("Romance",Constant.ROMANCE,rvRomanceItem,null))
            categories.add(Categories("Science Fiction",Constant.SCIENCE,rvScienceItem,null))
            categories.add(Categories("TV",Constant.TV,rvTvItem,null))
            categories.forEach {
                val layout=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                val snapHelper= StartSnaper()
                snapHelper.attachToRecyclerView(it.rv)
                val adapter=MovieAdapter(mutableListOf(), object : OnItemClickListener {
                    override fun onItemClicking(item: Movie.Result, imgView: ImageView) {
                        val intent=Intent(context,DetailActivity::class.java)
                        intent.putExtra("data",item)
                        startActivity(intent)
                        //(requireActivity() as BaseActivity).goNext()
                    }

                    override fun onVideoClicking(youTubePlayer: YouTubePlayer) {
                    }
                })
                it.rv?.layoutManager=layout
                it.rv?.adapter=adapter
                it.adapter=adapter
                it.rv?.addOnScrollListener(object : PaginationListener(it.rv!!.layoutManager as LinearLayoutManager){
                    override fun loadMore() {
                        it.adapter!!.isLoading=true
                        it.adapter!!.currentPage++
                        homeViewModel.getMovieByGenreId(it.id ?:0,it.adapter!!.currentPage)
                    }

                    override fun isLastPage(): Boolean {
                        return it.adapter!!.isLastPage
                    }

                    override fun isLoading(): Boolean {
                        return it.adapter!!.isLoading
                    }

                })

            }
        }
    }

    override fun onPause() {
        super.onPause()
        isLoading=false
    }




    class MovieAdapter(list: MutableList<Movie.Result?>,var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private var data:MutableList<Movie.Result?> = list
        var isLastPage=false
        var isLoading=false
        var currentPage:Int=1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MovieViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.movie_item_design,parent,false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item= data[position]
            when{
                holder is MovieViewHolder && item is Movie.Result -> holder.bindData(item,onItemClickListener)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }
        inner class MovieViewHolder(val binding: MovieItemDesignBinding) : RecyclerView.ViewHolder(binding.root){
            @SuppressLint("CheckResult", "ResourceType")
            fun bindData(item : Movie.Result,listener: OnItemClickListener){

                Glide.with(itemView.context).load(Config.IMAGE_URL+item.posterPath).diskCacheStrategy(
                    DiskCacheStrategy.ALL).placeholder(Color.WHITE).into(binding.imgPoster)
                binding.imgPoster.setOnClickListener {
                    listener.onItemClicking(item,binding.imgPoster)
                }

            }

        }
        fun setData(list: List<Movie.Result?>?)
        {
            data.addAll(list?: listOf())
            notifyItemRangeInserted(data.size-list!!.size,list.size)
        }

    }
    enum class HomeViewType{
        CATEGORIES,
        MOVIES,
    }




}