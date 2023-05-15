package com.nmt.film4life.data

import com.google.gson.annotations.SerializedName
import com.nmt.film4life.constant.Constant

class CategoryHelper() {
    companion object{
        fun getCategory() : List<Genre>
        {
            return listOf(Genre("Trending", Constant.TRENDING),
                    Genre("Action", Constant.ACTION),
                    Genre("Adventure", Constant.ADVENTURE),
                    Genre("Animation", Constant.ANIMATION),
                    Genre("Comedy", Constant.COMEDY),
                    Genre("Crime", Constant.CRIME),
                    Genre("Documentary", Constant.DOCUMENTARY),
                    Genre("Drama", Constant.DRAMA),
                    Genre("Horror", Constant.HORROR),
                    Genre("Romance", Constant.ROMANCE),
                    Genre("Science Fiction", Constant.SCIENCE),
                    Genre("TV", Constant.TV)
            )
        }
        fun getNameById(id: Int) : String?{
            return getCategory().find {
                it.id==id
            }?.name
        }
    }
    data class Genre(
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("id")
        var id: Int? = null,
        )
}