package com.example.fixin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MechanicList extends AppCompatActivity {
    ImageView image1;
    ListView work_person;
    private List<item> mProductList;
    private productlistAdapter adapter;


    ArrayList<String> trade_person = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_list);
        work_person = (ListView) findViewById(R.id.work_person);
        ArrayAdapter<String> myarrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, trade_person);


        mProductList = new ArrayList<>();
        mProductList.add(new item("Arun"));
        mProductList.add(new item("Albert"));
        mProductList.add(new item("Jake"));
        mProductList.add(new item("Velder"));
        mProductList.add(new item("Marcus"));
        mProductList.add(new item("Lee"));
        mProductList.add(new item("PTom"));



        adapter = new productlistAdapter(getApplicationContext(), mProductList);
        work_person.setAdapter(adapter);

        work_person.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MechanicList.this, Person.class);
                startActivity(i);

            }
        });
    }
}
