package com.example.fixin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginFixIn extends AppCompatActivity{

    private EditText email, password;
    private Button email_sign_in_button;
    private FirebaseAuth mAuth;
    private TextView textView4;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private SignInButton google_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fix_in);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        email_sign_in_button = (Button)findViewById(R.id.email_sign_in_button);
        textView4 =(TextView)findViewById(R.id.textView4);

        google_button = (SignInButton)findViewById(R.id.google_button);

        google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });




        mAuth = FirebaseAuth.getInstance();

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(LoginFixIn.this, "Please wait...", "Processing...", true);
                (mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginFixIn.this, "Login Successful", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(LoginFixIn.this, Trade_Profile.class);
                                    i.putExtra("Email",mAuth.getCurrentUser().getEmail());
                                    startActivity(i);
                                }
                                else {
                                    Log.e("ERROR", task.getException().toString());
                                    Toast.makeText(LoginFixIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginFixIn.this, Register.class);
                startActivity(intent);

            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

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
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            updateUI(currentUser);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(LoginFixIn.this, Trade_Profile.class);
                            i.putExtra("username",mAuth.getCurrentUser().getDisplayName());
//        getIntent().putExtra("url", String.valueOf(url));
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(LoginFixIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }





    private void updateUI(FirebaseUser user){


        Intent i = new Intent(LoginFixIn.this, Trade_Profile.class);
        i.putExtra("username",mAuth.getCurrentUser().getDisplayName());
//        getIntent().putExtra("url", String.valueOf(url));
        startActivity(i);
    }
}
