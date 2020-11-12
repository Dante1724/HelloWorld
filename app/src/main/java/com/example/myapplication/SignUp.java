package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    DBHandler dbHandler;
    protected Cursor cursor;

    private EditText signUpUsername;
    private EditText signUpEmail;
    private EditText signUpPassword;
    private Button signButton;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUpUsername = findViewById(R.id.txtUsername);
        signUpEmail = findViewById(R.id.txtEmail);
        signUpPassword = findViewById(R.id.txtPassword);
        signButton = findViewById(R.id.btnSignUp);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp(){
        if(signUpUsername.getText().toString().equals("") ||
                signUpEmail.getText().toString().equals("") ||
                signUpPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Semua field harus terisi, tidak boleh kosong", Toast.LENGTH_LONG).show();
        } else {
            dbHandler = new DBHandler(this);
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            db.execSQL("INSERT INTO user(username, email, password) VALUES('" +
                    signUpUsername.getText().toString() + "','" +
                    signUpEmail.getText().toString() + "','" +
                    signUpPassword.getText().toString() + "')");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("STATUS_SIGN_UP", "Berhasil");
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Sign Up Berhasil!", Toast.LENGTH_LONG).show();
        }
    }
}
