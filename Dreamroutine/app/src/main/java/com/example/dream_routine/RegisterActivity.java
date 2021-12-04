package com.example.dream_routine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextView txtLogin;
    Button btnRegister;
    EditText edtUsername, edtName, edtPass, edtEmail;
    User user;
    DataHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edtUsername = findViewById(R.id.username);
        edtName = findViewById(R.id.name);
        edtPass = findViewById(R.id.register_password);
        edtEmail = findViewById(R.id.email);

        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.login);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
        db = new DataHelper(getApplicationContext());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edtUsername.getText().toString();
                String name =  edtName.getText().toString();
                String userPass = edtPass.getText().toString();
                String userEmail = edtEmail.getText().toString();
                user = new User(userName, name, userPass, userEmail);
                db.insertUser(user);
            }
        });

    }
}