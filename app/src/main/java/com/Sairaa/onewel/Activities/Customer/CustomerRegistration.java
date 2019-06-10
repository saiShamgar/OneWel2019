package com.Sairaa.onewel.Activities.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Sairaa.onewel.Activities.Promoter.PromoterRegistrationSuccess;
import com.Sairaa.onewel.R;

public class CustomerRegistration extends AppCompatActivity {

    private Button btn_customer_reg_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        btn_customer_reg_details=findViewById(R.id.btn_customer_reg_details);

        btn_customer_reg_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  reg=new Intent(CustomerRegistration.this, PromoterRegistrationSuccess.class);
                startActivity(reg);

            }
        });

    }
}
