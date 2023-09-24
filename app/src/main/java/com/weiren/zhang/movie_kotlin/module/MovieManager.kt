package com.weiren.zhang.movie_kotlin.module

import android.content.Context
import android.widget.Toast
import com.google.gson.reflect.TypeToken
import com.weiren.zhang.lib_common.ext.MMKVExt
import com.weiren.zhang.lib_common.ext.fromJson
import com.weiren.zhang.lib_common.ext.toJson
import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel

object MovieManager {

    private const val Movie_RECORD = "movie_record"
    private var movieJson by MMKVExt(Movie_RECORD, "")

    fun addMovieRecord(context: Context, data: MovieListModel) {
        movieJson = if (movieJson.isNullOrEmpty()) {
            val videoList = mutableListOf<MovieListModel>()
            videoList.add(data)
            toJson(videoList)
        } else {
            val videoList: MutableList<MovieListModel> =
                fromJson(movieJson!!, object : TypeToken<MutableList<MovieListModel>>() {}.type)

            if (!videoList.any {
                    it.movie_id == data.movie_id
                }) {
                videoList.add(data)
            } else {
                Toast.makeText(context, data.release_movie_name + " 已加入我的最愛", Toast.LENGTH_LONG)
                    .show()
            }
            toJson(videoList)
        }
    }

    fun removeMovieRecord(data: MovieListModel) {
        val videoList: MutableList<MovieListModel> =
            fromJson(movieJson!!, object : TypeToken<MutableList<MovieListModel>>() {}.type)
        videoList.remove(data)
        movieJson = toJson(videoList)
    }

    fun getMovieList(): MutableList<MovieListModel> {
        return if (movieJson.isNullOrEmpty()) {
            mutableListOf()
        } else {
            fromJson(movieJson!!, object : TypeToken<MutableList<MovieListModel>>() {}.type)
        }
    }

}