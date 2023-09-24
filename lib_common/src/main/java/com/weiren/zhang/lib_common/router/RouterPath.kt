package com.weiren.zhang.lib_common.router

/**
 * 全局路由路径
 */
object RouterPath {

    class MovieList {
        companion object {
            const val PATH_ThisWeek_HOME = "/movielist/thisweek"
            const val PATH_InTheaters_HOME = "/movielist/intheaters"
            const val PATH_ComIngSoon_HOME = "/movielist/comingsoon"
        }
    }

    class WebView {
        companion object {
            const val PATH_WebView_HOME = "/webView/webView"
        }
    }

    class MovieInfoMain {
        companion object {
            const val PATH_MovieInfoMain_HOME = "/movieinfomain/movieinfomain"
            const val PATH_MovieInfo_HOME = "/movieinfomain/movieinfo"
            const val PATH_StoreInfo_HOME = "/movieinfomain/storeinfo"
            const val PATH_MovieTimeTabs_HOME = "/movieinfomain/movietimetabs"
            const val PATH_Video_HOME = "/movieinfomain/video"
        }
    }

    class Theater {
        companion object {
            const val PATH_Area_HOME = "/theater/area"
            const val PATH_TheaterList_HOME = "/theater/theaterlist"
            const val PATH_TheaterResult_HOME = "/theater/theaterresult"
        }
    }

    class MyFavourite {
        companion object {
            const val PATH_MyFavourite_HOME = "/myfavourite/myfavourite"
            const val PATH_MovieMyFavourite_HOME = "/myfavourite/moviemyfavourite"
            const val PATH_TheaterMyFavourite_HOME = "/myfavourite/theatermyfavourite"
        }
    }

}