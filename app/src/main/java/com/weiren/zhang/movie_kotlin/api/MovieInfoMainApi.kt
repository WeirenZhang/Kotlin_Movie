package com.weiren.zhang.movie_kotlin.api

import com.weiren.zhang.movie_kotlin.model.movieinfomain.GetScheduleByMovieModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInfoMainApi {
    @GET("movieinfo_main/{id}")
    suspend fun getMovieInfo(@Path("id") id: String): Response<ResponseBody>

    @GET("ajax/pc/get_schedule_by_movie")
    suspend fun getMovieTimeResult(@Query("movie_id") movie_id: String, @Query("date") date: String, @Query("area_id") area_id: String, @Query("theater_id") theater_id: String, @Query("datetime") datetime: String, @Query("movie_type_id") movie_type_id: String): GetScheduleByMovieModel
}