package com.nmt.film4life.listener

import android.widget.ImageView
import com.nmt.film4life.data.Movie
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

interface OnItemClickListener {
    fun onItemClicking(item: Movie.Result, imgView: ImageView)
    fun onVideoClicking(youTubePlayer: YouTubePlayer)
}