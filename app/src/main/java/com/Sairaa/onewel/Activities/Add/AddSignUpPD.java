package com.Sairaa.onewel.Activities.Add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.Sairaa.onewel.R;

public class AddSignUpPD extends AppCompatActivity {

    private Button btn_next_add_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sign_up_pd);

        btn_next_add_details=findViewById(R.id.btn_next_add_details);

        btn_next_add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotFurtherDetails=new Intent(AddSignUpPD.this,AddShopDetails.class);
                startActivity(gotFurtherDetails);
            }
        });
    }
}
