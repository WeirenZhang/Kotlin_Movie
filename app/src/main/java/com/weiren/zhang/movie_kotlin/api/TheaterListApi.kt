package com.weiren.zhang.movie_kotlin.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TheaterListApi {
    @GET("theater_list.html")
    suspend fun getTheaterList(): Response<ResponseBody>

    @GET("theater_result.html/id={id}")
    suspend fun getTheaterResultList(@Path("id") id: String): Response<ResponseBody>
}