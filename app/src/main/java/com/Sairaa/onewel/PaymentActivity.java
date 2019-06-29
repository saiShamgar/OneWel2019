package com.Sairaa.onewel;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.Sairaa.onewel.Utils.AppUtils;

public class PaymentActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        webView = findViewById(R.id.webView);
        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading");
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl("http://sairaa.org/Onewel/razorpay.html");


    }

    private class WebAppInterface {
        Context mContext;
        public WebAppInterface(Context context) {
            mContext = context;
        }
        @android.webkit.JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    public class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            AppUtils.dismissCustomProgress(mCustomProgressDialog);

        }
    }

}
