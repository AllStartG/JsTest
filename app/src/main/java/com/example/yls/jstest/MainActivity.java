package com.example.yls.jstest;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView contentWebView;
    private Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        contentWebView = (WebView) findViewById(R.id.webview);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        //启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);
        //从assets目录下面的加载html
        contentWebView.loadUrl("file:///android_asset/web.html");
        contentWebView.addJavascriptInterface(MainActivity.this,"android");
        contentWebView = new WebView(getApplicationContext());
        contentWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.contains("tarena/tell:")){
                    String index = "tarena/tell:";
                    String phone = url.substring(index.indexOf("tarena/tell".length()));
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tell"+phone));
                    startActivity(intent);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        btn1.setOnClickListener(new WebView.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentWebView.loadUrl("Javascript:javacalljs()");
            }
        });

        btn2.setOnClickListener(new WebView.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentWebView.loadUrl("Javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'"+")");
            }
        });


    }

    @JavascriptInterface
    public void startFunction(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"show",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
            }
        });
    }

}
