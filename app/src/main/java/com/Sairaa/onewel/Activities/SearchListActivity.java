package com.Sairaa.onewel.Activities;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import com.Sairaa.onewel.Adapters.SearchListAdapter;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.Model.Advertisement.AdvertisementDetails;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Queue;

public class SearchListActivity extends BaseActivity {

    private String address,item;
    private double lat,lon;
    private AutoCompleteTextView search_act_Location;
    private EditText search_act_enter_items;
    private TextView search_act_buttonGo;
    private RecyclerView searchActivity_recyclerView;

    private ArrayList<AdvertisementDetails> details=new ArrayList<>();

    private SearchListAdapter adapter;
    int distance = 0;
    float[] results = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        address=getIntent().getExtras().getString("address");
        lat=getIntent().getExtras().getDouble("lat");
        lon=getIntent().getExtras().getDouble("lon");
        item=getIntent().getExtras().getString("item");

        Log.e("lat",String.valueOf(lat));
        Log.e("lon",String.valueOf(lon));

        search_act_Location=findViewById(R.id.search_act_Location);
        search_act_enter_items=findViewById(R.id.search_act_enter_items);
        search_act_buttonGo=findViewById(R.id.search_act_buttonGo);
        searchActivity_recyclerView=findViewById(R.id.searchActivity_recyclerView);

        search_act_enter_items.setText(item);
        search_act_Location.setText(address);

        callService();

        search_act_buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void callService() {
        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading...");
        Query query= FirebaseDatabase.getInstance().getReference().child(Contants.ADVERTISER)
                .orderByChild("shop_type").equalTo(item);
        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    details.clear();

                    for (DataSnapshot list:dataSnapshot.getChildren()){
                        Log.e("data",list.child("phone_num").getValue().toString());
                        double nearLat= Double.parseDouble(list.child("latitude").getValue().toString());
                        double nearLon=Double.parseDouble(list.child("longitude").getValue().toString());

                        if (lat!=0){
                            Location.distanceBetween(lat,lon, nearLat,nearLon,results);
                            Log.e("distance",String.valueOf(results[0]/1000));

                            if (results[0]/1000<20){
                                AdvertisementDetails list5=new AdvertisementDetails();
                                list5.setAddress(list.child("address").getValue().toString());
                                list5.setCloses_to(list.child("closes_to").getValue().toString());
                                list5.setImage_url(list.child("image_url").getValue().toString());
                                list5.setLandmark(list.child("landmark").getValue().toString());
                                list5.setLatitude(list.child("latitude").getValue().toString());
                                list5.setLongitude(list.child("longitude").getValue().toString());
                                list5.setOpens_from(list.child("opens_from").getValue().toString());
                                list5.setPhone_num(list.child("phone_num").getValue().toString());
                                list5.setRef_num(list.child("ref_num").getValue().toString());
                                list5.setShop_desc(list.child("shop_desc").getValue().toString());
                                list5.setShop_name(list.child("shop_name").getValue().toString());
                                list5.setShop_type(list.child("shop_type").getValue().toString());

                                details.add(list5);
                            }


                        }


                    }
                    AppUtils.dismissCustomProgress(mCustomProgressDialog);
                    adapter=new SearchListAdapter(getApplicationContext(),details);
                    searchActivity_recyclerView.setHasFixedSize(true);
                    searchActivity_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    searchActivity_recyclerView.setAdapter(adapter);



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error",databaseError.toString());
                AppUtils.dismissCustomProgress(mCustomProgressDialog);

            }
        };
        query.addValueEventListener(eventListener);
    }
}
