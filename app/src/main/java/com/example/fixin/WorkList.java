package com.example.fixin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class WorkList extends AppCompatActivity {


    private TextView Customer_textView13, Customer_txtemail;
    FirebaseAuth mAuth;
    ListView listview;
    private List<item> mProductList;
    private productlistAdapter adapter;


    ArrayList<String> tradework = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        Customer_txtemail = (TextView) findViewById(R.id.Customer_txtemail);
        Customer_txtemail.setText(getIntent().getExtras().getString("Email"));



        listview = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tradework);



        mAuth = FirebaseAuth.getInstance();


        mProductList = new ArrayList<>();
        mProductList.add(new item("Mechanic"));
        mProductList.add(new item("Plumber"));
        mProductList.add(new item("Carpenter"));
        mProductList.add(new item("Velder"));
        mProductList.add(new item("Painter"));
        mProductList.add(new item("Gardener"));
        mProductList.add(new item("Pipe Fitters"));

        adapter = new productlistAdapter(getApplicationContext(), mProductList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(WorkList.this, MechanicList.class);
                startActivity(i);

            }
        });

    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null) {
            Intent i = new Intent(WorkList.this, CustomerLogin.class);
            startActivity(i);
        }
    }
}
