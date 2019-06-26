package com.Sairaa.onewel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.Sairaa.onewel.Model.UserData;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.Sairaa.onewel.Utils.SharedPreferenceConfig;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.FirebaseDatabase;

public class GoogleSignIN extends BaseActivity {
    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;
    //Tag for the logs optional
    private static final String TAG = "simplifiedcoding";

    String uniqueKey;

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;

    //And also a Firebase Auth object
    FirebaseAuth mAuth;

    private SharedPreferenceConfig config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

        //first we intialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        config=new SharedPreferenceConfig(this);

        //Then we need a GoogleSignInOptions object
        //And we need to build it as below
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Now we will attach a click listener to the sign_in_button
        //and inside onClick() method we are calling the signIn() method that will open
        //google sign in intent
        findViewById(R.id.google_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            //   Toast.makeText(this,""+currentUser.getEmail()+" : "+currentUser.getPhoneNumber()+currentUser.getDisplayName(),Toast.LENGTH_SHORT).show();
            if(currentUser.getEmail()!=null && !currentUser.getEmail().equals("")){
               // sharedPreferences.writeEmail(currentUser.getEmail());
//                sharedPreferences.writeName(currentUser.getDisplayName());

                config.writeGoogle_image(currentUser.getPhotoUrl().toString());
                config.writeGoogle_email(currentUser.getEmail());
            }
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            AppUtils.dismissCustomProgress(mCustomProgressDialog);

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(GoogleSignIN.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading...");

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = mAuth.getCurrentUser();

                            saveUserDetails(user);

                            Toast.makeText(GoogleSignIN.this, "User Signed In", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(GoogleSignIN.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void saveUserDetails(final FirebaseUser user) {

        uniqueKey=user.getUid();


        UserData userData=new UserData(user.getDisplayName()
                                ,user.getEmail()
                                ,user.getPhotoUrl().toString()
                                ,user.getPhoneNumber());
        FirebaseDatabase.getInstance().getReference().child(Contants.USERS)
                .child(uniqueKey)
                 .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Log.e("save user data","success");
                            AppUtils.dismissCustomProgress(mCustomProgressDialog);
                            updateUI(user);
                        }
                        else {
                            Log.e("save user data","fail");
                        }

            }
        });

    }

    //this method is called on click
    private void signIn() {
        //getting the google signin intent
        AppUtils.showCustomProgressDialog(mCustomProgressDialog,"Loading...");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}
