package com.Sairaa.onewel;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.Sairaa.onewel.Activities.*;
import com.Sairaa.onewel.Activities.Add.AddSignUpPD;
import com.Sairaa.onewel.Activities.Customer.CustomerRegistration;
import com.Sairaa.onewel.Activities.Customer.CutomerVerification;
import com.Sairaa.onewel.Activities.Matrimony.CheckUserActivity;
import com.Sairaa.onewel.Activities.Matrimony.CloseAccount;
import com.Sairaa.onewel.Activities.Matrimony.MatrimonyMainScreen;
import com.Sairaa.onewel.Activities.Matrimony.MatrimonyRegistration;
import com.Sairaa.onewel.Activities.Promoter.PromoterSignUp;
import com.Sairaa.onewel.Adapters.PlaceArrayAdapter;
import com.Sairaa.onewel.Adapters.PlaceAutocompleteAdapter;
import com.Sairaa.onewel.Adapters.ViewAllAdapter;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    private CircleImageView img_ic_profile;
    private AutoCompleteTextView mSearchText,main_act_enter_items;
    private Context context;
    private TextView main_act_go,txt_matrimony;
    private ImageView img_ic_history,img_ic_all,navigatinButtonMainActivity,img_ic_tailor,img_ic_home,img_ic_beauty,img_ic_shoes,img_ic_stationary,img_ic_furniture,img_ic_electronics;

    boolean validate;

    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private double lat,lon;

    FirebaseAuth mAuth;
    private Toolbar toolBarLayout;
    private SharedPreferenceConfig config;
    private CircleImageView img_view_navigation;
    private TextView txt_profile_name,txt_profile_email,navigation_cus_reg_layout,navigation_cus_verfi_layout;
    private LinearLayout navigation_promoter_layout,navigation_advertisement_layout,navigation_matrimony_edit,navigation_matrimony_close
            ,navigation_about_us,navigation_privacy_policy,navigation_signout;
    private NavigationView nvView;
    private boolean statusShare=false;
    GoogleSignInClient mGoogleSignInClient;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        config=new SharedPreferenceConfig(this);
        requestMultiplePermissions();

        mAuth = FirebaseAuth.getInstance();
        img_ic_profile=findViewById(R.id.img_ic_profile);
        mAutocompleteTextView=findViewById(R.id.main_act_Location);
        main_act_enter_items=findViewById(R.id.main_act_enter_items);
        main_act_go=findViewById(R.id.main_act_go);
        img_ic_history=findViewById(R.id.img_ic_history);
        img_ic_all=findViewById(R.id.img_ic_all);

        img_ic_tailor=findViewById(R.id.img_ic_tailor);
        img_ic_beauty=findViewById(R.id.img_ic_beauty);
        img_ic_shoes=findViewById(R.id.img_ic_shoes);
        img_ic_stationary=findViewById(R.id.img_ic_stationary);
        img_ic_furniture=findViewById(R.id.img_ic_furniture);
        img_ic_home=findViewById(R.id.img_ic_home);
        img_ic_electronics=findViewById(R.id.img_ic_electronics);
        txt_matrimony=findViewById(R.id.txt_matrimony);
        toolBarLayout=findViewById(R.id.toolBarLayout);
        nvView=findViewById(R.id.nvView);


        setSupportActionBar(toolBarLayout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolBarLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View header = nvView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.txt_profile_name);
        TextView profile = (TextView) header.findViewById(R.id.txt_profile_email);
        img_view_navigation=header.findViewById(R.id.img_view_navigation);
        name.setText(config.readGoogle_name());
        profile.setText(config.readGoogle_email());

        nvView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });




        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.shop_arrays));

       // textView = (AutoCompleteTextView) v.findViewById(R.id.txtViewNames);
        main_act_enter_items.setAdapter(arrayAdapter);
        main_act_enter_items.setThreshold(2);
        main_act_enter_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                main_act_enter_items.showDropDown();
            }
        });

        Glide.with(this)
                .load(config.readGoogle_image())
                .error(R.drawable.ic_profile)
                .into(img_ic_profile);

        Glide.with(this)
                .load(config.readGoogle_image())
                .error(R.drawable.ic_profile)
                .into(img_view_navigation);

        main_act_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()){
                    Intent search=new Intent(MainActivity.this, SearchListActivity.class);
                    search.putExtra("address",mAutocompleteTextView.getText().toString().trim());
                    search.putExtra("lat",lat);
                    search.putExtra("lon",lon);
                    search.putExtra("item",main_act_enter_items.getText().toString());
                    startActivity(search);
                }

            }
        });


        img_ic_tailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, AddSignUpPD.class);
                viewAll.putExtra("position",8);
                startActivity(viewAll);
            }
        });

        img_ic_beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, AddSignUpPD.class);
                viewAll.putExtra("position",9);
                startActivity(viewAll);
            }
        });

        img_ic_shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, AddSignUpPD.class);
                viewAll.putExtra("position",10);
                startActivity(viewAll);
            }
        });

        img_ic_stationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, AddSignUpPD.class);
                viewAll.putExtra("position",11);
                startActivity(viewAll);
            }
        });

        img_ic_furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, AddSignUpPD.class);
                viewAll.putExtra("position",12);
                startActivity(viewAll);
            }
        });
        img_ic_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, AddSignUpPD.class);
                viewAll.putExtra("position",13);
                startActivity(viewAll);
            }
        });

        img_ic_electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, AddSignUpPD.class);
                viewAll.putExtra("position",1);
                startActivity(viewAll);
            }
        });

        img_ic_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, ViewAllActivity.class);
                startActivity(viewAll);
            }
        });

        txt_matrimony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAll=new Intent(MainActivity.this, MatrimonyMainScreen.class);
                startActivity(viewAll);

            }
        });




        img_ic_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showCustomDialog(context, null);
            }
        });
        img_ic_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userHistory=new Intent(MainActivity.this, UserHistory.class);
                startActivity(userHistory);

            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
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

    private void selectDrawerItem(MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.nav_promoter:
                Intent promoter=new Intent(MainActivity.this,PromoterSignUp.class);
                startActivity(promoter);
                break;
            case R.id.nav_advertiser:
                Intent add=new Intent(MainActivity.this,AddSignUpPD.class);
                add.putExtra("position",0);
                startActivity(add);
                break;
            case R.id.customer_reg:
                Intent cus=new Intent(MainActivity.this,CustomerRegistration.class);
                startActivity(cus);
                break;

            case R.id.customer_verfi:
                Intent ver=new Intent(MainActivity.this,CutomerVerification.class);
                startActivity(ver);
                break;

            case R.id.matrimony_edit:
              Intent checkUser=new Intent(MainActivity.this, CheckUserActivity.class);
              startActivity(checkUser);
                break;

            case R.id.matrimony_close:
                Intent closeAccount=new Intent(MainActivity.this, CloseAccount.class);
                startActivity(closeAccount);

                break;

            case R.id.nav_share_app:
                ShareApp();
                break;

            case R.id.log_out:
                mAuth.signOut();
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent logout=new Intent(MainActivity.this,GoogleSignIN.class);
                            logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(logout);
                        }
                    }
                });

                break;

            case R.id.about_us:
                Intent about_us=new Intent(MainActivity.this, AboutUs.class);
                startActivity(about_us);
                break;

            case R.id.privacy_policy:
                Intent privacy_policy=new Intent(MainActivity.this, PrivacyPolicy.class);
                startActivity(privacy_policy);
                break;

        }

    }

    private void ShareApp() {
        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "Play store Link";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
           // waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    boolean doValidation() {
        validate = true;

        if (mAutocompleteTextView.getText().toString().trim().length() == 0) {
            validate = false;
            mAutocompleteTextView.setError("Enter Location");
            mAutocompleteTextView.requestFocus();

        }
       else if (mAutocompleteTextView.getText().toString().trim().length() > 0) {
           if (lat==0){
               validate = false;
               mAutocompleteTextView.setError("Please select location");
               mAutocompleteTextView.requestFocus();
           }
        }else if (main_act_enter_items.getText().toString().trim().length() == 0 ) {
            validate = false;
            main_act_enter_items.requestFocus();
            main_act_enter_items.setError("Enter correct Phone number");

        }
        return validate;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this)
                .load(config.readGoogle_image())
                .error(R.drawable.ic_profile)
                .into(img_ic_profile);

        Glide.with(this)
                .load(config.readGoogle_image())
                .error(R.drawable.ic_profile)
                .into(img_view_navigation);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
           // finish();
            mAuth.signOut();
            startActivity(new Intent(this, GoogleSignIN.class));

        }
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

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                          //  Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}



