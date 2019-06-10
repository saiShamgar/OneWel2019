package com.Sairaa.onewel.Activities.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Sairaa.onewel.R;

public class CutomerVerification extends AppCompatActivity {

    private Button btn_next_cus_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_verification);

        btn_next_cus_details=findViewById(R.id.btn_next_cus_details);

        btn_next_cus_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verification=new Intent(CutomerVerification.this,CustomerVerficationStatus.class);
                startActivity(verification);
            }
        });
    }
}
