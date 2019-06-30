package com.Sairaa.onewel.Activities.Matrimony;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.Sairaa.onewel.Activities.OtpActivity;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.Model.MatrimonyInsertionData;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MatrimonyRegistration extends BaseActivity  {

    private EditText edt_mat_reg_name,edt_mat_reg_surname,edt_mat_reg_phone_num,edt_mat_reg_city,edt_mat_reg_gothra;
    private RadioGroup edt_mat_reg_gender,edt_mat_reg_marital_status,edt_mat_reg_physical_status,edt_mat_reg_eating,edt_mat_reg_drinking,edt_mat_reg_smoking;
    private Spinner edt_mat_reg_age,edt_mat_reg_height_in_feet,edt_mat_reg_heightin_inch,edt_mat_reg_religion,edt_mat_reg_mother_tongue,
             edt_mat_reg_caste_devision,edt_mat_reg_highest_study,edt_mat_reg_annual_income,edt_mat_reg_country
            ,edt_mat_reg_state,edt_mat_reg_district,edt_mat_reg_rashi,edt_mat_reg_star;
    private AutoCompleteTextView edt_mat_reg_occupation,edt_mat_reg_father_occupation,edt_mat_reg_mother_profession;
    private EditText edt_mat_reg_sisters,edt_mat_reg_brothers;
    private Button saveMatrimonyDetails;
    private TextView txt_add_matrimony_image;
    private ImageView img_matrimony_image;
    private RadioButton gender_male,gender_female,radio_single,radio_divorced,radio_widowed,
            radio_separated,radio_normal,radio_physically_cahllenged,radio_all,radio_veg,radio_non_veg,radio_non_drinker
            ,radio_light_drinker,radio_heavy_drinker,radio_non_smoker,radio_light_smoker,radio_heavy_smoker;

    private Context context;
    private String religion,motherTongue,state,age,height_in_feet,height_in_inch,
            caste,study,income,country,district,rashi,nakshitram;
    private boolean validate;

    final static int REQUEST_TAKE_PHOTO_FILE = 2;
    String mCurrentPhotoPath;
    private Uri imagecaptureuri;
    private Bitmap bitmap;

    private SharedPreferenceConfig config;
    private ImageView back_matrimony_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_registration);

        context=MatrimonyRegistration.this;
        config=new SharedPreferenceConfig(this);

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
        saveMatrimonyDetails=findViewById(R.id.saveMatrimonyDetails);
        gender_male=findViewById(R.id.gender_male);
        gender_female=findViewById(R.id.gender_female);
        radio_single=findViewById(R.id.radio_single);
        radio_divorced=findViewById(R.id.radio_divorced);
        radio_widowed=findViewById(R.id.radio_widowed);
        radio_separated=findViewById(R.id.radio_separated);
        radio_normal=findViewById(R.id.radio_normal);
        radio_physically_cahllenged=findViewById(R.id.radio_physically_cahllenged);
        radio_non_drinker=findViewById(R.id.radio_non_drinker);
        radio_light_drinker=findViewById(R.id.radio_light_drinker);
        radio_heavy_drinker=findViewById(R.id.radio_heavy_drinker);
        radio_all=findViewById(R.id.radio_all);
        radio_non_veg=findViewById(R.id.radio_non_veg);
        radio_veg=findViewById(R.id.radio_veg);
        radio_non_smoker=findViewById(R.id.radio_non_smoker);
        radio_light_smoker=findViewById(R.id.radio_light_smoker);
        radio_heavy_smoker=findViewById(R.id.radio_heavy_smoker);
        edt_mat_reg_father_occupation=findViewById(R.id.edt_mat_reg_father_occupation);
        edt_mat_reg_mother_profession=findViewById(R.id.edt_mat_reg_mother_profession);
        edt_mat_reg_sisters=findViewById(R.id.edt_mat_reg_sisters);
        edt_mat_reg_brothers=findViewById(R.id.edt_mat_reg_brothers);
        txt_add_matrimony_image=findViewById(R.id.txt_add_matrimony_image);
        img_matrimony_image=findViewById(R.id.img_matrimony_image);
        back_matrimony_reg=findViewById(R.id.back_matrimony_reg);

        back_matrimony_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_add_matrimony_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchCameraIntent();
            }
        });

        SetAdapter(edt_mat_reg_religion,getResources().getStringArray(R.array.religion_array));
        SetAdapter(edt_mat_reg_mother_tongue,getResources().getStringArray(R.array.mother_tongue_hindu));
        SetAdapter(edt_mat_reg_country,getResources().getStringArray(R.array.countries));
        SetAdapter(edt_mat_reg_state,getResources().getStringArray(R.array.india_states));
        SetAdapter(edt_mat_reg_rashi,getResources().getStringArray(R.array.Rashi));
        SetAdapter(edt_mat_reg_star,getResources().getStringArray(R.array.Nakshatras));
        SetAdapter(edt_mat_reg_age,getResources().getStringArray(R.array.age_array));
        SetAdapter(edt_mat_reg_height_in_feet,getResources().getStringArray(R.array.feet_array));
        SetAdapter(edt_mat_reg_heightin_inch,getResources().getStringArray(R.array.inches_array));
        SetAdapter(edt_mat_reg_highest_study,getResources().getStringArray(R.array.qualification));
        SetAdapter(edt_mat_reg_annual_income,getResources().getStringArray(R.array.annual_income));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, R.layout.show_count,
                getResources().getStringArray(R.array.profission_array));

        // textView = (AutoCompleteTextView) v.findViewById(R.id.txtViewNames);
        edt_mat_reg_occupation.setAdapter(arrayAdapter);
        edt_mat_reg_occupation.setThreshold(2);
        edt_mat_reg_occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                edt_mat_reg_occupation.showDropDown();
            }
        });

        edt_mat_reg_father_occupation.setAdapter(arrayAdapter);
        edt_mat_reg_father_occupation.setThreshold(2);
        edt_mat_reg_father_occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                edt_mat_reg_father_occupation.showDropDown();
            }
        });

        edt_mat_reg_mother_profession.setAdapter(arrayAdapter);
        edt_mat_reg_mother_profession.setThreshold(2);
        edt_mat_reg_mother_profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                edt_mat_reg_mother_profession.showDropDown();
            }
        });

        saveMatrimonyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doValidation()){
                    MatrimonyInsertionData insertionData=new MatrimonyInsertionData();
                    insertionData.setName(edt_mat_reg_name.getText().toString());
                    insertionData.setSurname(edt_mat_reg_surname.getText().toString());
                    insertionData.setPhone_num(edt_mat_reg_phone_num.getText().toString());
                    insertionData.setGender(radioGroupSelectedText(edt_mat_reg_gender));
                    insertionData.setAge(age);
                    insertionData.setHeight_in_feet(height_in_feet);
                    insertionData.setHeight_in_inch(height_in_inch);
                    insertionData.setMarital_status(radioGroupSelectedText(edt_mat_reg_marital_status));
                    insertionData.setReligion(religion);
                    insertionData.setMother_tongue(motherTongue);
                    insertionData.setCaste_division(caste);
                    insertionData.setPhysical_staus(radioGroupSelectedText(edt_mat_reg_physical_status));
                    insertionData.setFather_profession(edt_mat_reg_father_occupation.getText().toString());
                    insertionData.setMother_profession(edt_mat_reg_mother_profession.getText().toString());
                    insertionData.setSisters(edt_mat_reg_sisters.getText().toString());
                    insertionData.setBrothers(edt_mat_reg_brothers.getText().toString());
                    insertionData.setStudy(study);
                    insertionData.setProfession(edt_mat_reg_occupation.getText().toString());
                    insertionData.setAnnual_income(income);
                    insertionData.setCountry(country);
                    insertionData.setState(state);
                    insertionData.setDistrict(district);
                    insertionData.setCity(edt_mat_reg_city.getText().toString());
                    insertionData.setRashi(rashi);
                    insertionData.setNakshatra(nakshitram);
                    insertionData.setGothra(edt_mat_reg_gothra.getText().toString());
                    insertionData.setEating(radioGroupSelectedText(edt_mat_reg_eating));
                    insertionData.setDrinking(radioGroupSelectedText(edt_mat_reg_drinking));
                    insertionData.setSmoking(radioGroupSelectedText(edt_mat_reg_smoking));
                    insertionData.setStatus("0");
                    config.writeMatrimonyImage(imageToString(bitmap));

                    Intent otpActivity=new Intent(MatrimonyRegistration.this, OtpActivity.class);
                    otpActivity.putExtra("status","Matrimony");
                    otpActivity.putExtra("reg_data",insertionData);
                    otpActivity.putExtra("number",edt_mat_reg_phone_num.getText().toString());
                    startActivityForResult(otpActivity,100);

                }
            }
        });

        edt_mat_reg_rashi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rashi=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_mat_reg_star.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nakshitram=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_mat_reg_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_mat_reg_annual_income.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                income=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edt_mat_reg_highest_study.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                study=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_mat_reg_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                age=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_mat_reg_height_in_feet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                height_in_feet=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_mat_reg_heightin_inch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                height_in_inch=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edt_mat_reg_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edt_mat_reg_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               state=parent.getSelectedItem().toString();
               if (state.contains("Andhra Pradesh")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.AndhraPradesh));
               }
               else if (state.contains("Arunachal Pradesh")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.ArunachalPradesh));
               }
               else if (state.contains("Assam")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Assam));
               }
               else if (state.contains("Bihar")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Bihar));
               }
               else if (state.contains("Chandigarh")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Chandigarh));
               }
               else if (state.contains("Chhattisgarh")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Chhattisgarh));
               }
               else if (state.contains("Delhi")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Delhi));
               }
               else if (state.contains("Andaman and Nicobar Islands")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Andaman_Nicobar));
               }
               else if (state.contains("Dadra and Nagar Haveli")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Dadra_Nagar_Haveli));
               }
               else if (state.contains("Daman and Diu")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Daman_Diu));
               }
               else if (state.contains("Goa")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Goa));
               }
               else if (state.contains("Gujarat")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Gujarat));
               }
               else if (state.contains("Haryana")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Haryana));
               }
               else if (state.contains("Himachal Pradesh")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Himachal_Pradesh));
               }
               else if (state.contains("Jammu and Kashmir")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Jammu_Kashmir));
               }
               else if (state.contains("Jharkhand")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Jharkhand));
               }
               else if (state.contains("Karnataka")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Karnataka));
               }
               else if (state.contains("Kerala")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Kerala));
               }
               else if (state.contains("Lakshadweep")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Lakshadweep));
               }
               else if (state.contains("Madhya Pradesh")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Madhya_Pradesh));
               }
               else if (state.contains("Maharashtra")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Maharashtra));
               }
               else if (state.contains("Manipur")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Manipur));
               }
               else if (state.contains("Meghalaya")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Meghalaya));
               }
               else if (state.contains("Mizoram")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Mizoram));
               }
               else if (state.contains("Nagaland")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Nagaland));
               }
               else if (state.contains("Orissa")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Odisha));
               }
               else if (state.contains("Pondicherry")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Puducherry));
               }
               else if (state.contains("Punjab")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Punjab));
               }
               else if (state.contains("Rajasthan")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Rajasthan));
               }
               else if (state.contains("Sikkim")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Sikkim));
               }
               else if (state.contains("Tamil Nadu")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Tamil_Nadu));
               }
               else if (state.contains("Telangana")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Telangana));
               }
               else if (state.contains("Tripura")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Tripura));
               }
               else if (state.contains("Uttaranchal")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Uttarakhand));
               }
               else if (state.contains("Uttar Pradesh")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.Uttar_Pradesh));
               }
               else if (state.contains("West Bengal")){
                   SetAdapter(edt_mat_reg_district,getResources().getStringArray(R.array.West_Bengal));
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edt_mat_reg_religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                religion=parent.getSelectedItem().toString();
                if (religion.contains("Musilm-shia")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.musilim_castes));
                }
                else if (religion.contains("Musilm-sunni")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.musilim_castes));
                }
                else if (religion.contains("Musilm-others")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.musilim_castes));
                }
                else if (religion.contains("Christian - Catholic")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.christian_castes));
                }
                else if (religion.contains("Christian - Orthodox")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.christian_castes));
                }
                else if (religion.contains("Christian - Protestant")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.christian_castes));
                }
                else if (religion.contains("Christian - Others")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.christian_castes));
                }
                else if (religion.contains("Sikh")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.sikh_caste));
                }
                else if (religion.contains("Jain - Digambar")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.jain_castes));
                }
                else if (religion.contains("Jain - Shwetambar")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.jain_castes));
                }
                else if (religion.contains("Jain - Others")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.jain_castes));
                }
                else if (religion.contains("Parsi")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.parsi_castes));
                }
                else if (religion.contains("Buddhist")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.buddhist_castes));
                }
                else if (religion.contains("No Religious Belief")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.buddhist_castes));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edt_mat_reg_mother_tongue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                motherTongue=parent.getSelectedItem().toString();
                if (religion.contains("Hindu") && motherTongue.contains("Telugu")){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.telugu_hindu_castes));
                }
                else if (religion.contains("Hindu") && motherTongue.contains("Hindi") ){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.hindi_hindu_castes));
                }
                else if (religion.contains("Hindu") && motherTongue.contains("Kannada") ){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.kannada_castes));
                }
                else if (religion.contains("Hindu") && motherTongue.contains("Tamil") ){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.tamil_castes));
                }
                else if (religion.contains("Hindu") && motherTongue.contains("Urdu") ){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.hindi_hindu_castes));
                }
                else if (religion.contains("Hindu") && motherTongue.contains("Bengali") ){
                    SetAdapter(edt_mat_reg_caste_devision,getResources().getStringArray(R.array.bengali_castes));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_mat_reg_caste_devision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                caste=adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //radioGroupSelectedText(edt_mat_reg_gender);
    }
    private void dispatchCameraIntent() {
        //getting images
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "select file"), REQUEST_TAKE_PHOTO_FILE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO_FILE:
                    imagecaptureuri=intent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagecaptureuri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(this)
                            .load(imagecaptureuri)
                            .into(img_matrimony_image);

            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
    boolean doValidation() {
        validate = true;

        if (bitmap==null){
            validate = false;
            AppUtils.showToast(context,"Please Add Your Image");
        }

       else if (edt_mat_reg_name.getText().toString().trim().length() == 0) {
            validate = false;
            edt_mat_reg_name.setError("Enter Name");
            edt_mat_reg_name.requestFocus();

        }else if (edt_mat_reg_phone_num.getText().toString().trim().length() != 10 ) {
            validate = false;
            edt_mat_reg_phone_num.requestFocus();
            edt_mat_reg_phone_num.setError("Enter correct Phone number");

        }else if (edt_mat_reg_surname.getText().toString().trim().length() == 0) {
            validate = false;
            edt_mat_reg_surname.requestFocus();
            edt_mat_reg_surname.setError("Enter Address");

        }else if (!gender_male.isChecked() && !gender_female.isChecked()){
            validate = false;
            AppUtils.showToast(context,"Please select gender");
        }else if (age.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select age");
        } else if (height_in_feet.equalsIgnoreCase("Feet")){
            validate = false;
            AppUtils.showToast(context,"Please select Height in Feet");
        } else if (height_in_inch.equalsIgnoreCase("Inch")){
            validate = false;
            AppUtils.showToast(context,"Please select Height in Inch");
        }else if (!radio_single.isChecked() && !radio_divorced.isChecked() && !radio_separated.isChecked() && !radio_widowed.isChecked()){
            validate = false;
            AppUtils.showToast(context,"Please select Marital status");
        }else if (religion.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Religion");
        }else if (motherTongue.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Mother Tongue");
        }else if (caste.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Caste");
        }else if (!radio_normal.isChecked() && !radio_physically_cahllenged.isChecked()){
            validate = false;
            AppUtils.showToast(context,"Please select Physical Status");
        }else if (edt_mat_reg_father_occupation.getText().toString().length()==0){
            validate=false;
            edt_mat_reg_father_occupation.requestFocus();
            edt_mat_reg_father_occupation.setError("Enter Father Profession");
        }else if (edt_mat_reg_mother_profession.getText().toString().length()==0){
            validate=false;
            edt_mat_reg_mother_profession.requestFocus();
            edt_mat_reg_mother_profession.setError("Enter Mother Profession");
        }else if (edt_mat_reg_sisters.getText().toString().length()==0){
            validate=false;
            edt_mat_reg_sisters.requestFocus();
            edt_mat_reg_sisters.setError("Enter No of Sisters");
        }else if (edt_mat_reg_brothers.getText().toString().length()==0){
            validate=false;
            edt_mat_reg_brothers.requestFocus();
            edt_mat_reg_brothers.setError("Enter No of Brothers");
        }else if (study.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Qualification");
        }else if (edt_mat_reg_occupation.getText().toString().length()==0){
            validate = false;
            edt_mat_reg_occupation.requestFocus();
            edt_mat_reg_occupation.setError("Enter Your Profession");
        }else if (income.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Annual Income");
        }else if (state.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Your State");
        }else if (district.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Your District");
        }else if (edt_mat_reg_city.getText().toString().length()==0){
            validate = false;
            edt_mat_reg_city.requestFocus();
            edt_mat_reg_city.setError("Enter City/Area");
        }else if (rashi.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Your Rashi");
        }else if (nakshitram.contains("- Select -")){
            validate = false;
            AppUtils.showToast(context,"Please select Your Nakshatram");
        }else if (edt_mat_reg_gothra.getText().toString().trim().length() == 0) {
            validate = false;
            edt_mat_reg_gothra.requestFocus();
            edt_mat_reg_gothra.setError("Enter Gothram");

        }else if (!radio_all.isChecked() && !radio_non_veg.isChecked() && !radio_veg.isChecked()){
            validate = false;
            AppUtils.showToast(context,"Please select Your Eating Habit");
        }else if (!radio_non_drinker.isChecked() && !radio_light_drinker.isChecked() && !radio_heavy_drinker.isChecked()){
            validate = false;
            AppUtils.showToast(context,"Please select Your Drinking Habit");
        }else if (!radio_non_smoker.isChecked() && !radio_light_smoker.isChecked() && !radio_heavy_smoker.isChecked()){
            validate = false;
            AppUtils.showToast(context,"Please select Your Smoking Habit");
        }

        return validate;
    }
    private String radioGroupSelectedText(RadioGroup id){
        int radioButtonID = id.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) id.findViewById(radioButtonID);
        String selectedtext = (String) radioButton.getText();
        Log.e("selectedtext",selectedtext);

        return selectedtext;
    }


    private void SetAdapter(Spinner edt_mat_reg_caste_devision, String[] stringArray){
       ArrayAdapter countAdapter = new ArrayAdapter<String>(context, R.layout.show_count, stringArray);
        edt_mat_reg_caste_devision.setAdapter(countAdapter);
    }

    //upload images
    public String  imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        byte[] imgbyte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte,Base64.DEFAULT);
    }
}
