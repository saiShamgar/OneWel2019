package com.Sairaa.onewel.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.Sairaa.onewel.R;

public class AboutUs extends AppCompatActivity {

    private TextView about_us_text;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        about_us_text=findViewById(R.id.about_us_text);
        back=findViewById(R.id.back);

        about_us_text.setText(getResources().getString(R.string.about_us));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
