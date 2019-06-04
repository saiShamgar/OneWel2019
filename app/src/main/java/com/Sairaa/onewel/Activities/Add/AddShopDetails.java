package com.Sairaa.onewel.Activities.Add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.Sairaa.onewel.Activities.Promoter.PromoterRegistrationSuccess;
import com.Sairaa.onewel.Activities.Promoter.PromoterUPI;
import com.Sairaa.onewel.R;

public class AddShopDetails extends AppCompatActivity {
    private Button btn_next_add_shop_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_details);
        btn_next_add_shop_details=findViewById(R.id.btn_next_add_shop_details);

        btn_next_add_shop_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rigistrationSuccess=new Intent(AddShopDetails.this, PromoterRegistrationSuccess.class);
                startActivity(rigistrationSuccess);
            }
        });
    }
}
