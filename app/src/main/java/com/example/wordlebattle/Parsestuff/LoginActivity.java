package com.example.wordlebattle.Parsestuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wordlebattle.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //XLM data assignment
    EditText Username, Password;
    Button Login, Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //Setting data values
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.LoginB);
        Register = findViewById(R.id.RegisterB);

        //Persistence Check
/*        if(ParseUser.getCurrentUser() !=  null)goMain();*/

        //Login Check
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Username.getText().toString();
                String pass = Password.getText().toString();
                ParseUser.logInInBackground(user, pass, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e != null){
                            Log.e("LoginActivity", "Login Error: " + e);
                        }
                        goMain();
                    }
                });
            }
        });

        //Register Check
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = Username.getText().toString();
                String pass = Password.getText().toString();

                ParseUser newUser = new ParseUser();
                newUser.setUsername(user);
                newUser.setPassword(pass);
                newUser.put("FriendsList", new ArrayList<>());
                newUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Log.e("LoginActivity", "Register Check: " + e);
                            Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_SHORT).show();
                            return;
                            }

                        ParseUser.logInInBackground(user, pass, new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(e != null){
                                    Log.e("LoginActivity", "Register Login failed");
                                    return;
                                }
                                goMain();
                            }
                            });

                        }
                    });

            }
        });

    }

    //Moving from login to main
    public void goMain(){
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}