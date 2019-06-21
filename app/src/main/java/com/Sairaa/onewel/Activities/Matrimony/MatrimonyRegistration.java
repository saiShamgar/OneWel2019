package com.Sairaa.onewel.Activities.Matrimony;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.Sairaa.onewel.R;

public class MatrimonyRegistration extends AppCompatActivity {

    private EditText edt_mat_reg_name,edt_mat_reg_surname,edt_mat_reg_phone_num;
    private RadioGroup edt_mat_reg_gender,edt_mat_reg_marital_status,edt_mat_reg_physical_status,edt_mat_reg_eating,edt_mat_reg_drinking,edt_mat_reg_smoking;
    private Spinner edt_mat_reg_age,edt_mat_reg_height_in_feet,edt_mat_reg_heightin_inch,edt_mat_reg_religion,edt_mat_reg_mother_tongue,
             edt_mat_reg_caste_devision,edt_mat_reg_highest_study,edt_mat_reg_occupation,edt_mat_reg_annual_income,edt_mat_reg_country
            ,edt_mat_reg_state,edt_mat_reg_district,edt_mat_reg_city,edt_mat_reg_rashi,edt_mat_reg_star,edt_mat_reg_gothra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_registration);

        edt_mat_reg_phone_num=findViewById(R.id.edt_mat_reg_phone_num);
        edt_mat_reg_surname=findViewById(R.id.edt_mat_reg_surname);
        edt_mat_reg_name=findViewById(R.id.edt_mat_reg_name);
        edt_mat_reg_gender=findViewById(R.id.edt_mat_reg_gender);
        edt_mat_reg_marital_status=findViewById(R.id.edt_mat_reg_marital_status);
        edt_mat_reg_physical_status=findViewById(R.id.edt_mat_reg_physical_status);
        edt_mat_reg_eating=findViewById(R.id.edt_mat_reg_eating);
        edt_mat_reg_drinking=findViewById(R.id.edt_mat_reg_drinking);
        edt_mat_reg_smoking=findViewById(R.id.edt_mat_reg_smoking);
        edt_mat_reg_age=findViewById(R.id.edt_mat_reg_age);
        edt_mat_reg_height_in_feet=findViewById(R.id.edt_mat_reg_height_in_feet);
        edt_mat_reg_heightin_inch=findViewById(R.id.edt_mat_reg_heightin_inch);
        edt_mat_reg_religion=findViewById(R.id.edt_mat_reg_religion);
        edt_mat_reg_mother_tongue=findViewById(R.id.edt_mat_reg_mother_tongue);
        edt_mat_reg_caste_devision=findViewById(R.id.edt_mat_reg_caste_devision);
        edt_mat_reg_highest_study=findViewById(R.id.edt_mat_reg_highest_study);
        edt_mat_reg_occupation=findViewById(R.id.edt_mat_reg_occupation);
        edt_mat_reg_annual_income=findViewById(R.id.edt_mat_reg_annual_income);
        edt_mat_reg_country=findViewById(R.id.edt_mat_reg_country);
        edt_mat_reg_state=findViewById(R.id.edt_mat_reg_state);
        edt_mat_reg_district=findViewById(R.id.edt_mat_reg_district);
        edt_mat_reg_city=findViewById(R.id.edt_mat_reg_city);
        edt_mat_reg_rashi=findViewById(R.id.edt_mat_reg_rashi);
        edt_mat_reg_star=findViewById(R.id.edt_mat_reg_star);
        edt_mat_reg_gothra=findViewById(R.id.edt_mat_reg_gothra);



    }
}
