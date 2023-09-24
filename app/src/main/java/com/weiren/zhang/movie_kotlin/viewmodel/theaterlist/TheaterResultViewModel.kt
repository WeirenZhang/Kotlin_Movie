package com.weiren.zhang.movie_kotlin.viewmodel.theaterlist

import androidx.lifecycle.LiveData
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.movie_kotlin.api.TheaterListApi
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterResultModel
import com.weiren.zhang.movie_kotlin.model.TimeModel
import com.weiren.zhang.movie_kotlin.model.TypeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import javax.inject.Inject

@HiltViewModel
class TheaterResultViewModel @Inject
constructor(private val mDailyApi: TheaterListApi) : BaseViewModel() {

    fun getTheaterResultList(id: String): LiveData<List<TheaterResultModel>> =
        liveDataEx {
            val movieListModels = mutableListOf<TheaterResultModel>()
            val dailyModel = mDailyApi.getTheaterResultList(id)
            val stringResponse = dailyModel.body()?.string()
            val doc = Jsoup.parse(stringResponse);
            val elements = doc.select(".release_list>li");
            if (elements.size > 0) {
                for (element in elements) {
                    val theaterlist_name = element.select(".theaterlist_name>a").text()
                    println(theaterlist_name)

                    val en = element.select(".en>a").text()
                    println(en)

                    val release_foto = element.select(".release_foto > a >img").attr("src")
                    println(release_foto)

                    val url = element.select(".release_foto > a")
                    val id = url.attr("href").split("-").last()
                    println(id)

                    val icon = "https://s.yimg.com/cv/ae/movies/" + element.select("div[class*=icon]").attr("class") + ".png"
                    println(icon)

                    var tapbox = ArrayList<TypeModel>()
                    val tapboxs: Elements = element.select(".tapbox > div")
                    for (_tapbox in tapboxs) {
                        if (!_tapbox.text().equals("")) {
                            println(_tapbox.text())
                            val _Type = TypeModel(
                                _tapbox.text()
                            )
                            tapbox.add(_Type)
                        }
                    }

                    var theater_time = ArrayList<TimeModel>()
                    val theater_times: Elements = element.select(".theater_time > li")
                    for (_theater_time in theater_times) {
                        if (!_theater_time.text().equals("")) {
                            println(_theater_time.text())
                            val _Time = TimeModel(
                                _theater_time.text()
                            )
                            theater_time.add(_Time)
                        }
                    }

                    val movieListModel = TheaterResultModel(
                        id,
                        release_foto,
                        theaterlist_name,
                        en,
                        icon,
                        tapbox,
                        theater_time,
                    )
                    movieListModels.add(movieListModel)
                }
            }
            movieListModels
        }
}