package com.Sairaa.onewel.Activities.Add;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;

public class AddSignUpPD extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btn_next_add_details;
    private EditText edt_name_add,edt_phone_num_add,edt_ref_num_add;
    private Spinner spin_shop_category_add;

    String shop_type;
    private boolean validate;
    private Context mContext;
    private SharedPreferenceConfig config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sign_up_pd);

        mContext=AddSignUpPD.this;
        config=new SharedPreferenceConfig(this);

        btn_next_add_details=findViewById(R.id.btn_next_add_details);
        edt_name_add=findViewById(R.id.edt_name_add);
        edt_phone_num_add=findViewById(R.id.edt_phone_num_add);
        edt_ref_num_add=findViewById(R.id.edt_ref_num_add);
        spin_shop_category_add=findViewById(R.id.spin_shop_category_add);

        spin_shop_category_add.setOnItemSelectedListener(this);



        btn_next_add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (doValidation()){
                    saveAddPersonalDetails();
                }

            }
        });
    }

    private void saveAddPersonalDetails() {
        config.writeAdvertiserName(edt_name_add.getText().toString().trim());
        config.writeAdvertiserPhone(edt_phone_num_add.getText().toString().trim());
        config.writeAdvertiserRef_num(edt_ref_num_add.getText().toString().trim());
        config.writeAdvertiserShopType(shop_type);

        Intent gotFurtherDetails=new Intent(AddSignUpPD.this,AddShopDetails.class);
        startActivity(gotFurtherDetails);
    }

    boolean doValidation() {
        validate = true;

        if (edt_name_add.getText().toString().trim().length() == 0) {
            validate = false;
            edt_name_add.setError("Enter Add Name");
            edt_name_add.requestFocus();

        }
        if (edt_phone_num_add.getText().toString().trim().length() != 10 ) {
            validate = false;
            edt_phone_num_add.requestFocus();
            edt_phone_num_add.setError("Enter correct Phone number");

        }
        if (edt_ref_num_add.getText().toString().trim().length()>0){
            if (edt_ref_num_add.getText().toString().trim().length() != 10 ) {
                validate = false;
                edt_ref_num_add.requestFocus();
                edt_ref_num_add.setError("Enter correct Ref number");

            }
        }
        if (shop_type.contains("Choose a Shop")){
            validate = false;
            AppUtils.showToast(mContext,"Please Select Type of Shop");
        }

        return validate;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        shop_type=parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
