package com.Sairaa.onewel.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.google.firebase.database.*;

public class UserHistory extends BaseActivity {

    private EditText edt_number_history;
    private Spinner spinner_type_of_user;
    private boolean validate;
    private String type_of_user;
    private Context context;
    private Button btn_next_history_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        edt_number_history=findViewById(R.id.edt_number_history);
        spinner_type_of_user=findViewById(R.id.spinner_type_of_user);
        btn_next_history_details=findViewById(R.id.btn_next_history_details);

        context=UserHistory.this;

        btn_next_history_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()){
                    checkUser(type_of_user);
                }
            }
        });

        spinner_type_of_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_of_user=parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkUser(final String type_of_user) {

        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading..");

        Query query=FirebaseDatabase.getInstance().getReference().child(type_of_user);

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.hasChild(edt_number_history.getText().toString().trim())){
                        AppUtils.dismissCustomProgress(mCustomProgressDialog);
                        Intent otp=new Intent(UserHistory.this,OtpActivity.class);
                        otp.putExtra("status",type_of_user);
                        otp.putExtra("number",edt_number_history.getText().toString());
                        startActivity(otp);
                    }
                    else {
                        AppUtils.dismissCustomProgress(mCustomProgressDialog);

                        AppUtils.showToast(context,"This is not a registered number");
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
        if (edt_number_history.getText().toString().trim().length() == 0) {
            validate = false;
            edt_number_history.setError("Enter Shop Registered number");
            edt_number_history.requestFocus();

        } else if (edt_number_history.getText().toString().trim().length() != 10) {
            validate = false;
            edt_number_history.requestFocus();
            edt_number_history.setError("Enter correct Phone number");

        }
        else if (type_of_user.contains("Type of user")){
            validate = false;
            AppUtils.showToast(context,"Select type of user");
        }

        return validate;
    }

}
