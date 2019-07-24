package com.Sairaa.onewel.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.Sairaa.onewel.Adapters.CustomerDetails_adapter;
import com.Sairaa.onewel.Adapters.PromoterListAdapter;
import com.Sairaa.onewel.Adapters.ReferenceListAdapter;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.Model.Advertisement.ADDVERTISER_PAYING_DETAILS;
import com.Sairaa.onewel.Model.Advertisement.AdvertisementDetails;
import com.Sairaa.onewel.Model.Customer.CUSTOMER_PAYING_DETAILS;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.bumptech.glide.Glide;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class ReferenceList extends BaseActivity {
    private String number,status;
    private Context context;
    private CardView profile_cardview;
    private ImageView advt_imageView;
    private TextView advt_shop_name,advt_shop_desc;
    private RecyclerView recyclerview_referenceList;
    private ImageView back;
    private TextView history;

    private ReferenceListAdapter adapter;
    private CustomerDetails_adapter customerDetails_adapter;
    private PromoterListAdapter promoterListAdapter;

    private ArrayList<ADDVERTISER_PAYING_DETAILS> paying_details_list=new ArrayList<>();
    private ArrayList<CUSTOMER_PAYING_DETAILS> paying_details_list1=new ArrayList<>();
    private ArrayList<String> referenceList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_list);

        context=ReferenceList.this;
        number=getIntent().getExtras().getString("number");
        status=getIntent().getExtras().getString("status");

        profile_cardview=findViewById(R.id.profile_cardview);
        advt_imageView=findViewById(R.id.advt_imageView);
        advt_shop_name=findViewById(R.id.advt_shop_name);
        advt_shop_desc=findViewById(R.id.advt_shop_desc);
        history=findViewById(R.id.history);
        back=findViewById(R.id.back);
        recyclerview_referenceList=findViewById(R.id.recyclerview_referenceList);

        if (status.contains("PROMOTER")){
            profile_cardview.setVisibility(View.GONE);
            history.setText("Promoter Reference List");
            getPromoterData();
        }
        else if (status.contains("ADVERTISER")){
            profile_cardview.setVisibility(View.VISIBLE);
            history.setText("Advertisement History");
            getAdvertiseProfileAndHistory();
        }
        else if (status.contains("CUSTOMER")){
            profile_cardview.setVisibility(View.GONE);
            history.setText("Customer History");
            getCustomerDetails();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getCustomerDetails() {
        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading..");
        getPayingDetails(Contants.CUSTOMER_PAYING_DETAILS,2);
    }

    private void getAdvertiseProfileAndHistory() {

        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading..");
        Query query=FirebaseDatabase.getInstance().getReference().child(status).child(number);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                        AdvertisementDetails details=new AdvertisementDetails();
                        details.setAddress(dataSnapshot.child("address").getValue().toString());
                        details.setCloses_to(dataSnapshot.child("closes_to").getValue().toString());
                        details.setImage_url(dataSnapshot.child("image_url").getValue().toString());
                        details.setLandmark(dataSnapshot.child("landmark").getValue().toString());
                        details.setLatitude(dataSnapshot.child("latitude").getValue().toString());
                        details.setLongitude(dataSnapshot.child("longitude").getValue().toString());
                        details.setOpens_from(dataSnapshot.child("opens_from").getValue().toString());
                        details.setPhone_num(dataSnapshot.child("phone_num").getValue().toString());
                        details.setRef_num(dataSnapshot.child("ref_num").getValue().toString());
                        details.setShop_desc(dataSnapshot.child("shop_desc").getValue().toString());
                        details.setShop_name(dataSnapshot.child("shop_name").getValue().toString());
                        details.setShop_type(dataSnapshot.child("shop_type").getValue().toString());


                    Glide.with(getApplicationContext())
                            .load(details.getImage_url())
                            .error(R.drawable.ic_gallery)
                            .into(advt_imageView);

                    advt_shop_name.setText(details.getShop_name());
                    advt_shop_desc.setText(details.getShop_desc());

                    getPayingDetails(Contants.ADDVERTISER_PAYING_DETAILS,1);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppUtils.dismissCustomProgress(mCustomProgressDialog);

            }
        };

        query.addValueEventListener(valueEventListener);
    }

    private void getPayingDetails(String addvertiserPayingDetails, final int i) {

        Query query=FirebaseDatabase.getInstance().getReference().child(addvertiserPayingDetails);

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    AppUtils.dismissCustomProgress(mCustomProgressDialog);
                    if (dataSnapshot.hasChild(number)) {
                        AppUtils.dismissCustomProgress(mCustomProgressDialog);
                        paying_details_list.clear();
                        paying_details_list1.clear();

                        if (i == 1) {
                            for (DataSnapshot payingDetails : dataSnapshot.child(number).getChildren()) {

                                ADDVERTISER_PAYING_DETAILS details = new ADDVERTISER_PAYING_DETAILS(
                                        payingDetails.child("custer_number").getValue().toString(),
                                        payingDetails.child("total_price").getValue().toString(),
                                        payingDetails.child("discount_price").getValue().toString(),
                                        payingDetails.child("description").getValue().toString(),
                                        payingDetails.child("date").getValue().toString(),
                                        payingDetails.child("time").getValue().toString()
                                );
                                paying_details_list.add(details);
                            }
                            adapter = new ReferenceListAdapter(context, paying_details_list);
                            recyclerview_referenceList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerview_referenceList.setHasFixedSize(true);
                            recyclerview_referenceList.setAdapter(adapter);
                        }
                         else if (i == 2) {
                            for (DataSnapshot payingDetails : dataSnapshot.child(number).getChildren()) {

                                Log.e("keys", payingDetails.getKey());

                                Log.e("totalPrice", payingDetails.child("totalPrice").getValue().toString());

                                CUSTOMER_PAYING_DETAILS details = new CUSTOMER_PAYING_DETAILS(
                                        payingDetails.child("name").getValue().toString(),
                                        payingDetails.child("totalPrice").getValue().toString(),
                                        payingDetails.child("discountPrice").getValue().toString(),
                                        payingDetails.child("description").getValue().toString(),
                                        payingDetails.child("date").getValue().toString(),
                                        payingDetails.child("time").getValue().toString()
                                );

                                paying_details_list1.add(details);


                            }

                            Log.e("list size", String.valueOf(paying_details_list.size()));
                            customerDetails_adapter = new CustomerDetails_adapter(context, paying_details_list1);
                            recyclerview_referenceList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerview_referenceList.setHasFixedSize(true);
                            recyclerview_referenceList.setAdapter(customerDetails_adapter);
                        }
                    }


                }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppUtils.dismissCustomProgress(mCustomProgressDialog);

            }
        };

        query.addValueEventListener(valueEventListener);
    }

    private void getPromoterData() {
        referenceList.clear();
        Query query= FirebaseDatabase.getInstance().getReference().child(Contants.REFERENCES);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.hasChild(number)){
                        for (DataSnapshot list:dataSnapshot.child(number).getChildren()){
                            // Log.e("keys",list.getKey());
                            String key=list.getKey();
//                            for (DataSnapshot dataSnapshot1:list.getChildren()){
                            String child=list.child("REFERRED").getValue().toString();
                            Log.e("child",child);

                            referenceList.add(child);

                            promoterListAdapter = new PromoterListAdapter(context, referenceList);
                            DividerItemDecoration itemDecoration=new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                            recyclerview_referenceList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerview_referenceList.setHasFixedSize(true);
                            recyclerview_referenceList.addItemDecoration(itemDecoration);
                            recyclerview_referenceList.setAdapter(promoterListAdapter);
//                            }
                        }
                    }
                    else {
                        AppUtils.showToast(context,"No References found");
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);
    }
}
