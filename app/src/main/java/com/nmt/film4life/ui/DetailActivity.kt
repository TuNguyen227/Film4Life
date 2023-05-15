package com.nmt.film4life.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nmt.film4life.R
import com.nmt.film4life.base.BaseActivity
import com.nmt.film4life.config.Config
import com.nmt.film4life.data.CategoryHelper
import com.nmt.film4life.data.Movie
import com.nmt.film4life.databinding.ActivityDetailBinding
import com.nmt.film4life.utility.AppHelper
import com.nmt.film4life.viewmodel.DetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.android.ext.android.inject


class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel : DetailViewModel by inject()
    private var isLoading=true
    private val data by lazy {
        intent.getParcelableExtra<Movie.Result>("data")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_detail)
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        viewModel.getTrailers(data?.id!!)
        viewModel.getDetailMovie(data?.id!!)
    }

    override fun setUpUI() {
        with(binding)
        {
//            Glide.with(this@DetailActivity).load(Config.IMAGE_BACKDROP_URL+data?.backdropPath).dontTransform().diskCacheStrategy(
//                DiskCacheStrategy.ALL).into(imgDetailMov)

            data?.genreIds?.forEach {
                it?.let { id ->
                    when{
                        editTxtGenres.text.isEmpty() && CategoryHelper.getNameById(it)!=null ->
                            editTxtGenres.text= CategoryHelper.getNameById(it)
                        editTxtGenres.text.isNotEmpty() && CategoryHelper.getNameById(it)!=null ->
                            editTxtGenres.text=editTxtGenres.text.toString()+" • "+CategoryHelper.getNameById(it)
                        else ->{}
                    }

                }

            }
            lifecycle.addObserver(binding.youtubeView)


        }


    }

    override fun setListener() {
        with(binding){
            tabMore.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewPaperMore.currentItem=tab!!.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
            buttonBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun setObserver() {
        with(viewModel){
            isLoadingObserver.observe(this@DetailActivity){
                when{
                    isLoading && it==true -> {
                        binding.layoutCover.visibility= View.VISIBLE
                        binding.progressCircular.visibility= View.VISIBLE
                        isLoading=false
                    }
                    isLoading==false && it==false -> {
                        binding.layoutCover.visibility= View.GONE
                        binding.progressCircular.visibility= View.GONE
                    }
                }
            }
            detailMovieObserver.observe(this@DetailActivity){
                with(binding){

                    editTxtMvName.text=it.title
                    editTxtImdbScore.text=it.voteAverage.toString()
                    editTxtYear.text=it.releaseDate
                    editTxtMvLength.text= AppHelper.convertToHour(it.runtime!!)
                    editTxtOverview.text=it.overview
                    viewPaperMore.adapter=ViewPaperAdapter(data!!)
                    viewPaperMore.isUserInputEnabled=false
                    viewPaperMore.offscreenPageLimit=2
                    TabLayoutMediator(tabMore,viewPaperMore){tab, pos ->
                        when(pos){
                            0 -> tab.text="Similar"
                            1 -> tab.text="More Trailers"
                            else -> {}
                        }
                    }.attach()
                }

            }
            trailersOberser.observe(this@DetailActivity){
                binding.youtubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val video=it.results?.firstOrNull { (it?.type == "Teaser" || it?.type == "Trailer") && it.key != null }
                        video?.let {
                            youTubePlayer.loadVideo(it.key!!,0f)
                            youTubePlayer.play()
                        }

                    }
                })
            }
        }
    }

    override fun setAnimation() {

    }

    inner class ViewPaperAdapter(var data: Movie.Result) : FragmentStateAdapter(this){
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> MoreFragment.newInstance(data)
                1 -> MoreTrailersFragment.newInstance(data)
                else -> MoreFragment.newInstance(data)
            }
        }

    }


}