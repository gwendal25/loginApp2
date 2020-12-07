package com.example.loginapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupActivity extends AppCompatActivity {

    //Fields
    Button choosePicture, register;
    EditText name, familyName, password, confirmPassword;
    ImageView imageSelected;

    //preferences
    public static final String nameKey = "NameKey";
    public static final String familyNameKey = "FamilyNameKey";
    public static final String passwordKey = "PasswordKey";
    public static final String rememberMeKey = "RememberMeKey";
    public static final String imagePath = "ImagePath";
    public static final String Preferences = "preferences";
    SharedPreferences sharedPrefs;

    private int passwordLength = 8;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sharedPrefs = getSharedPreferences(Preferences, Context.MODE_PRIVATE);
        initViews();
        initChoosePicture();
        initRegister();
    }

    private void initViews(){
        choosePicture = findViewById(R.id.choosePictureButton);
        register = findViewById(R.id.signInButton);
        name = findViewById(R.id.nameField);
        familyName = findViewById(R.id.familyNameField);
        password = findViewById(R.id.passwordField);
        confirmPassword = findViewById(R.id.confirmPasswordField);
        imageSelected = findViewById(R.id.selectedImage);
    }

    private void initChoosePicture(){
        choosePicture.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            )!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                        signupActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_STORAGE_PERMISSION
                );
            }
            else{
                selectImage();
            }
        });
    }

    private void initRegister(){
        register.setOnClickListener(v -> {
            //get the content of the text fields
            String newName = name.getText().toString();
            String newFamilyName = familyName.getText().toString();
            String newPassword = password.getText().toString();
            String newConfirmPassword = confirmPassword.getText().toString();

            //match the patterns for letter, number and special character
            Pattern letter = Pattern.compile("[A-Za-z]");
            Pattern number = Pattern.compile("[0-9]");
            Pattern specialChar = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            Matcher hasLetter = letter.matcher(newPassword);
            Matcher hasNumber = number.matcher(newPassword);
            Matcher hasSpecialChar = specialChar.matcher(newPassword);



            if(!Objects.equals(newPassword, newConfirmPassword)){
                Toast.makeText(getApplicationContext(), "password and confirm pasword must be the same : "+newPassword+", "+newConfirmPassword, Toast.LENGTH_SHORT).show();
            }
            else if(newPassword.length() < passwordLength){
                Toast.makeText(getApplicationContext(), "password must contains at least 8 characters", Toast.LENGTH_SHORT).show();
            }
            else if(!hasLetter.find()){
                Toast.makeText(getApplicationContext(), "password must contains at least one letter", Toast.LENGTH_SHORT).show();
            }
            else if(!hasNumber.find()){
                Toast.makeText(getApplicationContext(), "password must contains at least one number", Toast.LENGTH_SHORT).show();
            }
            else if(!hasSpecialChar.find()){
                Toast.makeText(getApplicationContext(), "password must contains at least one special character", Toast.LENGTH_SHORT).show();
            }
            else{
                //add the new values to the preferences
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(nameKey, newName);
                editor.putString(familyNameKey, newFamilyName);
                editor.putString(passwordKey, newPassword);
                editor.putBoolean(rememberMeKey, true);
                editor.commit();

                //change view with a little message
                Toast.makeText(getApplicationContext(),"preferences changed succesfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }
            else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try{
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageSelected.setImageBitmap(bitmap);

                        File selectedImageFile = new File(getPathFromUri(selectedImageUri));
                    }
                    catch(Exception exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri){
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);

        if(cursor == null){
            filePath = contentUri.getPath();
        }
        else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }
}