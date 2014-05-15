package com.kenlist.blogrss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity {
    private ProgressDialog m_progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_web);
        
        WebView webView = (WebView)this.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            
            @Override
            public void onLoadResource(WebView view, String url) {
                m_progressDialog.dismiss();
            }
            
        });
        
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        
        webView.loadUrl(url);
        
        m_progressDialog = ProgressDialog.show(this, "", "Loading..", true);   
        
        
        
    }

}
