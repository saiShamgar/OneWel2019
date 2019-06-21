package com.Sairaa.onewel.Activities.Matrimony;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.Sairaa.onewel.R;

public class MatrimonyMainScreen extends AppCompatActivity {

    private TextView matrimony_reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_main_screen);

        matrimony_reg_btn=findViewById(R.id.matrimony_reg_btn);

        matrimony_reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(MatrimonyMainScreen.this,MatrimonyRegistration.class);
                startActivity(register);
            }
        });
    }
}
