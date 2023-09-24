package com.weiren.zhang.movie_kotlin.model.movieinfomain

import com.weiren.zhang.movie_kotlin.model.TimeModel
import com.weiren.zhang.movie_kotlin.model.TypeModel

data class MovieTimeTabItemModel(
    val id: String,
    val area: String,
    var data: List<MovieTimeResultModel>
)

data class MovieTimeResultModel(
    val id: String,
    val theater: String,
    val tel: String,
    val types: List<TypesModel>
)

data class TypesModel(
    val types: List<TypeModel>,
    val times: List<TimeModel>
)

data class GetScheduleByMovieModel(
    val view: String,
)

