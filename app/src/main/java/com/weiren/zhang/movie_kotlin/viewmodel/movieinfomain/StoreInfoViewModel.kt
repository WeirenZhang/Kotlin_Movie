package com.weiren.zhang.movie_kotlin.viewmodel.movieinfomain

import androidx.lifecycle.LiveData
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.movie_kotlin.api.MovieInfoMainApi
import com.weiren.zhang.movie_kotlin.model.movieinfomain.StoreInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class StoreInfoViewModel @Inject
constructor(private val mDailyApi: MovieInfoMainApi) : BaseViewModel() {

    fun getStoreInfo(id: String): LiveData<List<StoreInfoModel>> =
        liveDataEx {
            val storeInfoModels = mutableListOf<StoreInfoModel>()
            val dailyModel = mDailyApi.getStoreInfo(id, "StoreInfo")
            if (dailyModel.size > 0) {
                for (element in dailyModel) {
                    storeInfoModels.add(element)
                }
            }
            /*
            val stringResponse = dailyModel.body()?.string()
            val doc = Jsoup.parse(stringResponse);
            val storeInfo = doc.select("span#story").html().replace("<br>", "\n").replace(" ", "");
            println(storeInfo)
            val storeInfoModel = StoreInfoModel(
                storeInfo
            )
            storeInfoModels.add(storeInfoModel)
            */
            storeInfoModels
        }
}


