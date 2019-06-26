package com.Sairaa.onewel.Activities.Matrimony;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.Contants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MatrimonyMainScreen extends AppCompatActivity {

    private TextView matrimony_reg_btn,matrimony_go;
    private AutoCompleteTextView matrimony_search_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_main_screen);

        matrimony_reg_btn=findViewById(R.id.matrimony_reg_btn);
        matrimony_search_items=findViewById(R.id.matrimony_search_items);
        matrimony_go=findViewById(R.id.matrimony_go);

        matrimony_reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(MatrimonyMainScreen.this,MatrimonyRegistration.class);
                startActivity(register);
            }
        });

        FirebaseDatabase.getInstance().getReference().child(Contants.MATRIMONY)
                .orderByValue().equalTo("dhfhd").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
