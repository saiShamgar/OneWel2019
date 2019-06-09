package com.Sairaa.onewel.Activities.Promoter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.Sairaa.onewel.Adapters.PlaceArrayAdapter;
import com.Sairaa.onewel.MainActivity;
import com.Sairaa.onewel.Model.promoter.PromoterPersonalDetails;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class PromoterSignUp extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private Button btn_next_promoter_details;
    private EditText edt_name_promoter,edt_phone_num_promoter,edt_ref_num_promoter;
    private AutoCompleteTextView edt_address_promoter;

    private static final String LOG_TAG = "PromoterSignUp";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private double lat,lon;

    private boolean validate;

    private Context mContext;
    private SharedPreferenceConfig config;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_sign_up);

        mContext=PromoterSignUp.this;
        config=new SharedPreferenceConfig(this);

        btn_next_promoter_details=findViewById(R.id.btn_next_promoter_details);
        edt_name_promoter=findViewById(R.id.edt_name_promoter);
        edt_phone_num_promoter=findViewById(R.id.edt_phone_num_promoter);
        edt_ref_num_promoter=findViewById(R.id.edt_ref_num_promoter);
        edt_address_promoter=findViewById(R.id.edt_address_promoter);

        btn_next_promoter_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()){
                    savePromoterPersonalDetails();
                }

            }
        });


        //places api
        mGoogleApiClient = new GoogleApiClient.Builder(PromoterSignUp.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        edt_address_promoter.setThreshold(3);
        edt_address_promoter.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        edt_address_promoter.setAdapter(mPlaceArrayAdapter);
    }

    private void savePromoterPersonalDetails() {

        config.writePromoterName(edt_name_promoter.getText().toString().trim());
        config.writePromoterPhone(edt_phone_num_promoter.getText().toString().trim());
        config.writePromoterREF_num(edt_ref_num_promoter.getText().toString().trim());
        config.writePromoterAddress(edt_address_promoter.getText().toString().trim());
        config.writePromoterLat(String.valueOf(lat));
        config.writePromoterLon(String.valueOf(lon));

        Intent gotoUpi=new Intent(PromoterSignUp.this,PromoterUPI.class);
                            startActivity(gotoUpi);
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
                edt_address_promoter.setText(Html.fromHtml(attributions.toString()));
            }
        }
    };

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

    boolean doValidation() {
        validate = true;

        if (edt_name_promoter.getText().toString().trim().length() == 0) {
            validate = false;
            edt_name_promoter.setError("Enter Name");
            edt_name_promoter.requestFocus();

        } else if (edt_phone_num_promoter.getText().toString().trim().length() != 10 ) {
            validate = false;
            edt_phone_num_promoter.requestFocus();
            edt_phone_num_promoter.setError("Enter correct Phone number");

        } else if (edt_address_promoter.getText().toString().trim().length() == 0) {
            validate = false;
            edt_address_promoter.requestFocus();
            edt_address_promoter.setError("Enter Address");

        } else if (edt_address_promoter.getText().toString().trim().length() > 0) {
            if (lat==0){
                validate = false;
                edt_address_promoter.requestFocus();
                edt_address_promoter.setError("Enter correct Address");
            }
        }else if (edt_ref_num_promoter.getText().toString().trim().length()>0){
             if (edt_ref_num_promoter.getText().toString().trim().length() != 10 ) {
                validate = false;
                 edt_ref_num_promoter.requestFocus();
                 edt_ref_num_promoter.setError("Enter correct Ref number");

            }

        }

        return validate;
    }
}
