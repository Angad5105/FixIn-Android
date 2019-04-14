package com.example.fixin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Person extends AppCompatActivity {
    TextView textView13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        textView13 = (TextView)findViewById(R.id.textView13);

        textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(Person
                        .this, "Please wait...", "Processing...", true);
                Intent i = new Intent(Person.this, Booking.class);
                startActivity(i);
            }
        });
    }
}
