package com.nmt.film4life.base

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nmt.film4life.data.Category
import com.nmt.film4life.data.Movie
import com.nmt.film4life.repository.Repository

abstract class BaseViewModel(val repository: Repository): ViewModel(){
    protected var errorData= MutableLiveData<String>()
    var movieObserver = MutableLiveData<Movie>()
    var trendingDataObserver=MutableLiveData<Movie>()
    var adventureDataObserver=MutableLiveData<Movie>()
    var actionDataObserver=MutableLiveData<Movie>()
    var animationDataObserver=MutableLiveData<Movie>()
    var comedyDataObserver=MutableLiveData<Movie>()
    var crimeDataObserver=MutableLiveData<Movie>()
    var documentaryDataObserver=MutableLiveData<Movie>()
    var dramaDataObserver=MutableLiveData<Movie>()
    var horrorDataObserver=MutableLiveData<Movie>()
    var romanceDataObserver=MutableLiveData<Movie>()
    var scienceDataObserver=MutableLiveData<Movie>()
    var tvDataObserver=MutableLiveData<Movie>()


    val errorLiveData: LiveData<String>
        get() = errorData

    protected var isLoading= MutableLiveData<Boolean>()
    val isLoadingObserver: LiveData<Boolean>
        get() = isLoading

//    protected fun requestCurrentUser()=repository.getCurrentUser()

//    protected fun requestAvatarUri(): Uri? {
//        var photoURL: Uri?=null
//        if (isAccountProvidedbyGoogle() == true)
//        {
//            photoURL=requestCurrentUser()?.photoUrl
//            return photoURL
//        }
////        if (isAccountProvidedbyFacebook()==true)
////        {
////            photoURL= Uri.parse("${requestCurrentUser()?.photoUrl}/picture?type=normal&access_token=${AccessToken.getCurrentAccessToken()?.token}")
////            return photoURL
////        }
//        return photoURL
//    }
//
//    protected fun requestCurrentUserName()=requestCurrentUser()?.displayName
//
//    protected fun isAccountProvidedbyGoogle():Boolean
//    {
//        if (requestCurrentUser()!=null)
//        {
//            for (profile in requestCurrentUser()!!.providerData)
//            {
//                if (profile.providerId=="google.com")
//                    return true
//            }
//        }
//
//        return false
//    }
//
//    protected fun isAccountProvidedbyFacebook():Boolean
//    {
//        if (requestCurrentUser()!=null)
//        {
//            for (profile in requestCurrentUser()!!.providerData)
//            {
//                if (profile.providerId=="facebook.com")
//                    return true
//            }
//        }
//
//        return false
//    }

}