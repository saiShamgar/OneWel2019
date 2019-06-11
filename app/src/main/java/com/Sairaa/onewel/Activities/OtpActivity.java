package com.Sairaa.onewel.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.Sairaa.onewel.Activities.Add.AddShopDetails;
import com.Sairaa.onewel.Activities.Promoter.PromoterRegistrationSuccess;
import com.Sairaa.onewel.BaseActivity;
import com.Sairaa.onewel.Model.Advertisement.AdvertisementDetails;
import com.Sairaa.onewel.Model.Customer.CustomerDetails;
import com.Sairaa.onewel.Model.promoter.PromoterPersonalDetails;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.google.android.gms.tasks.*;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Ref;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends BaseActivity {

    private String mVerificationId;

    //The edittext to input the code
    private EditText editTextCode;

    //firebase auth object
    private FirebaseAuth mAuth;

    private SharedPreferenceConfig config;
    private Context context;

    private String status;
    private Bitmap bitmap;
    private String imageId;
    private String image_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        config=new SharedPreferenceConfig(this);
        context=OtpActivity.this;

        mAuth = FirebaseAuth.getInstance();

        status=getIntent().getExtras().getString("status");

        editTextCode = findViewById(R.id.edt_verify_otp);


        if (status.contains("promoter")){
            sendVerificationCode(config.readPromoterPhone());

            findViewById(R.id.btn_verify_otp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = editTextCode.getText().toString().trim();
                    if (code.isEmpty() || code.length() < 6) {
                        editTextCode.setError("Enter valid code");
                        editTextCode.requestFocus();
                        return;
                    }

                    //verifying the code entered manually
                    verifyVerificationCode(code);
                }
            });

        }
        else if (status.contains("advertisement")){
            sendVerificationCode(config.readAdvertiserPhone());

            findViewById(R.id.btn_verify_otp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = editTextCode.getText().toString().trim();
                    if (code.isEmpty() || code.length() < 6) {
                        editTextCode.setError("Enter valid code");
                        editTextCode.requestFocus();
                        return;
                    }

                    //verifying the code entered manually
                    verifyVerificationCode(code);
                }
            });
        }
        else if (status.contains("customer")){
            sendVerificationCode(config.readCustomer_phone());

            findViewById(R.id.btn_verify_otp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = editTextCode.getText().toString().trim();
                    if (code.isEmpty() || code.length() < 6) {
                        editTextCode.setError("Enter valid code");
                        editTextCode.requestFocus();
                        return;
                    }

                    //verifying the code entered manually
                    verifyVerificationCode(code);
                }
            });

        }



    }

    private void sendVerificationCode(String readPromoterPhone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + readPromoterPhone,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("error otp",e.toString());
            AppUtils.showToast(context,e.getMessage());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {

        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading");
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveUserDetails();
                        } else {

                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }

    private void saveUserDetails() {
        if (status.contains("promoter")){
            PromoterPersonalDetails details=new PromoterPersonalDetails(
                    config.readPromoterName(),
                    config.readPromoterPhone(),
                    config.readPromoterREF_num(),
                    config.readPromoterAddress(),
                    config.readPromoterUPI_num(),
                    config.readPromoterLat(),
                    config.readPromoterLon());

            FirebaseDatabase.getInstance().getReference().child(Contants.PROMOTER)
                    .child(config.readPromoterPhone())
                    .setValue(details)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                saveRefNum(config.readPromoterPhone(),config.readPromoterREF_num(),1);
                            }
                            else {
                                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                AppUtils.showToast(context,"Registration Failed please try again");
                            }

                        }
                    });
        }
        else if (status.contains("advertisement")){
            bitmap=StringToBitMap(config.readAdvertiserImage());
            if (bitmap!=null){
                pushImageToFirebase();
            }

        }

        else if (status.contains("customer")){

            CustomerDetails customerDetails=new CustomerDetails(config.readCustomer_name(),
                    config.readCustomer_phone(),
                    config.readCustomer_ref());
            FirebaseDatabase.getInstance().getReference().child(Contants.CUMTOMER)
                    .child(config.readCustomer_phone())
                    .setValue(customerDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                saveRefNum(config.readCustomer_phone(),config.readCustomer_ref(),3);
                            }
                            else {
                                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                AppUtils.showToast(context,"Registration Failed please try again");
                            }

                        }
                    });



        }
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private void pushImageToFirebase() {
        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading...");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        imageId = FirebaseDatabase.getInstance().getReference().push().getKey();
        imageId = "images/" + imageId + ".jpg";
        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child(imageId);
        // Get the data from an ImageView as bytes

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(OtpActivity.this, "success image posted", Toast.LENGTH_SHORT).show();
                getUrlForDownload();
            }
        });
    }

    private void getUrlForDownload() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://onewel2019.appspot.com/");
        final StorageReference pathReference = storageRef.child(imageId);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Toast.makeText(CameraActivity.this, "" + pathReference.getDownloadUrl(), Toast.LENGTH_SHORT).show();
                Log.e("url", "" + uri.toString());

                image_path=uri.toString();

                AdvertisementDetails details=new AdvertisementDetails(
                        config.readAdvertiserName(),
                        config.readAdvertiserPhone(),
                        config.readAdvertiserRef_num(),
                        config.readAdvertiserShopType(),
                        config.readAdvertiserAddress(),
                        config.readAdvertiserLat(),
                        config.readAdvertiserLon(),
                        config.readAdvertiserLandmark(),
                        config.readAdvertiserFromTime(),
                        config.readAdvertiserToTime(),
                        config.readAdvertiserShopDesc(),
                        image_path);

                FirebaseDatabase.getInstance().getReference().child(Contants.ADVERTISER)
                        .child(config.readAdvertiserPhone())
                        .setValue(details)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    if (config.readAdvertiserRef_num()!=null){
                                        saveRefNum(config.readAdvertiserPhone(),config.readAdvertiserRef_num(),2);
                                    }

                                }
                                else {
                                    AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                    AppUtils.showToast(context,"Registration Failed please try again");
                                }

                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AppUtils.dismissCustomProgress(mCustomProgressDialog);

            }
        });
    }

    private void saveRefNum(String phone_num, String ref_num, int i) {

        if (i==2){
            FirebaseDatabase.getInstance().getReference().child(Contants.REFERENCES)
                    .child(ref_num).push()
                    .child(phone_num)
                    .setValue("REFERRED")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                //verification successful we will start the profile activity
                                Intent intent = new Intent(OtpActivity.this, PromoterRegistrationSuccess.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        }
                    });
        }
         if (i==1){
            FirebaseDatabase.getInstance().getReference().child(Contants.REFERENCES)
                    .child(ref_num).push()
                    .child(phone_num)
                    .setValue("REFERRED")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                //verification successful we will start the profile activity
                                Intent intent = new Intent(OtpActivity.this, PromoterRegistrationSuccess.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        }
                    });

        }
         if (i==3){
             FirebaseDatabase.getInstance().getReference().child(Contants.REFERENCES)
                     .child(ref_num).push()
                     .child(phone_num)
                     .setValue("REFERRED")
                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()){
                                 AppUtils.dismissCustomProgress(mCustomProgressDialog);
                                 //verification successful we will start the profile activity
                                 Intent intent = new Intent(OtpActivity.this, PromoterRegistrationSuccess.class);
                                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 startActivity(intent);
                             }

                         }
                     });

         }


    }


}

