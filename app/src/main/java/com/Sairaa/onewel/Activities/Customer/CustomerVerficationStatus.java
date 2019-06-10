package com.Sairaa.onewel.Activities.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Sairaa.onewel.R;

public class CustomerVerficationStatus extends AppCompatActivity {

    private Button btn_verified_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_verfication_status);

        btn_verified_continue=findViewById(R.id.btn_verified_continue);

        btn_verified_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent payDetails=new Intent(CustomerVerficationStatus.this,CustomerPayingDetails.class);
                startActivity(payDetails);
            }
        });
    }


}
