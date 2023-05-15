package com.nmt.film4life.data


import com.google.gson.annotations.SerializedName

data class Trailers(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("results")
    var results: List<Result?>? = null
) {
    data class Result(
        @SerializedName("id")
        var id: String? = null,
        @SerializedName("iso_3166_1")
        var iso31661: String? = null,
        @SerializedName("iso_639_1")
        var iso6391: String? = null,
        @SerializedName("key")
        var key: String? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("official")
        var official: Boolean? = null,
        @SerializedName("published_at")
        var publishedAt: String? = null,
        @SerializedName("site")
        var site: String? = null,
        @SerializedName("size")
        var size: Int? = null,
        @SerializedName("type")
        var type: String? = null
    )
}