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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerRegister extends AppCompatActivity {

    private EditText Customer_editText, Customer_editText2, Customer_editText3, Customer_editText4, Customer_editText5;
    private Button Customer_button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        Customer_editText = (EditText)findViewById(R.id.Customer_editText);
        Customer_editText2 = (EditText)findViewById(R.id.Customer_editText2);
        Customer_editText3 = (EditText)findViewById(R.id.Customer_editText3);
        Customer_editText4 = (EditText)findViewById(R.id.Customer_editText4);
        Customer_editText5 = (EditText)findViewById(R.id.Customer_editText5);
        Customer_button = (Button)findViewById(R.id.Customer_button);

        mAuth = FirebaseAuth.getInstance();

        Customer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = ProgressDialog.show(CustomerRegister.this, "Please wait...", "Processing...", true);
                (mAuth.createUserWithEmailAndPassword(Customer_editText4.getText().toString(), Customer_editText5.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(CustomerRegister.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CustomerRegister.this, CustomerLogin.class);
                            startActivity(i);
                        }
                        else {
                            Log.e("Error", task.getException().toString());
                            Toast.makeText(CustomerRegister.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        });
    }
}
