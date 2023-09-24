package com.weiren.zhang.movie_kotlin.module

import android.content.Context
import android.widget.Toast
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterInfoModel
import com.google.gson.reflect.TypeToken
import com.weiren.zhang.lib_common.ext.MMKVExt
import com.weiren.zhang.lib_common.ext.fromJson
import com.weiren.zhang.lib_common.ext.toJson

object TheaterManager {

    private const val Theater_RECORD = "theater_record"
    private var theaterJson by MMKVExt(Theater_RECORD, "")

    fun addTheaterRecord(context: Context, data: TheaterInfoModel) {
        theaterJson = if (theaterJson.isNullOrEmpty()) {
            val videoList = mutableListOf<TheaterInfoModel>()
            videoList.add(data)
            toJson(videoList)
        } else {
            val videoList: MutableList<TheaterInfoModel> =
                fromJson(theaterJson!!, object : TypeToken<MutableList<TheaterInfoModel>>() {}.type)

            if (!videoList.any {
                    it.id == data.id
                }) {
                videoList.add(data)
            } else {
                Toast.makeText(context, data.name + " 已加入我的最愛", Toast.LENGTH_LONG).show()
            }
            toJson(videoList)
        }
    }

    fun removeTheaterRecord(data: TheaterInfoModel) {
        val videoList: MutableList<TheaterInfoModel> =
            fromJson(theaterJson!!, object : TypeToken<MutableList<TheaterInfoModel>>() {}.type)
        videoList.remove(data)
        theaterJson = toJson(videoList)
    }

    fun getTheaterList(): MutableList<TheaterInfoModel> {
        return if (theaterJson.isNullOrEmpty()) {
            mutableListOf()
        } else {
            fromJson(theaterJson!!, object : TypeToken<MutableList<TheaterInfoModel>>() {}.type)
        }
    }

}