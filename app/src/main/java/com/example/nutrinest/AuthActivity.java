package com.example.nutrinest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(AuthActivity.this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(AuthActivity.this, LoginActivity.class)));
    }
}