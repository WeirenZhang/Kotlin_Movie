package com.weiren.zhang.movie_kotlin.api

import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListApi {
    //@GET("macros/s/AKfycbwwB2Ke85PFeQqt2P9BRZFOxWif6JI4_ImblPyfFlP-VTJLkJJ6sZkCMD4tPhF_g8yT/exec")
    //suspend fun getMovieList(@Query("page") page: String): Response<ResponseBody>

    @GET("macros/s/AKfycbwwB2Ke85PFeQqt2P9BRZFOxWif6JI4_ImblPyfFlP-VTJLkJJ6sZkCMD4tPhF_g8yT/exec")
    suspend fun getMovieList(@Query("page") page: String, @Query("type") type: String, @Query("tab") tab: String): List<MovieListModel>
}