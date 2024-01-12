package com.weiren.zhang.movie_kotlin.viewmodel.movieinfomain

import androidx.lifecycle.LiveData
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.movie_kotlin.api.MovieInfoMainApi
import com.weiren.zhang.movie_kotlin.model.movieinfomain.MovieInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject
constructor(private val mDailyApi: MovieInfoMainApi) : BaseViewModel() {

    fun getMovieInfo(id: String): LiveData<List<MovieInfoModel>> =
        liveDataEx {
            val movieInfoModels = mutableListOf<MovieInfoModel>()
            val dailyModel = mDailyApi.getMovieInfo(id, "MovieInfo")
            if (dailyModel.size > 0) {
                for (element in dailyModel) {
                    movieInfoModels.add(element)
                }
            }

            /*
            val stringResponse = dailyModel.body()?.string()
            val doc = Jsoup.parse(stringResponse);
            val h1 = doc.select("div.movie_intro_info_r > h1").text();
            println(h1)
            val h3 = doc.select("div.movie_intro_info_r > h3").text();
            println(h3)
            val movie_intro_foto = doc.select("div.movie_intro_foto > img").attr("src");
            println(movie_intro_foto)
            val icon =
                "https://s.yimg.com/cv/ae/movies/" + doc.select("div.movie_intro_info_r > div[class^='icon']")
                    .attr("class") + ".png";
            println(icon)
            val level_name_box = doc.select("div.level_name_box").text();
            println(level_name_box)
            val movie_intro_info_r = doc.select("div.movie_intro_info_r > span");
            var release_movie_time = "";
            var length = "";
            if (movie_intro_info_r.size > 0) {
                var num: Int = 0
                for (element in movie_intro_info_r) {
                    when (num) {
                        0 -> release_movie_time = element.text();
                        1 -> length = element.text();
                        else -> {
                        }
                    }
                    num += 1
                }
            }
            println(release_movie_time)
            println(length)
            val movie_intro_list = doc.select("span.movie_intro_list");
            var director = "";
            var actor = "";
            if (movie_intro_list.size > 0) {
                var num: Int = 0
                for (element in movie_intro_list) {
                    when (num) {
                        0 -> director = element.text();
                        1 -> actor = element.text();
                        else -> {
                        }
                    }
                    num += 1
                }
            }
            println(director)
            println(actor)
            val movieInfoModel = MovieInfoModel(
                h1,
                h3,
                movie_intro_foto,
                icon,
                level_name_box,
                release_movie_time,
                length,
                director,
                actor
            )
            movieInfoModels.add(movieInfoModel)
            */
            movieInfoModels
        }
}

