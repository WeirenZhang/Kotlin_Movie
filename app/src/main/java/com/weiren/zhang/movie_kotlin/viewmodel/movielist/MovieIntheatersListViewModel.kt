package com.weiren.zhang.movie_kotlin.viewmodel.movielist

import androidx.lifecycle.LiveData
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.movie_kotlin.api.MovieListApi
import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class MovieIntheatersListViewModel @Inject
constructor(private val mDailyApi: MovieListApi) : BaseViewModel() {

    private var Page: Int = 1

    fun getMovieList(isRefreshing: Boolean): LiveData<List<MovieListModel>> =
        liveDataEx {
            if (isRefreshing) {
                Page = 1
            }
            val movieListModels = mutableListOf<MovieListModel>()
            val dailyModel = mDailyApi.getMovieList(Page.toString(), "MovieList", "0")
            if (dailyModel.size > 0) {
                Page += 1
                for (element in dailyModel) {
                    movieListModels.add(element)
                }
            }

            /*
            val stringResponse = dailyModel.body()?.string()
            val doc = Jsoup.parse(stringResponse);
            val elements = doc.select(".release_list>li");
            if (elements.size > 0) {
                Page += 1
                for (element in elements) {
                    val release_movie_name = element.select("div.release_movie_name > a").text()
                    println(release_movie_name)
                    val en = element.select("div.release_movie_name > .en > a").text()
                    println(en)
                    val release_movie_time = element.select("div.release_movie_time").text()
                    println(release_movie_time)
                    val img = element.select("div.release_foto > a > img").attr("data-src")
                    println(img)
                    val url = element.select("div.release_foto > a")
                    val movie_id = url.attr("href").split("-").last()
                    println(movie_id)
                    val movieListModel = MovieListModel(
                        release_movie_name,
                        en,
                        release_movie_time,
                        img,
                        movie_id
                    )
                    movieListModels.add(movieListModel)
                }
            }
            */
            movieListModels
        }
}