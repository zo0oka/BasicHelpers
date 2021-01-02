package com.zo0okadev.basichelpers;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Zo0okaDev (https://github.com/zo0oka)
 * On 02 Jan, 2021.
 * Have a nice day!
 */
public class WebViewLoader {

    private final WebView webView;

    public WebViewLoader(WebView webView) {
        this.webView = webView;
        setSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setSettings() {
        WebSettings settings = webView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        webView.setBackgroundColor(0);
        webView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
    }

    public void loadHtmlData(String data) {
        String html = "<html>" +
                "<head>" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>" +
                "    <style type=\"text/css\">" +
                "        @font-face { font-family: 'Tajawal Light'; src: url('file:///android_asset/fonts/tajawal_light.ttf'); }" +
                "        body {font-family: 'Tajawal Light';}" +
                "    </style>" +
                "</head>" +
                "<body>" +
                data +
                "</body>" +
                "</html>";
        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }
}
