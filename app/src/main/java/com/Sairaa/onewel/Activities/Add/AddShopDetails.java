package com.Sairaa.onewel.Activities.Add;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.Sairaa.onewel.Activities.OtpActivity;
import com.Sairaa.onewel.Activities.Promoter.PromoterRegistrationSuccess;
import com.Sairaa.onewel.Activities.Promoter.PromoterSignUp;
import com.Sairaa.onewel.Activities.Promoter.PromoterUPI;
import com.Sairaa.onewel.Adapters.PlaceArrayAdapter;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddShopDetails extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private Button btn_next_add_shop_details;
    private AutoCompleteTextView edt_name_add_shop_details;
    private EditText edt_landmark_add_shop_details,edt_shop_description_add;
    private Spinner spin_add_from_time,spin_add_to_time;
    private ImageView shop_image;
    private Button Add_image;

    private String fromTime,toTime;
    private boolean validate;
    private double lat,lon;

    private SharedPreferenceConfig config;

    private static final String LOG_TAG = "AddShopDetails";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    final static int REQUEST_TAKE_PHOTO = 1;
    final static int REQUEST_TAKE_PHOTO_FILE = 2;
    String mCurrentPhotoPath;
    private Bitmap bitmap;
    private Context context;
    private AlertDialog dialog;
    private Uri imagecaptureuri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_details);

        context=AddShopDetails.this;

        config=new SharedPreferenceConfig(this);
        btn_next_add_shop_details=findViewById(R.id.btn_next_add_shop_details);
        edt_name_add_shop_details=findViewById(R.id.edt_name_add_shop_details);
        edt_landmark_add_shop_details=findViewById(R.id.edt_landmark_add_shop_details);
        edt_shop_description_add=findViewById(R.id.edt_shop_description_add);
        spin_add_from_time=findViewById(R.id.spin_add_from_time);
        spin_add_to_time=findViewById(R.id.spin_add_to_time);
        Add_image=findViewById(R.id.Add_image);
        shop_image=findViewById(R.id.shop_image);


        spin_add_from_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               fromTime=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_add_to_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toTime=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_next_add_shop_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (doValidation()){
                    saveShopDetails();
                }

            }
        });

        //places api
        mGoogleApiClient = new GoogleApiClient.Builder(AddShopDetails.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        edt_name_add_shop_details.setThreshold(3);
        edt_name_add_shop_details.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        edt_name_add_shop_details.setAdapter(mPlaceArrayAdapter);

        Add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchCameraIntent();
            }
        });
    }

    private void dispatchCameraIntent() {
        //getting images
        final String[] items = new String[]{"from camera", "from sd card"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    try {
                        captureImage();
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Ensure that there's a camera activity to handle the intent
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                // Error occurred while creating the File

                            }
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                        "com.Sairaa.onewel.fileprovider",
                                        photoFile);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (i == 1) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "select file"), REQUEST_TAKE_PHOTO_FILE);
                }
                dialogInterface.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();



    }

    //image uploading code
    private void captureImage()   {
        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_TAKE_PHOTO);
            }
            else {
                // Open your camera here.
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                         bitmap = MediaStore.Images.Media
                                .getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            shop_image.setImageBitmap(bitmap);
                        }
                    }
                    break;

                case REQUEST_TAKE_PHOTO_FILE:
                    imagecaptureuri=intent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagecaptureuri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(this)
                            .load(imagecaptureuri)
                            .into(shop_image);

            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        Log.e("uri",mCurrentPhotoPath);
        return image;
    }

    private void saveShopDetails() {

        config.writeAdvertiserAddress(edt_name_add_shop_details.getText().toString().trim());
        config.writeAdvertiserLandmark(edt_landmark_add_shop_details.getText().toString().trim());
        config.writeAdvertiserShopDesc(edt_shop_description_add.getText().toString().trim());
        config.writeAdvertiserFromTime(fromTime);
        config.writeAdvertiserToTime(toTime);
        config.writeAdvertiserLat(String.valueOf(lat));
        config.writeAdvertiserLon(String.valueOf(lon));
        config.writeAdvertiserImage(imageToString(bitmap));

        Intent rigistrationSuccess=new Intent(AddShopDetails.this, OtpActivity.class);
        rigistrationSuccess.putExtra("status","advertisement");
        startActivity(rigistrationSuccess);

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
                edt_name_add_shop_details.setText(Html.fromHtml(attributions.toString()));
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

         if (edt_name_add_shop_details.getText().toString().trim().length() == 0) {
            validate = false;
             edt_name_add_shop_details.requestFocus();
             edt_name_add_shop_details.setError("Enter Address");
        }
         if (edt_name_add_shop_details.getText().toString().trim().length() > 0) {
            if (lat==0){
                validate = false;
                edt_name_add_shop_details.requestFocus();
                edt_name_add_shop_details.setError("Enter correct Address");
            }
        } if (edt_landmark_add_shop_details.getText().toString().trim().length() == 0) {
            validate = false;
            edt_landmark_add_shop_details.requestFocus();
            edt_landmark_add_shop_details.setError("Enter LandMark");

        } if (edt_shop_description_add.getText().toString().trim().length() == 0) {
            validate = false;
             edt_shop_description_add.requestFocus();
             edt_shop_description_add.setError("Enter Shop Description");

        }
         if (bitmap==null){
             validate=false;
             AppUtils.showToast(context,"Please Set Image");
         }
        return validate;
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
