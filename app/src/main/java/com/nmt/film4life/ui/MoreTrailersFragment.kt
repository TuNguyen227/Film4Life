package com.nmt.film4life.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.view.menu.MenuView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseFragment
import com.nmt.film4life.config.Config
import com.nmt.film4life.data.Categories
import com.nmt.film4life.data.Movie
import com.nmt.film4life.data.Trailers
import com.nmt.film4life.databinding.FragmentMoreTrailersBinding
import com.nmt.film4life.databinding.MovieItemDesignBinding
import com.nmt.film4life.databinding.TrailerItemBinding
import com.nmt.film4life.listener.OnItemClickListener
import com.nmt.film4life.viewmodel.DetailViewModel
import com.nmt.film4life.viewmodel.HomeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import org.koin.android.ext.android.inject
import java.util.*

class MoreTrailersFragment : BaseFragment(){
    private val viewModel:DetailViewModel by inject()
    private lateinit var binding: FragmentMoreTrailersBinding
    private var isLoading:Boolean=true
    private lateinit var adapter:TrailersAdapter
    private val data by lazy {
        arguments?.getParcelable<Movie.Result>("data")
    }
    companion object {
        fun newInstance(data: Movie.Result): MoreTrailersFragment {
            val fragment = MoreTrailersFragment()
            val args = Bundle()
            data?.let { args.putParcelable("data",data) }
            fragment.arguments = args

            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_more_trailers,container,false)
        return binding.root
    }

    override fun init() {
        viewModel.getTrailers(data?.id!!)
    }

    override fun setUpUI() {
        with(binding){
            adapter=TrailersAdapter(lifecycle, mutableListOf(),object :OnItemClickListener{
                override fun onItemClicking(item: Movie.Result, imgView: ImageView) {

                }

                override fun onVideoClicking(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.play()
                }
            })
            val layout=LinearLayoutManager(context)
            rvTrailers.layoutManager=layout
            rvTrailers.adapter=adapter
        }
    }


    override fun setListener() {

    }

    override fun setObserver() {
        with(viewModel)
        {
            trailersOberser.observe(this@MoreTrailersFragment){
                adapter.setData(it.results)
            }
        }
    }

    override fun setAnimation() {

    }

    private fun setUpCategories()
    {

    }

    override fun onPause() {
        super.onPause()
        isLoading=false
    }




    class TrailersAdapter(var lifecycle: Lifecycle,list: MutableList<Trailers.Result>,var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private var data:MutableList<Trailers.Result> = list
        var isLastPage=false
        var isLoading=false
        var currentPage:Int=1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return TrailersViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.trailer_item,parent,false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item= data[position]
            when{
                holder is TrailersViewHolder && item.type!="Trailer" -> holder.bindData(item,onItemClickListener)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }
        inner class TrailersViewHolder(val binding: TrailerItemBinding) : RecyclerView.ViewHolder(binding.root){
            @SuppressLint("CheckResult", "ResourceType")
            fun bindData(item : Trailers.Result,listener: OnItemClickListener){
                lifecycle.addObserver(binding.itemYoutube)
                binding.itemYoutube.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        item.key?.let { youTubePlayer.cueVideo(it,0f) }
                    }
                })

            }

        }
        fun setData(list: List<Trailers.Result?>?)
        {
            data.addAll((list?: listOf()) as Collection<Trailers.Result>)
            notifyItemRangeInserted(data.size-list!!.size,list.size)
        }

    }
    enum class HomeViewType{
        CATEGORIES,
        MOVIES,
    }




}