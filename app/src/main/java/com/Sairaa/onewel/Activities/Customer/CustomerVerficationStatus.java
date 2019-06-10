package com.Sairaa.onewel.Activities.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.RelativeLayout;
import android.widget.TextView;
import com.Sairaa.onewel.R;

public class CustomerVerficationStatus extends AppCompatActivity {

    private Button btn_verified_continue,btn_continue_not_verified;
    private String status,number;
    private RelativeLayout verifiedLayout,not_verifiedLayout;
    private TextView verified_number,verified_number_not_verified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_verfication_status);

        btn_verified_continue=findViewById(R.id.btn_verified_continue);
        verifiedLayout=findViewById(R.id.verifiedLayout);
        not_verifiedLayout=findViewById(R.id.not_verifiedLayout);
        btn_continue_not_verified=findViewById(R.id.btn_continue_not_verified);
        verified_number=findViewById(R.id.verified_number);
        verified_number_not_verified=findViewById(R.id.verified_number_not_verified);

        status=getIntent().getExtras().getString("status");
        number=getIntent().getExtras().getString("number");

        if (status.contains("verified")){
            verifiedLayout.setVisibility(View.VISIBLE);
            not_verifiedLayout.setVisibility(View.GONE);

            if (number!=null){
                verified_number.setText("+91 "+number);
            }

            btn_verified_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent payDetails=new Intent(CustomerVerficationStatus.this,CustomerPayingDetails.class);
                    startActivity(payDetails);
                }
            });

        }
        else {
            verifiedLayout.setVisibility(View.GONE);
            not_verifiedLayout.setVisibility(View.VISIBLE);

            if (number!=null){
                verified_number_not_verified.setText("+91 "+number);

            }

            btn_continue_not_verified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent payDetails=new Intent(CustomerVerficationStatus.this,CutomerVerification.class);
                    startActivity(payDetails);
                }
            });

        }


    }


}
