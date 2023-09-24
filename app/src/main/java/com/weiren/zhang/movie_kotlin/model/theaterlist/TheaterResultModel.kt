package com.weiren.zhang.movie_kotlin.model.theaterlist

import com.weiren.zhang.movie_kotlin.model.TimeModel
import com.weiren.zhang.movie_kotlin.model.TypeModel

data class TheaterResultModel(
    val id: String,
    val release_foto: String,
    val theaterlist_name: String,
    val en: String,
    var icon: String,
    val tapbox: List<TypeModel>,
    val theater_time: List<TimeModel>,
)

