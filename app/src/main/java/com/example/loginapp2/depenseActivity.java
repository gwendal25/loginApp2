package com.example.loginapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class depenseActivity extends AppCompatActivity {

    RecyclerView depenseView;
    FloatingActionButton add_button;

    CustomCashAdapter adapter;
    DBCashHelper dataHelper;
    ArrayList<String> depense_id, depense_date, depense_valeur, depense_categorie, depense_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense);

        depenseView = findViewById(R.id.depense_recycler_view);
        add_button = findViewById(R.id.add_depense_button);

        dataHelper = new DBCashHelper(depenseActivity.this);

        depense_id = new ArrayList<>();
        depense_date = new ArrayList<>();
        depense_valeur = new ArrayList<>();
        depense_categorie = new ArrayList<>();
        depense_desc = new ArrayList<>();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(depenseActivity.this, addDepense.class);
                startActivity(intent);
            }
        });

        storeDataInArrays();

        adapter = new CustomCashAdapter(depenseActivity.this, depense_id, depense_date, depense_valeur, depense_categorie, depense_desc);
        depenseView.setAdapter(adapter);
        depenseView.setLayoutManager(new LinearLayoutManager(depenseActivity.this));
    }

    public void storeDataInArrays(){
        Cursor cursor = dataHelper.readAllDataDepense();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                depense_id.add(cursor.getString(0));
                depense_date.add(cursor.getString(1));
                depense_valeur.add(cursor.getString(2));
                depense_categorie.add(cursor.getString(3));
                depense_desc.add(cursor.getString(4));
            }
        }
    }
}