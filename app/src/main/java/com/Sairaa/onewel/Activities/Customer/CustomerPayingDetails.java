package com.Sairaa.onewel.Activities.Customer;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import com.Sairaa.onewel.Activities.Promoter.PromoterRegistrationSuccess;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.MainActivity;
import com.Sairaa.onewel.Model.Advertisement.ADDVERTISER_PAYING_DETAILS;
import com.Sairaa.onewel.Model.Customer.CUSTOMER_PAYING_DETAILS;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;

public class CustomerPayingDetails extends BaseActivity {

    private Button btn_next_customer_details;
    private EditText edt_name_customer,edt_total_price_customer,edt_discount_price_customer,edt_discription_customer;
    private boolean validate;
    private SharedPreferenceConfig config;
    private String number;
    private Context context;
    private ImageView img_ic_close_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_paying_details);

        config=new SharedPreferenceConfig(this);
        context=CustomerPayingDetails.this;

        number=getIntent().getExtras().getString("number");

        btn_next_customer_details=findViewById(R.id.btn_next_customer_details);
        edt_name_customer=findViewById(R.id.edt_name_customer);
        edt_total_price_customer=findViewById(R.id.edt_total_price_customer);
        edt_discount_price_customer=findViewById(R.id.edt_discount_price_customer);
        edt_discription_customer=findViewById(R.id.edt_discription_customer);
        img_ic_close_customer=findViewById(R.id.img_ic_close_customer);

        img_ic_close_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_next_customer_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()){
                    savePayingDetails();
                }

            }
        });
    }

    private void savePayingDetails() {

        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading");

        Query query=FirebaseDatabase.getInstance().getReference().child(Contants.ADVERTISER);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.hasChild(edt_name_customer.getText().toString().trim())){

                        final CUSTOMER_PAYING_DETAILS details=new CUSTOMER_PAYING_DETAILS(edt_name_customer.getText().toString().trim(),
                                edt_total_price_customer.getText().toString().trim(),
                                edt_discount_price_customer.getText().toString().trim(),
                                edt_discription_customer.getText().toString().trim());

                        FirebaseDatabase.getInstance().getReference().child(Contants.CUSTOMER_PAYING_DETAILS)
                        .child(number).push().setValue(details)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    ADDVERTISER_PAYING_DETAILS details1=new ADDVERTISER_PAYING_DETAILS(number,
                                            edt_total_price_customer.getText().toString().trim(),
                                            edt_discount_price_customer.getText().toString().trim(),
                                            edt_discription_customer.getText().toString().trim());
                                   FirebaseDatabase.getInstance().getReference().child(Contants.ADDVERTISER_PAYING_DETAILS)
                                           .child(edt_name_customer.getText().toString().trim())
                                           .push()
                                           .setValue(details1)
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()){
                                               AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                               Intent success=new Intent(CustomerPayingDetails.this,PromoterRegistrationSuccess.class);
                                               success.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                               startActivity(success);
                                           }
                                       }
                                   });
                                }
                            }
                        });

                    }
                    else {
                        AppUtils.dismissCustomProgress(mCustomProgressDialog);
                        AppUtils.showToast(context,"Registered number not valid");
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                AppUtils.showToast(context,databaseError.toString());


            }
        };
       query.addValueEventListener(valueEventListener);


    }

    boolean doValidation() {
        validate = true;

        if (edt_name_customer.getText().toString().trim().length() == 0) {
            validate = false;
            edt_name_customer.setError("Enter Shop Registered number");
            edt_name_customer.requestFocus();

        }
        else if (edt_name_customer.getText().toString().trim().length() != 10 ) {
            validate = false;
            edt_name_customer.requestFocus();
            edt_name_customer.setError("Enter correct Phone number");

        }else if (edt_total_price_customer.getText().toString().trim().length() == 0 ) {
            validate = false;
            edt_total_price_customer.requestFocus();
            edt_total_price_customer.setError("Enter Price");

        } else if (edt_discount_price_customer.getText().toString().trim().length() == 0) {
            validate = false;
            edt_discount_price_customer.requestFocus();
            edt_discount_price_customer.setError("Enter discount price");

        } else if (edt_discription_customer.getText().toString().trim().length() == 0) {
                validate = false;
                edt_discription_customer.requestFocus();
                edt_discription_customer.setError("Enter description");

        }

        return validate;
    }
}
