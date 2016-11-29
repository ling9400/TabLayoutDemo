package com.qk.tablayoutindicator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qk.tablayoutindicator.http.Utils;


/**
 * Created by qk on 2016/11/11.
 * 浏览器
 */

public class ActivityBrowser extends Activity {
    private WebView webView;
    private String url;
    private Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_browser);
        myContext = ActivityBrowser.this;
        webView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        if(intent != null){
            url = intent.getStringExtra("url");
        }

        if(Utils.isNullOrEmpty(url)){
            //显示对话框，出错
            new AlertDialog.Builder(myContext).setCancelable(false).setMessage("网址地址出错了，请退出重试")
                    .setTitle("出错了").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        }else{
            bindData();
        }
    }

    private void bindData() {
        WebSettings settings = webView.getSettings();
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient());
    }
}
