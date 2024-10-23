package com.weiren.zhang.movie_kotlin.api

import com.weiren.zhang.movie_kotlin.model.movieinfomain.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInfoMainApi {
    //@GET("movieinfo_main/{id}")
    //suspend fun getMovieInfo(@Path("id") id: String): Response<ResponseBody>

    @GET("macros/s/AKfycbzNPN95_VIeYPTKF85yVS5oml_lUiVL0TUlQvuNj1krEUjUQFtBq_BY6eraap6zW2ZI/exec")
    suspend fun getMovieInfo(@Query("movie_id") movie_id: String, @Query("type") type: String): List<MovieInfoModel>

    @GET("macros/s/AKfycbzNPN95_VIeYPTKF85yVS5oml_lUiVL0TUlQvuNj1krEUjUQFtBq_BY6eraap6zW2ZI/exec")
    suspend fun getStoreInfo(@Query("movie_id") movie_id: String, @Query("type") type: String): List<StoreInfoModel>

    @GET("macros/s/AKfycbzNPN95_VIeYPTKF85yVS5oml_lUiVL0TUlQvuNj1krEUjUQFtBq_BY6eraap6zW2ZI/exec")
    suspend fun getVideo(@Query("movie_id") movie_id: String, @Query("type") type: String): List<VideoModel>

    //@GET("ajax/pc/get_schedule_by_movie")
    //suspend fun getMovieTimeResult(@Query("movie_id") movie_id: String, @Query("date") date: String, @Query("area_id") area_id: String, @Query("theater_id") theater_id: String, @Query("datetime") datetime: String, @Query("movie_type_id") movie_type_id: String): GetScheduleByMovieModel

    @GET("macros/s/AKfycbzNPN95_VIeYPTKF85yVS5oml_lUiVL0TUlQvuNj1krEUjUQFtBq_BY6eraap6zW2ZI/exec")
    suspend fun getMovieDateResult(@Query("movie_id") movie_id: String, @Query("type") type: String): List<MovieDateTabItemModel>
}