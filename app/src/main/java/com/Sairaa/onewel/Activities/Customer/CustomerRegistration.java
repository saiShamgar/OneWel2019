package com.Sairaa.onewel.Activities.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import com.Sairaa.onewel.Activities.Add.AddShopDetails;
import com.Sairaa.onewel.Activities.Add.AddSignUpPD;
import com.Sairaa.onewel.Activities.OtpActivity;
import com.Sairaa.onewel.Activities.Promoter.PromoterRegistrationSuccess;
import com.Sairaa.onewel.Activities.Promoter.PromoterSignUp;
import com.Sairaa.onewel.Activities.Promoter.PromoterUPI;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.google.firebase.database.*;

public class CustomerRegistration extends BaseActivity {

    private Button btn_customer_reg_details;
    private EditText edt_name_customer_reg,edt_phone_num_customer_reg,edt_ref_num_customer_reg;
    boolean validate;
    private SharedPreferenceConfig config;
    private ImageView img_ic_close_customer_Reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        config=new SharedPreferenceConfig(this);

        btn_customer_reg_details=findViewById(R.id.btn_customer_reg_details);
        edt_name_customer_reg=findViewById(R.id.edt_name_customer_reg);
        edt_phone_num_customer_reg=findViewById(R.id.edt_phone_num_customer_reg);
        edt_ref_num_customer_reg=findViewById(R.id.edt_ref_num_customer_reg);
        img_ic_close_customer_Reg=findViewById(R.id.img_ic_close_customer_Reg);

        img_ic_close_customer_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_customer_reg_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()){
                       saveCusPersonalDetails();
                }
            }
        });

    }

    private void saveCusPersonalDetails() {
        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading...");
        Query query= FirebaseDatabase.getInstance().getReference().child(Contants.CUMTOMER);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.hasChild(edt_phone_num_customer_reg.getText().toString().trim())){
                        AppUtils.dismissCustomProgress(mCustomProgressDialog);
                        // AppUtils.showToast(mContext,"Phone number already registered");
                        edt_phone_num_customer_reg.requestFocus();
                        edt_phone_num_customer_reg.setError("Phone number already registered");
                        Log.e("status","Phone number already registered");
                    }else {
                        if (edt_ref_num_customer_reg.getText().toString().trim().length()>0){

                            Query query1=FirebaseDatabase.getInstance().getReference().child(Contants.PROMOTER);
                            ValueEventListener valueEventListener1=new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.hasChild(edt_ref_num_customer_reg.getText().toString().trim())){
                                        AppUtils.dismissCustomProgress(mCustomProgressDialog);

                                        // AppUtils.showToast(mContext,"Entered reference number is not valid");
                                        Log.e("status","Entered reference number is not valid");
                                        edt_ref_num_customer_reg.requestFocus();
                                        edt_ref_num_customer_reg.setError("Entered reference number is not valid");
                                    }
                                    else {
                                        AppUtils.dismissCustomProgress(mCustomProgressDialog);

                                        Log.e("status","Entered reference number is valid");
                                        config.writeCustomer_name(edt_name_customer_reg.getText().toString().trim());
                                        config.writeCustomer_phone(edt_phone_num_customer_reg.getText().toString().trim());
                                        config.writeCustomer_ref(edt_ref_num_customer_reg.getText().toString().trim());


                                        Intent  reg=new Intent(CustomerRegistration.this, OtpActivity.class);
                                        reg.putExtra("status","customer");
                                        startActivity(reg);
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            };
                            query1.addValueEventListener(valueEventListener1);

                        }else {
                            Log.e("status"," reference number  empty");
                            AppUtils.dismissCustomProgress(mCustomProgressDialog);
                            config.writeCustomer_name(edt_name_customer_reg.getText().toString().trim());
                            config.writeCustomer_phone(edt_phone_num_customer_reg.getText().toString().trim());
                            config.writeCustomer_ref(edt_ref_num_customer_reg.getText().toString().trim());

                            Intent  reg=new Intent(CustomerRegistration.this, OtpActivity.class);
                            reg.putExtra("status","customer");
                            startActivity(reg);
                        }

                    }
                }
                else {
                    AppUtils.dismissCustomProgress(mCustomProgressDialog);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                Log.e("error promoterSignUp",databaseError.toString());

            }
        };
        query.addValueEventListener(valueEventListener);



    }


    boolean doValidation() {
        validate = true;

        if (edt_name_customer_reg.getText().toString().trim().length() == 0) {
            validate = false;
            edt_name_customer_reg.setError("Enter Name");
            edt_name_customer_reg.requestFocus();

        } else if (edt_phone_num_customer_reg.getText().toString().trim().length() != 10 ) {
            validate = false;
            edt_phone_num_customer_reg.requestFocus();
            edt_phone_num_customer_reg.setError("Enter correct Phone number");

        }

        return validate;
    }
}
