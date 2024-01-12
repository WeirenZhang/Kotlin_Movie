package com.weiren.zhang.movie_kotlin.viewmodel.movieinfomain

import androidx.lifecycle.LiveData
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.movie_kotlin.api.MovieInfoMainApi
import com.weiren.zhang.movie_kotlin.model.TimeModel
import com.weiren.zhang.movie_kotlin.model.TypeModel
import com.weiren.zhang.movie_kotlin.model.movieinfomain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import javax.inject.Inject

@HiltViewModel
class MovieTimeResultViewModel @Inject
constructor(private val mDailyApi: MovieInfoMainApi) : BaseViewModel() {

    fun getMovieDateResult(id: String): LiveData<List<MovieDateTabItemModel>> =
        liveDataEx {
            val movieDateTabItemModels = mutableListOf<MovieDateTabItemModel>()
            val dailyModel = mDailyApi.getMovieDateResult(id, "MovieTime")
            if (dailyModel.size > 0) {
                for (element in dailyModel) {
                    movieDateTabItemModels.add(element)
                }
            }
            /*
            val dailyModel = mDailyApi.getMovieTimeResult(id, date, "", "", "", "")
            val doc = Jsoup.parse(dailyModel.view);
            val elements = doc.select("div.area_timebox");
            if (elements.size > 0) {
                for (element in elements) {
                    val area = element.select("div.area_title").text()
                    println(area)
                    val e: Elements = element.select("ul")
                    val movieTimeResults = mutableListOf<MovieTimeResultModel>()
                    for (e1 in e) {
                        val id = e1.select("li.adds > a").attr("href").split("=").last()
                        println(id)
                        val theater = e1.select("li.adds > a").text()
                        println(theater)
                        val tel = e1.select("li.adds > span").text()
                        println(tel)
                        var types = ArrayList<TypesModel>()
                        val e2: Elements = e1.select("li.taps")
                        //println(e2.text())
                        for (e3 in e2) {

                            var _types = ArrayList<TypeModel>()
                            val e4: Elements = e3.select("span")
                            for (e5 in e4) {
                                if (!e5.text().equals("")) {
                                    val _Type = TypeModel(
                                        e5.text()
                                    )
                                    println(e5.text())
                                    _types.add(_Type)
                                }
                            }

                            var _times = ArrayList<TimeModel>()
                            val e5: Elements = e3.nextElementSibling().select("label")
                            for (e6 in e5) {
                                if (!e6.text().equals("")) {
                                    val _Time = TimeModel(
                                        e6.text()
                                    )
                                    println(e6.text())
                                    _times.add(_Time)
                                }
                            }
                            val _Types = TypesModel(
                                _types,
                                _times
                            )
                            types.add(_Types)
                        }
                        val movieTimeResult = MovieTimeResultModel(
                            id,
                            theater,
                            tel,
                            types,
                        )
                        movieTimeResults.add(movieTimeResult)
                    }
                    val movieTimeTabItemModel = MovieTimeTabItemModel(
                        "",
                        area,
                        movieTimeResults,
                    )
                    movieTimeTabItemModels.add(movieTimeTabItemModel)
                }
            }
            */
            movieDateTabItemModels
        }
}

