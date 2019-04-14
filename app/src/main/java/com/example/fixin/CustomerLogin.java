package com.example.fixin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class CustomerLogin extends AppCompatActivity {

    private EditText Customer_email, Customer_password;
    private Button Customer_email_sign_in_button;
    private FirebaseAuth mAuth;
    private TextView Customer_textView4, Skip;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private SignInButton Customer_google_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        Customer_email = (EditText)findViewById(R.id.Customer_email);
        Customer_password = (EditText)findViewById(R.id.Customer_password);
        Customer_email_sign_in_button = (Button)findViewById(R.id.Customer_email_sign_in_button);
        Customer_textView4 = (TextView)findViewById(R.id.Customer_textView4);
        Skip = (TextView)findViewById(R.id.Skip);
        Customer_google_button = (SignInButton)findViewById(R.id.Customer_google_button);

        Customer_google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        mAuth = FirebaseAuth.getInstance();

        Customer_email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(CustomerLogin.this, "Please wait...", "Processing...", true);
                (mAuth.signInWithEmailAndPassword(Customer_email.getText().toString(), Customer_password.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(CustomerLogin.this, "Login Successful", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(CustomerLogin.this, WorkList.class);
                                    i.putExtra("Email",mAuth.getCurrentUser().getEmail());
                                    startActivity(i);
                                }
                                else {
                                    Log.e("ERROR", task.getException().toString());
                                    Toast.makeText(CustomerLogin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });


        Customer_textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomerLogin.this, CustomerRegister.class);
                startActivity(intent);

            }
        });


        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerLogin.this, WorkList.class);
                startActivity(intent);
            }
        });





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
                            Intent i = new Intent(CustomerLogin.this, WorkList.class);
                            i.putExtra("username",mAuth.getCurrentUser().getDisplayName());
//        getIntent().putExtra("url", String.valueOf(url));
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(CustomerLogin.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

}
