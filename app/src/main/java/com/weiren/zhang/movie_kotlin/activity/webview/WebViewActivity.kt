package com.weiren.zhang.movie_kotlin.activity.webview

import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.weiren.zhang.lib_common.base.activity.BaseBindVMActivity
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.fromJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.databinding.ActivityWebviewBrowserBinding
import com.weiren.zhang.movie_kotlin.model.movieinfomain.VideoModel
import com.weiren.zhang.movie_kotlin.viewmodel.webview.WebViewViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@Route(path = RouterPath.WebView.PATH_WebView_HOME)
class WebViewActivity : BaseBindVMActivity<WebViewViewModel, ActivityWebviewBrowserBinding>() {

    @JvmField
    @Autowired(name = BaseConstant.Video_ID_KEY)
    var videoModelJSON: String = ""

    private lateinit var videoModel: VideoModel

    override fun getViewBinding() = ActivityWebviewBrowserBinding.inflate(layoutInflater)

    override fun initWindow() {
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED
        )
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        videoModel = fromJson(videoModelJSON)

        var loadUrl = videoModel.href

        var title = videoModel.title
        actionbar!!.title = title

        WebViewOption(loadUrl, title)
    }

    private fun WebViewOption(loadUrl: String, title: String) {

        val settings = mBind.pbWebView!!.settings

        settings.javaScriptEnabled = true

        //允许打开js新窗口

        settings.javaScriptCanOpenWindowsAutomatically = true

        settings.domStorageEnabled = true

        mBind.pbWebView!!.webViewClient = MyWebViewClient()

        mBind.pbWebView!!.webChromeClient = MyChromeWebViewClient()

        mBind.pbWebView!!.loadUrl(loadUrl)
    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
        }
    }

    private inner class MyChromeWebViewClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {

            if (newProgress == 100) {
                mBind.pbAd!!.visibility = View.GONE
            } else {
                mBind.pbAd!!.visibility = View.VISIBLE
                mBind.pbAd!!.setProgress(newProgress)
            }
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KEYCODE_BACK && mBind.pbWebView.canGoBack()) {
            mBind.pbWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

