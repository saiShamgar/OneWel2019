package com.Sairaa.onewel;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends BaseActivity {
    private WebView webView;
    private String phone_number,status;
    private Context context;
    private SharedPreferenceConfig config;
    private Button btn_next_matrimony_details;
    private RelativeLayout paymentLayout;
    private ImageView img_ic_close_promoter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        context=PaymentActivity.this;
        config=new SharedPreferenceConfig(this);

        phone_number=getIntent().getExtras().getString("number");
        status=getIntent().getExtras().getString("status");

        webView = findViewById(R.id.webView);
        btn_next_matrimony_details = findViewById(R.id.btn_next_matrimony_details);
        paymentLayout = findViewById(R.id.paymentLayout);
        img_ic_close_promoter = findViewById(R.id.img_ic_close_promoter);

        btn_next_matrimony_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading");
                webView.setWebViewClient(new MyBrowser());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.addJavascriptInterface(new WebAppInterface(getApplicationContext()), "Android");
                webView.loadUrl("http://sairaa.org/Onewel/razorpay.html");
            }
        });

    }

    private class WebAppInterface {
        Context mContext;
        public WebAppInterface(Context context) {
            mContext = context;
        }
        @android.webkit.JavascriptInterface
        public void showToast(String toast) {
            if (status.contains(Contants.MATRIMONY)){
                if (!toast.isEmpty()){
                    saveBuyedDetails(toast);
                }
                else {
                    AppUtils.showToast(context,"Something went wrong");
                }
            }
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveBuyedDetails(String toast) {
        FirebaseDatabase.getInstance().getReference().child(Contants.USERS)
                .child(config.readUserUniqueKey())
                .child(Contants.BUYED_LIST)
                .child(phone_number).setValue(toast)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            AppUtils.showToast(context,"Details Saved");
                        }

                    }
                });

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
            paymentLayout.setVisibility(View.GONE);

        }
    }

}
