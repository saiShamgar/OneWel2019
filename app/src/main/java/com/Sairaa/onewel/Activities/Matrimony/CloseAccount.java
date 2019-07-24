package com.Sairaa.onewel.Activities.Matrimony;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.Sairaa.onewel.Activities.OtpActivity;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.google.firebase.database.*;

public class CloseAccount extends BaseActivity {

    private Context context;

    private EditText dialog_edt_reg_num;
    private TextView cancel_dialog,ok_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_account);

        context=CloseAccount.this;

        dialog_edt_reg_num=findViewById(R.id.dialog_edt_reg_num);
        cancel_dialog=findViewById(R.id.cancel_dialog);
        ok_dialog=findViewById(R.id.ok_dialog);

        ok_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog_edt_reg_num.getText().toString().length()==10){
                    AppUtils.showCustomProgressDialog(mCustomProgressDialog,"loading...");
                    Query query= FirebaseDatabase.getInstance().getReference().child(Contants.MATRIMONY);
                    ValueEventListener valueEventListener=new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                if (dataSnapshot.hasChild(dialog_edt_reg_num.getText().toString().trim())){
                                    AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                    AppUtils.showToast(context,"registered number");

                                    Intent otpActivity=new Intent(CloseAccount.this, OtpActivity.class);
                                    otpActivity.putExtra("status","CloseAccount");
                                    otpActivity.putExtra("number",dialog_edt_reg_num.getText().toString().trim());
                                    startActivity(otpActivity);
                                }
                                else {
                                    AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                    AppUtils.showToast(context,"Not registered");
                                }
                            }
                            AppUtils.dismissCustomProgress(mCustomProgressDialog);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            AppUtils.dismissCustomProgress(mCustomProgressDialog);
                            AppUtils.showToast(context,databaseError.getMessage());

                        }
                    };
                    query.addListenerForSingleValueEvent(valueEventListener);
                }
                else {
                    dialog_edt_reg_num.setError("Enter Valid number");
                }
            }
        });

        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
