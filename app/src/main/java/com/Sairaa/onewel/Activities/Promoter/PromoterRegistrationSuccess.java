package com.Sairaa.onewel.Activities.Promoter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.Sairaa.onewel.MainActivity;
import com.Sairaa.onewel.R;

public class PromoterRegistrationSuccess extends AppCompatActivity {
    private Button btn_next_promoter_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_registration_success);

        btn_next_promoter_details=findViewById(R.id.btn_next_promoter_details);

        btn_next_promoter_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_home=new Intent(PromoterRegistrationSuccess.this, MainActivity.class);
                go_to_home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(go_to_home);
            }
        });
    }
}
