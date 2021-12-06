package com.example.dream_routine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView txtRegister;
    EditText edtusername, edtpassword;
    DataHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.register);
        db = new DataHelper(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();

                boolean valid = db.CheckValid(username, password);
                if(valid){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(LoginActivity.this, Dashboard.class);
                    int id = db.getUserID(username, password);
                    login.putExtra("Id",id);
                    startActivity(login);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Đăng nhập ko thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

    }


}