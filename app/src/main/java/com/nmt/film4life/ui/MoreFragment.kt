package com.nmt.film4life.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.nmt.film4life.data.Movie
import com.nmt.film4life.databinding.FragmentMoreBinding
import com.nmt.film4life.databinding.MovieItemDesignBinding
import com.nmt.film4life.listener.OnItemClickListener
import com.nmt.film4life.viewmodel.DetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import org.koin.android.ext.android.inject

class MoreFragment : BaseFragment(){
    private val viewModel : DetailViewModel by inject()
    private lateinit var binding: FragmentMoreBinding
    private val withScreen by lazy {
        activity?.windowManager?.defaultDisplay
    }
    private var isLoading:Boolean=true
    private var adapter: MovieAdapter?=null
    private val data by lazy {
        arguments?.getParcelable<Movie.Result>("data")
    }
    companion object {
        fun newInstance(data: Movie.Result): MoreFragment {
            val fragment = MoreFragment()
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
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_more,container,false)
        return binding.root
    }

    override fun init() {
        val layout=GridLayoutManager(context,3)
        adapter=MovieAdapter(mutableListOf(),withScreen!!.width/3-4*5,object : OnItemClickListener {
            override fun onItemClicking(item: Movie.Result, imgView: ImageView) {
                val intent=Intent(context,DetailActivity::class.java)
                intent.putExtra("data",item)
                startActivity(intent)
                requireActivity().finish()
            }

            override fun onVideoClicking(youTubePlayer: YouTubePlayer) {

            }

        })
        with(binding){
            rvMore.layoutManager=layout
            rvMore.adapter=adapter
        }
        viewModel.getMoreLikeMovie(data?.id!!)
    }

    override fun setUpUI() {

    }


    override fun setListener() {

    }

    override fun setObserver() {
        with(viewModel){
            moreLikeMovieObserver.observe(viewLifecycleOwner){
                adapter?.setData(it.results)
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




    class MovieAdapter(list: MutableList<Movie.Result?>,var with: Int, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
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
            notifyItemRangeInserted(data.size-list!!.size,list.size)
        }

    }





}