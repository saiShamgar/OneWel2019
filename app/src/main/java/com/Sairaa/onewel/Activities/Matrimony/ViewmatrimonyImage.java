package com.Sairaa.onewel.Activities.Matrimony;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.Sairaa.onewel.Model.MatrimonyInsertionData;
import com.Sairaa.onewel.R;
import com.bumptech.glide.Glide;

public class ViewmatrimonyImage extends AppCompatActivity {

    private MatrimonyInsertionData insertionData;

    private TextView txt_mat_reg_name,txt_mat_reg_surname,txt_mat_reg_phone_num,txt_mat_reg_gender,txt_mat_reg_age,txt_mat_reg_height,
        txt_mat_reg_marital_status,txt_mat_reg_religion,txt_mat_reg_mother_tongue,txt_mat_reg_caste_devision,txt_mat_reg_physical_status
            ,txt_mat_reg_father_profession,txt_mat_reg_mother_profession,txt_mat_reg_siaters,txt_mat_reg_brothers,
            txt_mat_reg_highest_study,txt_mat_reg_profession,txt_mat_reg_annual_income,txt_mat_reg_country,txt_mat_reg_state,txt_mat_reg_city
            ,txt_mat_reg_district,txt_mat_reg_rashi,txt_mat_reg_nakshatram,txt_mat_reg_gothra,txt_mat_reg_eating,txt_mat_reg_drinking,
          txt_mat_reg_smoking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmatrimony_image);

        insertionData=(MatrimonyInsertionData) getIntent().getSerializableExtra("details");
        // Get Toolbar component.
        Toolbar toolbar = (Toolbar)findViewById(R.id.collapsing_toolbar);
        // Use Toolbar to replace default activity action bar.
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            // Display home menu item.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set collapsing tool bar title.
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("Full Details");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.color_black));

        // Set collapsing tool bar image.
        ImageView collapsingToolbarImageView = (ImageView)findViewById(R.id.collapsing_toolbar_image_view);


        txt_mat_reg_name=findViewById(R.id.txt_mat_reg_name);
        txt_mat_reg_surname=findViewById(R.id.txt_mat_reg_surname);
        txt_mat_reg_phone_num=findViewById(R.id.txt_mat_reg_phone_num);
        txt_mat_reg_gender=findViewById(R.id.txt_mat_reg_gender);
        txt_mat_reg_age=findViewById(R.id.txt_mat_reg_age);
        txt_mat_reg_height=findViewById(R.id.txt_mat_reg_height);
        txt_mat_reg_marital_status=findViewById(R.id.txt_mat_reg_marital_status);
        txt_mat_reg_religion=findViewById(R.id.txt_mat_reg_religion);
        txt_mat_reg_mother_tongue=findViewById(R.id.txt_mat_reg_mother_tongue);
        txt_mat_reg_caste_devision=findViewById(R.id.txt_mat_reg_caste_devision);
        txt_mat_reg_physical_status=findViewById(R.id.txt_mat_reg_physical_status);
        txt_mat_reg_father_profession=findViewById(R.id.txt_mat_reg_father_profession);
        txt_mat_reg_mother_profession=findViewById(R.id.txt_mat_reg_mother_profession);
        txt_mat_reg_siaters=findViewById(R.id.txt_mat_reg_siaters);
        txt_mat_reg_brothers=findViewById(R.id.txt_mat_reg_brothers);
        txt_mat_reg_highest_study=findViewById(R.id.txt_mat_reg_highest_study);
        txt_mat_reg_profession=findViewById(R.id.txt_mat_reg_profession);
        txt_mat_reg_annual_income=findViewById(R.id.txt_mat_reg_annual_income);
        txt_mat_reg_country=findViewById(R.id.txt_mat_reg_country);
        txt_mat_reg_state=findViewById(R.id.txt_mat_reg_state);
        txt_mat_reg_district=findViewById(R.id.txt_mat_reg_district);
        txt_mat_reg_city=findViewById(R.id.txt_mat_reg_city);
        txt_mat_reg_rashi=findViewById(R.id.txt_mat_reg_rashi);
        txt_mat_reg_nakshatram=findViewById(R.id.txt_mat_reg_nakshatram);
        txt_mat_reg_gothra=findViewById(R.id.txt_mat_reg_gothra);
        txt_mat_reg_eating=findViewById(R.id.txt_mat_reg_eating);
        txt_mat_reg_drinking=findViewById(R.id.txt_mat_reg_drinking);
        txt_mat_reg_smoking=findViewById(R.id.txt_mat_reg_smoking);

        if (insertionData!=null){
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(this)
                    .load(insertionData.getImage())
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.ic_gallery)
                    .into(collapsingToolbarImageView);

            txt_mat_reg_name.setText(insertionData.getName());
            txt_mat_reg_surname.setText(insertionData.getSurname());
            txt_mat_reg_phone_num.setText(insertionData.getPhone_num());
            txt_mat_reg_gender.setText(insertionData.getGender());
            txt_mat_reg_age.setText(insertionData.getAge());
            txt_mat_reg_height.setText(insertionData.getHeight_in_feet()+" "+insertionData.getHeight_in_inch());
            txt_mat_reg_marital_status.setText(insertionData.getMarital_status());
            txt_mat_reg_religion.setText(insertionData.getReligion());
            txt_mat_reg_mother_tongue.setText(insertionData.getMother_tongue());
            txt_mat_reg_caste_devision.setText(insertionData.getCaste_division());
            txt_mat_reg_physical_status.setText(insertionData.getPhysical_staus());
            txt_mat_reg_father_profession.setText(insertionData.getFather_profession());
            txt_mat_reg_mother_profession.setText(insertionData.getMother_profession());
            txt_mat_reg_siaters.setText(insertionData.getSisters());
            txt_mat_reg_brothers.setText(insertionData.getBrothers());
            txt_mat_reg_highest_study.setText(insertionData.getStudy());
            txt_mat_reg_profession.setText(insertionData.getProfession());
            txt_mat_reg_annual_income.setText(insertionData.getAnnual_income());
            txt_mat_reg_country.setText(insertionData.getCountry());
            txt_mat_reg_state.setText(insertionData.getState());
            txt_mat_reg_district.setText(insertionData.getDistrict());
            txt_mat_reg_city.setText(insertionData.getCity());
            txt_mat_reg_rashi.setText(insertionData.getRashi());
            txt_mat_reg_nakshatram.setText(insertionData.getNakshatra());
            txt_mat_reg_gothra.setText(insertionData.getGothra());
            txt_mat_reg_eating.setText(insertionData.getEating());
            txt_mat_reg_drinking.setText(insertionData.getDrinking());
            txt_mat_reg_smoking.setText(insertionData.getSmoking());
        }
    }
}
