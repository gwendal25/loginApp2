package com.example.loginapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class navigationActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initViews();
        initToggle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initNavigationView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initViews(){
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
    }

    public void initToggle(){
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void initNavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        //TODO : make home
                        Toast.makeText(navigationActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.depenses:
                        Intent depenseIntent = new Intent(navigationActivity.this, depenseActivity.class);
                        startActivity(depenseIntent);
                        return true;
                    case R.id.revenus:
                        //TODO : make revenus
                        Toast.makeText(navigationActivity.this, "Revenus", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.solde:
                        //TODO : make solde
                        Toast.makeText(navigationActivity.this, "Solde", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.cashflow:
                        Intent intent = new Intent(navigationActivity.this, addDepense.class);
                        startActivity(intent);
                        return true;
                    case R.id.converter:
                        Intent converterIntent = new Intent(navigationActivity.this, deviseConverterActivity.class);
                        startActivity(converterIntent);
                    case R.id.logout:
                        Intent logoutIntent = new Intent(navigationActivity.this, MainActivity.class);
                        startActivity(logoutIntent);
                        return true;
                }
                return true;
            }
        });
    }
}