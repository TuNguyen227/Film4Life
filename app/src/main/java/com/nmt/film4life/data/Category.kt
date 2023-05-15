package com.nmt.film4life.data


import com.google.gson.annotations.SerializedName


data class Category(
    @SerializedName("genres")
    var genres: List<Genre?>? = null
) {
    data class Genre(
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("name")
        var name: String? = null,

    )
}
