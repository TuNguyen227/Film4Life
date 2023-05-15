package com.nmt.film4life.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.firebase.auth.FirebaseAuth
import com.nmt.film4life.base.BaseViewModel
import com.nmt.film4life.data.DetailMovie
import com.nmt.film4life.data.Movie
import com.nmt.film4life.data.Trailers
import com.nmt.film4life.repository.Repository
import kotlinx.coroutines.*

class DetailViewModel(repository: Repository) : BaseViewModel(repository) {
    val detailMovieObserver=MutableLiveData<DetailMovie>()
    val moreLikeMovieObserver=MutableLiveData<Movie>()
    val trailersOberser=MutableLiveData<Trailers>()
    private lateinit var job:Job

    fun getDetailMovie(id:Int){
        job= CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            val response=repository.getDetailMovie(id)
            withContext(Dispatchers.Main){
                detailMovieObserver.postValue(response)
                isLoading.postValue(false)
            }
        }
    }
    fun getMoreLikeMovie(id: Int)
    {
        job= CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            val response=repository.getMoreLikeMovie(id)
            withContext(Dispatchers.Main){
                moreLikeMovieObserver.postValue(response)
            }
        }
    }
    fun getTrailers(id: Int)
    {
        job= CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            val response=repository.getTrailers(id)
            withContext(Dispatchers.Main){
                trailersOberser.postValue(response)
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
    }

}