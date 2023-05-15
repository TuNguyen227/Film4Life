package com.nmt.film4life.repository



import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nmt.film4life.api.AppApi
import com.nmt.film4life.config.Config
import com.nmt.film4life.data.Category
import com.nmt.film4life.data.DetailMovie
import com.nmt.film4life.data.Movie
import com.nmt.film4life.data.Trailers


class Repository(private val appApi: AppApi) {
    private var firebaseAuth:FirebaseAuth
    init {
        firebaseAuth=FirebaseAuth.getInstance()
    }

    fun getFirebaseAuth(): FirebaseAuth {
        return firebaseAuth
    }
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun getTrending() : Movie {
        return appApi.getTrending(getApiKey())
    }

    suspend fun getMovieByGenreId(id:Int,page: Int) : Movie{
        return appApi.getMovieByGenreId(getApiKey(),id,page)
    }

    suspend fun getDetailMovie(id: Int) : DetailMovie {
        return appApi.getDetailMovie(id,getApiKey())
    }
    suspend fun getMoreLikeMovie(id: Int) : Movie
    {
        return appApi.getMoreLikeMovie(id,getApiKey())
    }

    suspend fun getTrailers(id: Int) : Trailers
    {
        return appApi.getTrailers(id,getApiKey())
    }

    suspend fun searchingVideo(key:String) : Movie
    {
        return appApi.searchingVideo(key,getApiKey())
    }

    private fun getApiKey() :String{
        return Config.API_KEY
    }

}
interface ResponseListener{
    //fun onReceivedResponse(list: List<Restaurants?>)
    fun onError(e : String)
    fun onSuccess(task:Boolean)
}