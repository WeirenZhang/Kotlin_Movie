package com.weiren.zhang.movie_kotlin.viewmodel.movieinfomain

import androidx.lifecycle.LiveData
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.movie_kotlin.api.MovieInfoMainApi
import com.weiren.zhang.movie_kotlin.model.movieinfomain.VideoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject
constructor(private val mDailyApi: MovieInfoMainApi) : BaseViewModel() {

    fun getVideo(id: String): LiveData<List<VideoModel>> =
        liveDataEx {
            val videoModels = mutableListOf<VideoModel>()
            val dailyModel = mDailyApi.getMovieInfo(id)
            val stringResponse = dailyModel.body()?.string()
            val doc = Jsoup.parse(stringResponse);
            val have_arbox = doc.select("div.have_arbox");
            if (have_arbox.size > 0) {
                for (element in have_arbox) {
                    if (element.select("div.title").text().contains("預告")) {
                        val trailer_list = element.select("ul.trailer_list > li");
                        if (trailer_list.size > 0) {
                            for (element1 in trailer_list) {
                                val title = element1.select("h2.text_truncate_2").text();
                                println(title)
                                val href = element1.select("a").attr("href");
                                println(href)
                                val cover = element1.select("div.foto > img").attr("data-src");
                                println(cover)
                                val videoModel = VideoModel(
                                    title,
                                    href,
                                    cover
                                )
                                videoModels.add(videoModel)
                            }
                        }
                    }
                }
            }
            videoModels
        }
}

