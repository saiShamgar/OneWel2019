package com.Sairaa.onewel.Activities.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Sairaa.onewel.MainActivity;
import com.Sairaa.onewel.R;

public class CustomerPayingDetails extends AppCompatActivity {

    private Button btn_next_customer_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_paying_details);

        btn_next_customer_details=findViewById(R.id.btn_next_customer_details);

        btn_next_customer_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeDetails=new Intent(CustomerPayingDetails.this, MainActivity.class);
                startActivity(takeDetails);
            }
        });
    }
}