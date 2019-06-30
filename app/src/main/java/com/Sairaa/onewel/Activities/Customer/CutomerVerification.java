package com.Sairaa.onewel.Activities.Customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

public class CutomerVerification extends BaseActivity {

    private Button btn_next_cus_details,btn_signup_cus_details;
    private EditText edt_name_cus;
    private boolean validate;
    private ImageView img_ic_close_cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_verification);

        btn_next_cus_details=findViewById(R.id.btn_next_cus_details);
        btn_signup_cus_details=findViewById(R.id.btn_signup_cus_details);
        edt_name_cus=findViewById(R.id.edt_name_cus);
        img_ic_close_cus=findViewById(R.id.img_ic_close_cus);

        img_ic_close_cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_next_cus_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()){
                    checkCustomer();
                }

            }
        });

        btn_signup_cus_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verification=new Intent(CutomerVerification.this,CustomerRegistration.class);
                startActivity(verification);
            }
        });
    }

    private void checkCustomer() {
        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading...");
        Query query= FirebaseDatabase.getInstance().getReference().child(Contants.CUMTOMER);

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.hasChild(edt_name_cus.getText().toString().trim())){
                        AppUtils.dismissCustomProgress(mCustomProgressDialog);
                        Intent verification=new Intent(CutomerVerification.this,CustomerVerficationStatus.class);
                        verification.putExtra("status","verified");
                        verification.putExtra("number",edt_name_cus.getText().toString().trim());
                        startActivity(verification);
                    }
                    else {
                        AppUtils.dismissCustomProgress(mCustomProgressDialog);
                        Intent verification=new Intent(CutomerVerification.this,CustomerVerficationStatus.class);
                        verification.putExtra("status","notVerified");
                        verification.putExtra("number",edt_name_cus.getText().toString().trim());
                        startActivity(verification);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppUtils.dismissCustomProgress(mCustomProgressDialog);

            }
        };

        query.addValueEventListener(valueEventListener);


    }


    boolean doValidation() {
        validate = true;

        if (edt_name_cus.getText().toString().trim().length() == 0) {
            validate = false;
            edt_name_cus.setError("Enter Add number");
            edt_name_cus.requestFocus();

        }
        if (edt_name_cus.getText().toString().trim().length() != 10) {
            validate = false;
            edt_name_cus.setError("Enter correct phone number");
            edt_name_cus.requestFocus();

        }


        return validate;
    }

}


