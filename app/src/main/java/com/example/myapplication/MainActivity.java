package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnSignUp;
    private String username = "admin";
    private String password = "admin";

    DBHandler dbHandler;
    protected Cursor cursor;
    public static SharedPreferences sharedPreferences;
    public static final String myPreference = "MY_PREFERENCE";
    public static final String KEY_EMAIL = "KEY_EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: dipanggil");
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(KEY_EMAIL, new String()).equals("")){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickBtnLogin();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUp();
            }
        });
    }
    public void signUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    private void onClickBtnLogin(){
//        if(txtUsername.getText().toString().equals(username) && txtPassword.getText().toString().equals(password)){
//            Intent intent = new Intent(this, HomeActivity.class);
//            intent.putExtra("Try", "Try");
//            startActivity(intent);
//        }
        dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM USER " + "WHERE USERNAME = '" + txtUsername.getText().toString() + "'" +"AND password = '"+txtPassword.getText().toString()+"'",null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, txtUsername.getText().toString());
            editor.commit();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Username atau Password ANDA tidak benar!", Toast.LENGTH_LONG).show();
        }
    }
}