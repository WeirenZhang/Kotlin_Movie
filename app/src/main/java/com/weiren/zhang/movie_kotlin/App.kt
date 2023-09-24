package com.weiren.zhang.movie_kotlin

import android.app.Application
import com.weiren.zhang.lib_common.global.Configurator
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var mConfigurator: Configurator

    override fun onCreate() {
        super.onCreate()
        mConfigurator.withWebApiHost("https://movies.yahoo.com.tw/").configure()
    }

}