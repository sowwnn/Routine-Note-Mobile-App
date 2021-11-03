package com.example.dream_routine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView fogotpassword;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        fogotpassword = findViewById(R.id.forgot_pass);
        register = findViewById(R.id.register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(LoginActivity.this,Dashboard.class);
                startActivity(login);
            }
        });

        fogotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fogotfass = new Intent(LoginActivity.this,LoginActivity.class);
                startActivity(fogotfass);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register =  new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register);
            }
        });

    }


}