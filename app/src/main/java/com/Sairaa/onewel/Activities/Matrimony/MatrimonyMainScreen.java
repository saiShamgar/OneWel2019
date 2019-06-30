package com.Sairaa.onewel.Activities.Matrimony;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.Sairaa.onewel.Adapters.MatrimonySearchAdapter;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.Model.MatrimonyInsertionData;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrimonyMainScreen extends BaseActivity {

    private TextView matrimony_reg_btn,matrimony_go;
    private AutoCompleteTextView matrimony_search_items;
    private List<String> list1 =new ArrayList<String>();
    private MatrimonySearchAdapter adapter;
    private RecyclerView mat_search_recycler_view;
    private ArrayList<MatrimonyInsertionData> searchList=new ArrayList<>();
    private Context context;
    private SharedPreferenceConfig config;
    private ImageView ic_matrimony_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_main_screen);
        context=MatrimonyMainScreen.this;
        config=new SharedPreferenceConfig(this);

        matrimony_reg_btn=findViewById(R.id.matrimony_reg_btn);
        matrimony_search_items=findViewById(R.id.matrimony_search_items);
        matrimony_go=findViewById(R.id.matrimony_go);
        mat_search_recycler_view=findViewById(R.id.mat_search_recycler_view);
        ic_matrimony_back_button=findViewById(R.id.ic_matrimony_back_button);

        ic_matrimony_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        matrimony_reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(MatrimonyMainScreen.this,MatrimonyRegistration.class);
                startActivity(register);
            }
        });


        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.india_states)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.AndhraPradesh)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Telangana)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.ArunachalPradesh)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Assam)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Bihar)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Chhattisgarh)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Goa)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Gujarat)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Haryana)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Himachal_Pradesh)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Jammu_Kashmir)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Jharkhand)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Karnataka)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Kerala)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Madhya_Pradesh)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Maharashtra)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Manipur)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Meghalaya)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Mizoram)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Nagaland)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Odisha)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Punjab)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Rajasthan)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Sikkim)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Tamil_Nadu)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Tripura)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Uttar_Pradesh)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.West_Bengal)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Andaman_Nicobar)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Chandigarh)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Dadra_Nagar_Haveli)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Daman_Diu)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Delhi)));
        list1.addAll(Arrays.asList(getResources().getStringArray(R.array.Puducherry)));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, R.layout.show_count,  list1);

        // textView = (AutoCompleteTextView) v.findViewById(R.id.txtViewNames);

        matrimony_search_items.setAdapter(arrayAdapter);
        matrimony_search_items.setThreshold(2);
        matrimony_search_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                matrimony_search_items.showDropDown();
            }
        });


        adapter=new MatrimonySearchAdapter(context,searchList,config.readUserUniqueKey(),mCustomProgressDialog);
        mat_search_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mat_search_recycler_view.setHasFixedSize(true);
        mat_search_recycler_view.setAdapter(adapter);

        matrimony_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtils.isNetworkAvailable(context)){
                    AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading");
                    Query query= FirebaseDatabase.getInstance().getReference().child(Contants.MATRIMONY);

                    ValueEventListener valueEventListener=new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                searchList.clear();
                                for (DataSnapshot listsd: dataSnapshot.getChildren()){
                                   Log.e("key",listsd.getKey());
                                    if ((listsd.child("state").getValue().toString().equalsIgnoreCase(matrimony_search_items.getText().toString())
                                            || listsd.child("district").getValue().toString().equalsIgnoreCase(matrimony_search_items.getText().toString()))
                                         && listsd.child("status").getValue().toString().equalsIgnoreCase("0")){
                                        AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                        MatrimonyInsertionData data=new MatrimonyInsertionData();
                                        data.setName(listsd.child("name").getValue().toString());
                                        data.setSurname(listsd.child("surname").getValue().toString());
                                        data.setPhone_num(listsd.child("phone_num").getValue().toString());
                                        data.setGender(listsd.child("gender").getValue().toString());
                                        data.setAge(listsd.child("age").getValue().toString());
                                        data.setHeight_in_feet(listsd.child("height_in_feet").getValue().toString());
                                        data.setHeight_in_inch(listsd.child("height_in_inch").getValue().toString());
                                        data.setMarital_status(listsd.child("marital_status").getValue().toString());
                                        data.setReligion(listsd.child("religion").getValue().toString());
                                        data.setMother_tongue(listsd.child("mother_tongue").getValue().toString());
                                        data.setCaste_division(listsd.child("caste_division").getValue().toString());
                                        data.setPhysical_staus(listsd.child("physical_staus").getValue().toString());
                                        data.setFather_profession(listsd.child("father_profession").getValue().toString());
                                        data.setMother_profession(listsd.child("mother_profession").getValue().toString());
                                        data.setSisters(listsd.child("sisters").getValue().toString());
                                        data.setBrothers(listsd.child("brothers").getValue().toString());
                                        data.setStudy(listsd.child("study").getValue().toString());
                                        data.setProfession(listsd.child("profession").getValue().toString());
                                        data.setAnnual_income(listsd.child("annual_income").getValue().toString());
                                        data.setCountry(listsd.child("country").getValue().toString());
                                        data.setState(listsd.child("state").getValue().toString());
                                        data.setDistrict(listsd.child("district").getValue().toString());
                                        data.setCity(listsd.child("city").getValue().toString());
                                        data.setRashi(listsd.child("rashi").getValue().toString());
                                        data.setNakshatra(listsd.child("nakshatra").getValue().toString());
                                        data.setGothra(listsd.child("gothra").getValue().toString());
                                        data.setEating(listsd.child("eating").getValue().toString());
                                        data.setDrinking(listsd.child("drinking").getValue().toString());
                                        data.setSmoking(listsd.child("smoking").getValue().toString());
                                        data.setImage(listsd.child("image").getValue().toString());
                                        searchList.add(data);
                                        adapter.notifyDataSetChanged();
                                    }
                                    else {
                                        AppUtils.dismissCustomProgress(mCustomProgressDialog);

                                    }

                                }
                            }else {
                                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                Log.e("key","not exists");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            AppUtils.dismissCustomProgress(mCustomProgressDialog);
                            AppUtils.showToast(context,databaseError.getMessage());
                        }
                    };

                    query.addValueEventListener(valueEventListener);
                }


            }
        });






    }
}
