package com.Sairaa.onewel;

import android.content.Context;
import android.content.Intent;
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
import com.Sairaa.onewel.Activities.OtpActivity;
import com.Sairaa.onewel.Activities.Promoter.PromoterRegistrationSuccess;
import com.Sairaa.onewel.Model.Customer.CustomerDetails;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

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

        if (status.contains(Contants.MATRIMONY)){
            btn_next_matrimony_details.setText("Pay rs 100");
        }
        else if (status.contains(Contants.CUMTOMER)){
            btn_next_matrimony_details.setText("Pay rs 100");
        }

        btn_next_matrimony_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading");
                if (status.contains(Contants.MATRIMONY)){
                    webView.setWebViewClient(new MyBrowser());
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.addJavascriptInterface(new WebAppInterface(getApplicationContext()), "Android");
                    webView.loadUrl("http://sairaa.org/Onewel/razorpay.php?amount=10000&key=rzp_test_XZhkhiMzBkJjZf");
                }else if (status.contains(Contants.CUMTOMER)){
                    webView.setWebViewClient(new MyBrowser());
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.addJavascriptInterface(new WebAppInterface(getApplicationContext()), "Android");
                    webView.loadUrl("http://sairaa.org/Onewel/razorpay.php?amount=10000&key=rzp_test_XZhkhiMzBkJjZf");
                }

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
            else if (status.contains(Contants.CUMTOMER)){
                if (!toast.isEmpty()){
                    CustomerDetails customerDetails=new CustomerDetails(config.readCustomer_name(),
                            config.readCustomer_phone(),
                            config.readCustomer_ref());
                    FirebaseDatabase.getInstance().getReference().child(Contants.CUMTOMER)
                            .child(config.readCustomer_phone())
                            .setValue(customerDetails)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        saveRefNum(config.readCustomer_phone(),config.readCustomer_ref(),3);
                                    }
                                    else {
                                        AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                        AppUtils.showToast(context,"Registration Failed please try again");
                                    }

                                }
                            });
                }
                else {
                    AppUtils.showToast(context,"Something went wrong");
                }

            }
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveRefNum(String phone_num, String ref_num, int i) {
        Map<String, Object> postValues = new HashMap<String,Object>();
        postValues.put("status",status);
        postValues.put("Number",phone_num);
        if (i==3){
            FirebaseDatabase.getInstance().getReference().child(Contants.REFERENCES)
                    .child(ref_num).push()
                    .child("REFERRED")
                    .setValue(phone_num)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                //verification successful we will start the profile activity
                                Intent intent = new Intent(PaymentActivity.this, PromoterRegistrationSuccess.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        }
                    });
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
                            onBackPressed();
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
