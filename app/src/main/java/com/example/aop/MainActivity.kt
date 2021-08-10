package com.example.aop

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private val addressBar: EditText by lazy {
        findViewById(R.id.addressbar)
    }
    private val webView: WebView by lazy {
        findViewById(R.id.Webview)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        bindViews()
    }
//enter가 엑션 버튼임.. imeOptions!!


    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        //보안 이슈가 참 많다
        webView.apply {
            // 코틀린 문법이라네..???
            webViewClient = WebViewClient()
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
//        webView.webViewClient = WebViewClient()
//        webView.settings.javaScriptEnabled = true
//
//        webView.loadUrl("http://www.google.com")
        //보안 이슈가 참 많다
    }

    // return 값이 true 이면 소비해서 갇
    private fun bindViews() {
        addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                webView.loadUrl(v.text.toString())
            }
            return@setOnEditorActionListener false
        }
    }
}