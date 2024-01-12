package com.weiren.zhang.movie_kotlin.api

import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterAreaModel
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterDateItemModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheaterListApi {
    //@GET("theater_list.html")
    //suspend fun getTheaterList(): Response<ResponseBody>

    @GET("macros/s/AKfycbwwB2Ke85PFeQqt2P9BRZFOxWif6JI4_ImblPyfFlP-VTJLkJJ6sZkCMD4tPhF_g8yT/exec")
    suspend fun getTheaterList(@Query("type") type: String): List<TheaterAreaModel>

    //@GET("theater_result.html/id={id}")
    //suspend fun getTheaterResultList(@Path("id") id: String): Response<ResponseBody>

    @GET("macros/s/AKfycbwwB2Ke85PFeQqt2P9BRZFOxWif6JI4_ImblPyfFlP-VTJLkJJ6sZkCMD4tPhF_g8yT/exec")
    suspend fun getTheaterResultList(@Query("cinema_id") id: String, @Query("type") type: String): List<TheaterDateItemModel>
}