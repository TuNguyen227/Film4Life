package com.nmt.film4life.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseActivity
import com.nmt.film4life.base.BaseFragment
import com.nmt.film4life.config.Config
import com.nmt.film4life.data.Movie
import com.nmt.film4life.databinding.FragmentDownloadBinding
import com.nmt.film4life.databinding.FragmentSearchBinding
import com.nmt.film4life.databinding.MovieItemDesignBinding
import com.nmt.film4life.listener.OnItemClickListener
import com.nmt.film4life.utility.AppHelper
import com.nmt.film4life.utility.StartSnaper
import com.nmt.film4life.viewmodel.HomeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel:HomeViewModel by inject()
    private lateinit var movieAdapter:MovieAdapter
    companion object {
        fun newInstance(): SearchFragment {
            val fragment = SearchFragment()
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
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false)
        return binding.root
    }
    override fun init() {

    }

    override fun setUpUI() {
        val layout=GridLayoutManager(context,3)
        movieAdapter=MovieAdapter(mutableListOf(),AppHelper.getScreenWidthAndHeight(activity as BaseActivity)!!.width/3-10*4,object : OnItemClickListener{
            override fun onItemClicking(item: Movie.Result, imgView: ImageView) {

            }

            override fun onVideoClicking(youTubePlayer: YouTubePlayer) {

            }
        })
        with(binding){
            rvSearch.apply {
                isNestedScrollingEnabled=true
                layoutManager=layout
                adapter=movieAdapter
            }
        }
    }

    override fun setListener() {
        with(binding){
            search.setOnQueryTextListener(object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { viewModel.searchingVideo(newText) }
                    return true
                }

            })
        }
    }

    override fun setObserver() {
        with(viewModel){
            movieObserver.observe(this@SearchFragment){
                it.results?.let { list ->
                    if (list.isEmpty())
                    {
                        binding.message.apply {
                            text="No result"
                            visibility=View.VISIBLE
                        }
                        binding.rvSearch.visibility=View.GONE

                    }
                    else
                    {
                        binding.message.apply {
                            visibility=View.GONE
                        }
                        binding.rvSearch.visibility=View.VISIBLE
                        movieAdapter.refreshData()
                        movieAdapter.setData(it.results?.filter { data -> data?.posterPath!=null } ?: mutableListOf())
                    }

                }
            }
        }
    }

    override fun setAnimation() {

    }

    class MovieAdapter(list: MutableList<Movie.Result?>, var with: Int, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
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
            fun bindData(item : Movie.Result, listener: OnItemClickListener){
                binding.imgPoster.layoutParams.width=with
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
            notifyDataSetChanged()
        }

        fun refreshData()
        {
            data.clear()
            isLastPage=false
            currentPage=1
            notifyDataSetChanged()
        }

    }
}