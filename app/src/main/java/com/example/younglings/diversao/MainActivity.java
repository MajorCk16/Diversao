package com.example.younglings.diversao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;


public class MainActivity extends AppCompatActivity {

    ImageView imgFbAuth,imgGoogleAuth;
    TextView tvUsername,tvPassword;
    private static final int RC_SIGN_IN=1;
    private FirebaseAuth mAuth;
    private ProgressDialog pb;

    GoogleApiClient mGoogleSignInClient;
    static FirebaseDatabase database;
    static DatabaseReference myRef;

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        pb.setMessage("signing in");
        pb.setTitle("Auth");
        pb.show();
    }

    public void navigate(){

        Intent myIntent = new Intent(this, Navigator.class);
        startActivity(myIntent);
        pb.dismiss();



    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         imgGoogleAuth= findViewById(R.id.img_google);
         tvUsername= findViewById(R.id.edtUsername);
         tvPassword=findViewById(R.id.edtPassword);

         pb= new ProgressDialog(MainActivity.this);








        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(MainActivity.this,"Error Occurred",Toast.LENGTH_LONG).show();

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        imgGoogleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
               // Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                           navigate();
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(Tag., "signInWithCredential:failure", task.getException());

                           // updateUI(null);
                            Toast.makeText(MainActivity.this,"Sign IN failed",Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            //Navigator.profilepic.setImageURI(mAuth.getCurrentUser().getPhotoUrl());
            String user= mAuth.getCurrentUser().getDisplayName();
            Log.i("Name: ",user);
            navigate();
            finish();
        }


    }


}





