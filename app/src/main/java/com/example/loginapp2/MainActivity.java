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

        //find UI elements
        loginButton = (Button)findViewById(R.id.loginButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        loginText = (EditText)findViewById(R.id.passwordText);
        unregisteredText = (TextView)findViewById(R.id.unregisteredText);
        remememberBox = (CheckBox)findViewById(R.id.rememberMe);

        //obtain sharedPrefs infos
        sharedPrefs = getSharedPreferences(Preferences, Context.MODE_PRIVATE);
        String password = sharedPrefs.getString(passwordKey, null);
        boolean rememberMe = sharedPrefs.getBoolean(rememberMekey, false);

        //check if there is a password and remember me is checked
        if(password != null && rememberMe == true){
            remememberBox.setChecked(true);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signupActivity.class);
                startActivity(intent);
            }
        });
    }
}