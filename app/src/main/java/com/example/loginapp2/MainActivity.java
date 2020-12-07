package com.example.loginapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //UI elements
    Button loginButton, signUpButton, librarby;
    EditText loginText;
    TextView unregisteredText;
    CheckBox remememberBox;

    //shared preferences elements
    public static final String passwordKey = "PasswordKey";
    public static final String rememberMekey = "rememberMeKey";
    public static final String Preferences = "preferences";
    SharedPreferences sharedPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //base setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = getSharedPreferences(Preferences, Context.MODE_PRIVATE);
        initViews();
        initRememberMe();
        initLoginButton();
        initSignUpButton();
    }

    public void initViews(){
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        loginText = findViewById(R.id.passwordText);
        unregisteredText = findViewById(R.id.unregisteredText);
        remememberBox = findViewById(R.id.rememberMe);
    }

    public void initSignUpButton(){
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, signupActivity.class);
            startActivity(intent);
        });
    }

    public void initRememberMe(){
        String password = sharedPrefs.getString(passwordKey, null);
        boolean rememberMe = sharedPrefs.getBoolean(rememberMekey, false);
        if(password != null && rememberMe){
            remememberBox.setChecked(true);
        }
    }

    public void initLoginButton(){
        loginButton.setOnClickListener(v -> {
            //get the shared prefs password and the entered password
            String password = sharedPrefs.getString(passwordKey, null);
            String enteredPassword = loginText.getText().toString();

            if(password == null){
                Toast.makeText(getApplicationContext(), "No user defined yet", Toast.LENGTH_SHORT).show();
            }
            else if(enteredPassword.equals(password)){
                Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, navigationActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Wrong Credentials : "+enteredPassword, Toast.LENGTH_SHORT).show();
            }
        });
    }
}