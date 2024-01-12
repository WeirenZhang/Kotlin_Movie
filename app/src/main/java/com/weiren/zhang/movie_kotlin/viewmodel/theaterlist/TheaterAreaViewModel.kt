package com.weiren.zhang.movie_kotlin.viewmodel.theaterlist

import androidx.lifecycle.LiveData
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.movie_kotlin.api.TheaterListApi
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterAreaModel
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import javax.inject.Inject

@HiltViewModel
class TheaterAreaViewModel @Inject
constructor(private val mDailyApi: TheaterListApi) : BaseViewModel() {

    fun getAreaList(): LiveData<List<TheaterAreaModel>> =
        liveDataEx {
            val theaterareaModels = mutableListOf<TheaterAreaModel>()
            val dailyModel = mDailyApi.getTheaterList("Area")
            if (dailyModel.size > 0) {
                for (element in dailyModel) {
                    theaterareaModels.add(element)
                }
            }
            /*
            val stringResponse = dailyModel.body()?.string()
            val doc = Jsoup.parse(stringResponse);
            val elements = doc.select(".theater_content");
            if (elements.size > 0) {
                for (element in elements) {
                    val title = element.select("div.theater_top").text()
                    println(title)
                    val id = element.attr("data-area")
                    println(id)

                    val links: Elements = element.select("li:not(.tab)")
                    val theaterInfoModels = mutableListOf<TheaterInfoModel>()
                    for (link in links) {
                        val tid = link.select(".name>a").attr("href").split("=").last()
                        println(tid)
                        val name = link.select(".name>a").text()
                        println(name)
                        val adds = link.select(".adds").text()
                        println(adds)
                        val tel = link.select(".tel").text()
                        println(tel)
                        val theaterInfoModel = TheaterInfoModel(
                            tid,
                            name,
                            adds,
                            tel,
                        )
                        theaterInfoModels.add(theaterInfoModel)
                    }
                    val theaterareaModel = TheaterAreaModel(
                        id,
                        title,
                        theaterInfoModels
                    )
                    theaterareaModels.add(theaterareaModel)
                }
            }
            */
            theaterareaModels
        }
}