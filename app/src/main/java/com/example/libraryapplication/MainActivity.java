package com.example.libraryapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    EditText username, email, password, repassword;
    Button register, signin;
    DBHelper DB;
    BottomNavigationView bottomNavigationView;


    HomeFragment homeFragment = new HomeFragment();
    FavoritesFragment favoritesFragment = new FavoritesFragment();
    DoneFragment doneFragment = new DoneFragment();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        register = (Button) findViewById(R.id.regbutton);
        signin = (Button) findViewById(R.id.signinbutton);
        DB = new DBHelper(this);
        bottomNavigationView = findViewById(R.id.bot_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();


        //switches between pages(fragments)
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;

                    case R.id.favorites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,favoritesFragment).commit();
                        return true;

                    case R.id.done:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,doneFragment).commit();
                        return true;
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String em = email.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("") || em.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    //if passwords match we can continue
                    if(pass.equals(repass)){
                        boolean checkuser = DB.checkuser(user);
                        if(checkuser==false){
                            boolean insert = DB.DataInsert(user, em, pass);

                            //if true the home page will be opened

                            if(insert==true){
                                Toast.makeText(MainActivity.this, "You're account has been created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }

                            //if false registration is failed

                            else{
                                Toast.makeText(MainActivity.this, "Inputs do not match, try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //passwords don't match

                    else{
                        Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            }
        });
    }


}