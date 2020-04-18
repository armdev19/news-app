package com.infernal93.news.view.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.infernal93.news.R
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_detail.*


class NewsDetailActivity : AppCompatActivity() {
    private val TAG = "NewsDetailActivity"

    lateinit var mAlertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        mAlertDialog = SpotsDialog(this)
        mAlertDialog.show()

        //WebView
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {

                mAlertDialog.dismiss()
            }


        }

        if (intent != null)
            if (!intent.getStringExtra("webURL").isEmpty())
                webView.loadUrl(intent.getStringExtra("webURL"))

    }
}
