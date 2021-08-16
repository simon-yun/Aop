package com.example.aop

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

// 리플 이펙트 주는 방식이 다양한데,,, 간닿낙러로
class MainActivity : AppCompatActivity() {


    private val progressBar: ContentLoadingProgressBar by lazy {
        findViewById(R.id.progresbar)
    }
    private val refreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.refreshlaylout)
    }
    private val addressBar: EditText by lazy {
        findViewById(R.id.addressbar)
    }
    private val webView: WebView by lazy {
        findViewById(R.id.Webview)
    }

    private val goHomeButton: ImageButton by lazy {
        findViewById(R.id.GoHomeButton)
    }

    private val goBackButton: ImageButton by lazy {
        findViewById(R.id.GoBackButton)
    }

    private val goForwardButton: ImageButton by lazy {
        findViewById(R.id.goFowardButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        bindViews()
    }
//enter가 엑션 버튼임.. imeOptions!!

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
//    웹뷰 클라이언트는 프로그래스를 몰라
//    웹크로운 브라우저 .. 자바스크비르트이 얼ㄹ엇ㅇ
//    브라우저 관점에서 오버라이드 해주는것
    private fun initViews() {
        //보안 이슈가 참 많다
        webView.apply {
            // 코틀린 문법이라네..???
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            //webView.loadUrl("http://www.google.com")
            webChromeClient = WebChromeClient()
            webView.loadUrl(DRFAULT_URL)
        }
//        webView.webViewClient = WebViewClient()
//        webView.settings.javaScriptEnabled = true
//
        //webView.loadUrl("http://www.google.com")
        //보안 이슈가 참 많다
    }

    // return 값이 true 이면 소비해서 갇
    private fun bindViews() {
        addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //webView.loadUrl(v.text.toString())
                val loadingUrl = v.text.toString()
                if(URLUtil.isNetworkUrl(loadingUrl)) {
                    webView.loadUrl(loadingUrl)
                } else {
                    webView.loadUrl("http://$loadingUrl")
                }

            }
            return@setOnEditorActionListener false
        }

        goBackButton.setOnClickListener {
            webView.goBack()
        }
        goForwardButton.setOnClickListener {
            webView.goForward()
        }

        goHomeButton.setOnClickListener {
            webView.loadUrl(DRFAULT_URL)
        }

        refreshLayout.setOnRefreshListener {
            webView.reload()
        }
    }


    //inner 를 쓰는 이유는 상위에 정의해 놓은 프로퍼티에 접근 하기 위한것 .. 자바에서 뭐에 해당하난??
//    overide 생성 단축키 alt + insert


    //    왜 프로그ㅐㄹ
    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            refreshLayout.isRefreshing = false
            progressBar.hide()
            goBackButton.isEnabled=webView.canGoBack() // 뒤로webView.canGoBack( 이게 뒤로 갈 수  있는 지를,
            goForwardButton.isEnabled = webView.canGoForward()
//            리다이렉팅 실제 주소 와 모바일 주소의 차이 ..
            addressBar.setText(url)
        }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {


        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            progressBar.progress = newProgress
        }
    }

    companion object {
        private const val DRFAULT_URL = "http://www.google.com"
    }
}