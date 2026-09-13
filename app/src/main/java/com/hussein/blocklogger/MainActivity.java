package com.hussein.blocklogger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A single full-screen WebView showing the logger from app assets.
 *
 * Two settings matter more than the rest:
 *   domStorageEnabled  - the entire training log lives in localStorage.
 *                        Without this the app silently forgets everything.
 *   useWideViewPort=false + loadWithOverviewMode=false
 *                      - forces the WebView to lay out at the real device
 *                        width instead of a fake 980px desktop viewport,
 *                        which is what made the page look zoomed out.
 */
public class MainActivity extends Activity {

    private WebView web;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        web = new WebView(this);
        web.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        web.setBackgroundColor(0xFF14181E);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setUseWideViewPort(false);
        s.setLoadWithOverviewMode(false);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);
        s.setTextZoom(100);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setAllowFileAccess(false);
        s.setAllowContentAccess(false);

        web.setWebViewClient(new WebViewClient());
        web.setOverScrollMode(WebView.OVER_SCROLL_NEVER);

        setContentView(web);

        if (savedInstanceState == null) {
            web.loadUrl("file:///android_asset/index.html");
        } else {
            web.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        web.saveState(outState);
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
