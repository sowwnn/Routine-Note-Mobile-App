package com.example.dream_routine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button btnregister;
    TextView tvlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnregister = findViewById(R.id.btnregister);
        tvlogin = findViewById(R.id.tvlogin);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(register);
                Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_SHORT).show();
            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(login);
            }
        });
    }

}