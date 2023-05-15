package com.nmt.film4life.viewmodel

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.firebase.auth.FirebaseAuth
import com.nmt.film4life.base.BaseViewModel
import com.nmt.film4life.constant.Constant
import com.nmt.film4life.repository.Repository
import kotlinx.coroutines.*

class HomeViewModel(repository: Repository) : BaseViewModel(repository) {
    private lateinit var job:Job
    fun getAvatarUri(): Uri? {
        var photoURL: Uri?=null
        if (isAccountProvidedbyGoogle())
        {
            photoURL=repository.getCurrentUser()?.photoUrl
            return photoURL
        }
//        if (isAccountProvidedbyFacebook())
//        {
//            photoURL= Uri.parse("${repository.getCurrentUser()?.photoUrl}/picture?type=normal&access_token=${AccessToken.getCurrentAccessToken()?.token}")
//            return photoURL
//        }
        return photoURL
    }
    private fun isAccountProvidedbyGoogle():Boolean
    {
        if (repository.getCurrentUser()!=null)
        {
            for (profile in repository.getCurrentUser()!!.providerData)
            {
                if (profile.providerId=="google.com")
                    return true
            }
        }

        return false
    }

    private fun isAccountProvidedbyFacebook():Boolean
    {
        if (repository.getCurrentUser()!=null)
        {
            for (profile in repository.getCurrentUser()!!.providerData)
            {
                if (profile.providerId=="facebook.com")
                    return true
            }
        }

        return false
    }
    fun searchingVideo(key: String){
        job=CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            val response=repository.searchingVideo(key)
            withContext(Dispatchers.Main){
                movieObserver.postValue(response)
                isLoading.postValue(false)
            }
        }
    }
    fun getMovieByGenreId(id:Int,page: Int){
        job= CoroutineScope(Dispatchers.IO).launch {
            isLoading.postValue(true)
            val response=repository.getMovieByGenreId(id,page)

            withContext(Dispatchers.Main){
                when(id){
                    Constant.ACTION -> {
                        actionDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.ADVENTURE -> {
                        adventureDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.ANIMATION -> {
                        animationDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.COMEDY -> {
                        comedyDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.CRIME -> {
                        crimeDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.DOCUMENTARY -> {
                        documentaryDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.DRAMA -> {
                        dramaDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.HORROR -> {
                        horrorDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.ROMANCE -> {
                        romanceDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.SCIENCE -> {
                        scienceDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    Constant.TV -> {
                        tvDataObserver.postValue(response)
                        isLoading.postValue(false)
                    }
                    else -> {}
                }

            }
        }
    }

    fun getTrending()
    {
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=repository.getTrending()
            withContext(Dispatchers.Main){
                trendingDataObserver.postValue(response)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

}