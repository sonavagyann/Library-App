package com.example.libraryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    EditText username, email, password;
    Button regbutton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login4);

        username = (EditText) findViewById(R.id.username1);
        email = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.password1);
        regbutton = (Button) findViewById(R.id.regbutton);
        DB = new DBHelper(this);

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String em = email.getText().toString();
                String pass = password.getText().toString();

                //if at least one string is empty
                if(user.equals("") || email.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean checkuseremailpass = DB.checkuseremailpass(user, em, pass);
                    //if such user exists
                    if(checkuseremailpass==true){
                        Toast.makeText(LoginActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Some inputs do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}