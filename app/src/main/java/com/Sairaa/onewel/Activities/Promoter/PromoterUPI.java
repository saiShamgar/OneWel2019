package com.Sairaa.onewel.Activities.Promoter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.Sairaa.onewel.Activities.OtpActivity;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;

public class PromoterUPI extends AppCompatActivity {

    private Button btn_next_promoter_details;
    private EditText edt_uip_num_promoter;

    private boolean validate;
    private SharedPreferenceConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_upi);

        config=new SharedPreferenceConfig(this);

        btn_next_promoter_details=findViewById(R.id.btn_next_promoter_details);
        edt_uip_num_promoter=findViewById(R.id.edt_uip_num_promoter);

        btn_next_promoter_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()){
                    config.writePromoterUPI_num(edt_uip_num_promoter.getText().toString().trim());
                    Intent rigistrationSuccess=new Intent(PromoterUPI.this, OtpActivity.class);
                    rigistrationSuccess.putExtra("status","promoter");
                    startActivity(rigistrationSuccess);
                }
            }
        });
    }

    boolean doValidation() {
        validate = true;

        if (edt_uip_num_promoter.getText().toString().trim().length() != 10) {
            validate = false;
            edt_uip_num_promoter.setError("Enter Correct Upi number");
            edt_uip_num_promoter.requestFocus();

        }
        return validate;
    }
}
