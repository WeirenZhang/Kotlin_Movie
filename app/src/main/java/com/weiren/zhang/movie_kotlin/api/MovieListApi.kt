package com.weiren.zhang.movie_kotlin.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListApi {
    @GET("movie_thisweek.html")
    suspend fun getMovieThisweekList(@Query("page") page: String): Response<ResponseBody>

    @GET("movie_intheaters.html")
    suspend fun getMovieIntheatersList(@Query("page") page: String): Response<ResponseBody>

    @GET("movie_comingsoon.html")
    suspend fun getMovieComingsoonList(@Query("page") page: String): Response<ResponseBody>
}