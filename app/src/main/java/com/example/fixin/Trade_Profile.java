package com.example.fixin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Trade_Profile extends AppCompatActivity {
    private TextView textView13, txtemail;
//    private String username, photo;
    private Uri url;
    private TextView username;
    private Button logout;
    private FirebaseAuth mAuth;
//    ImageView ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade__profile);
        txtemail = (TextView) findViewById(R.id.txtemail);
        txtemail.setText(getIntent().getExtras().getString("Email"));
        username = (TextView)findViewById(R.id.username);
        username.setText(getIntent().getExtras().getString("username"));
        logout = (Button)findViewById(R.id.logout);
//        ph = (ImageView)findViewById(R.id.photo);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();

                Intent i = new Intent(Trade_Profile.this, LoginFixIn.class);
                startActivity(i);
            }
        });

        mAuth = FirebaseAuth.getInstance();



//        username = getIntent().getStringExtra("username");
//        photo = getIntent().getStringExtra("url");

//        url = Uri.parse(photo);

//        u.setText(username);
//        Picasso.get().load(url).into(ph);


    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null) {
            Intent i = new Intent(Trade_Profile.this, LoginFixIn.class);
            startActivity(i);
        }
    }
}
