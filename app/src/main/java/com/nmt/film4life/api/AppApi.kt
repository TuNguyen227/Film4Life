package com.nmt.film4life.api


import com.nmt.film4life.data.Category
import com.nmt.film4life.data.DetailMovie
import com.nmt.film4life.data.Movie
import com.nmt.film4life.data.Trailers
import retrofit2.http.*

interface AppApi {
    @GET("genre/movie/list")
    suspend fun getCategories(@Query("api_key") api_key : String,@Query("language") language:String="en-US") : Category

    @GET("trending/movie/day")
    suspend fun getTrending(@Query("api_key") api_key: String) : Movie

    @GET("discover/movie")
    suspend fun getMovieByGenreId(@Query("api_key") api_key: String,@Query("with_genres") id:Int,@Query("page")page: Int) : Movie

    @GET("movie/{id}")
    suspend fun getDetailMovie(@Path("id")id: Int, @Query("api_key") api_key: String) : DetailMovie

    @GET("movie/{movie_id}/similar")
    suspend fun getMoreLikeMovie(@Path("movie_id")id: Int,@Query("api_key") api_key: String) : Movie

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailers(@Path("movie_id")id: Int,@Query("api_key") api_key: String) : Trailers

    @GET("search/multi")
    suspend fun searchingVideo(@Query("query") key: String,@Query("api_key") api_key: String) : Movie
}