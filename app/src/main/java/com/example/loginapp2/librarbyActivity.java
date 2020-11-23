package com.example.loginapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class librarbyActivity extends AppCompatActivity {

    RecyclerView libraryView;
    FloatingActionButton addButton;
    ImageView empty_icon;
    TextView no_data;

    DBhelper dataHelper;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarby);

        libraryView = findViewById(R.id.recycler_view);
        addButton = findViewById(R.id.add_button);
        empty_icon = findViewById(R.id.empty_icon);
        no_data = findViewById(R.id.no_data_icon);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(librarbyActivity.this, addActivity.class);
                startActivity(intent);
            }
        });

        dataHelper = new DBhelper(librarbyActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(librarbyActivity.this, this, book_id, book_title, book_author, book_pages);
        libraryView.setAdapter(customAdapter);
        libraryView.setLayoutManager(new LinearLayoutManager(librarbyActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = dataHelper.readAllData();

        if(cursor.getCount() == 0){
            empty_icon.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "no data in librarby database", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
            empty_icon.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.librarby_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        //create a new alert dialog message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all data ?");
        builder.setMessage("Are you sure you want to delete all books ?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete data
                DBhelper dataHelper = new DBhelper(librarbyActivity.this);
                dataHelper.deleteAllData();

                //reload activity
                Intent intent = new Intent(librarbyActivity.this, librarbyActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}