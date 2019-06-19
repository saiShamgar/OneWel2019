package com.Sairaa.onewel.Activities;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.Sairaa.onewel.Adapters.PlaceArrayAdapter;
import com.Sairaa.onewel.Adapters.SearchListAdapter;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.MainActivity;
import com.Sairaa.onewel.Model.Advertisement.AdvertisementDetails;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Queue;

public class SearchListActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private String address,item;
    private double lat,lon;
    private AutoCompleteTextView search_act_enter_items;
    private TextView search_act_buttonGo;
    private RecyclerView searchActivity_recyclerView;

    private ArrayList<AdvertisementDetails> details=new ArrayList<>();

    private SearchListAdapter adapter;
    private Context context;
    int distance = 0;
    float[] results = new float[3];

    private static final String LOG_TAG = "SearchListActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        context=SearchListActivity.this;

        address=getIntent().getExtras().getString("address");
        lat=getIntent().getExtras().getDouble("lat");
        lon=getIntent().getExtras().getDouble("lon");
        item=getIntent().getExtras().getString("item");

        Log.e("lat",String.valueOf(lat));
        Log.e("lon",String.valueOf(lon));

        mAutocompleteTextView=findViewById(R.id.search_act_Location);
        search_act_enter_items=findViewById(R.id.search_act_enter_items);
        search_act_buttonGo=findViewById(R.id.search_act_buttonGo);
        searchActivity_recyclerView=findViewById(R.id.searchActivity_recyclerView);

        search_act_enter_items.setText(item);
        mAutocompleteTextView.setText(address);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.shop_arrays));

        // textView = (AutoCompleteTextView) v.findViewById(R.id.txtViewNames);
        search_act_enter_items.setAdapter(arrayAdapter);
        search_act_enter_items.setThreshold(2);
        search_act_enter_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                search_act_enter_items.showDropDown();
            }
        });


        callService(item);

        search_act_buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    callService(search_act_enter_items.getText().toString());
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(SearchListActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            lat=place.getLatLng().latitude;
            lon=place.getLatLng().longitude;

            CharSequence attributions = places.getAttributions();

//            mNameTextView.setText(Html.fromHtml(place.getName() + ""));
//            mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
//            mIdTextView.setText(Html.fromHtml(place.getId() + ""));
//            mPhoneTextView.setText(Html.fromHtml(place.getPhoneNumber() + ""));
//            mWebTextView.setText(place.getWebsiteUri() + "");
            if (attributions != null) {
                mAutocompleteTextView.setText(Html.fromHtml(attributions.toString()));
            }
        }
    };

    private void callService(String item) {
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
                else {
                    AppUtils.dismissCustomProgress(mCustomProgressDialog);
                    AppUtils.showToast(context,"There are no shops");
                    details.clear();
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

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }
}
