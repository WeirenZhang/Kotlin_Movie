package com.weiren.zhang.movie_kotlin.model.movieinfomain

import com.weiren.zhang.movie_kotlin.model.TypesModel

data class MovieDateTabItemModel(
    val date: String,
    val list: List<MovieTimeTabItemModel>
)

data class MovieTimeTabItemModel(
    val area: String,
    val `data`: List<MovieTimeResultModel>
)

data class MovieTimeResultModel(
    val id: String,
    val theater: String,
    val types: List<TypesModel>
)

data class GetScheduleByMovieModel(
    val view: String,
)

