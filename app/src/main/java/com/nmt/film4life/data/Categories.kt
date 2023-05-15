package com.nmt.film4life.data

import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import com.nmt.film4life.ui.HomeFragment

data class Categories(
    var title: String?,
    var id : Int?,
    var rv: RecyclerView?,
    var adapter: HomeFragment.MovieAdapter?,

    )

data class Movies(
    @SerializedName("adult")
    var adult: Boolean? = null,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("first_air_date")
    var firstAirDate: String? = null,
    @SerializedName("genre_ids")
    var genreIds: List<Int?>? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("media_type")
    var mediaType: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("origin_country")
    var originCountry: List<String?>? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_name")
    var originalName: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("popularity")
    var popularity: Double? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("video")
    var video: Boolean? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
)
