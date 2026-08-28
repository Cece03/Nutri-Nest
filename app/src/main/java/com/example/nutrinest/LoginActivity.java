package com.example.nutrinest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvForgetPassword;
    SQLiteDatabase db;

    MediaPlayer mediaPlayer; // ⭐ ADDED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);

        // ⭐ AUDIO INIT
        mediaPlayer = MediaPlayer.create(this, R.raw.audio);

        // Setup SQLite DB
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this, "UserDB", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS users(email TEXT PRIMARY KEY, password TEXT)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
        };

        db = helper.getReadableDatabase();

        // LOGIN BUTTON
        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = db.rawQuery(
                    "SELECT * FROM users WHERE email=? AND password=?",
                    new String[]{email, pass}
            );

            if (cursor != null && cursor.moveToFirst()) {

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                cursor.close();

                // ⭐ FIX: WAIT FOR AUDIO TO FINISH BEFORE NAVIGATION
                if (mediaPlayer != null) {
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mp -> {
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.putExtra("email", email); // ✅ ADDED
                        startActivity(intent);
                        finish();
                    });

                } else {
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("email", email); // ✅ ADDED
                    startActivity(intent);
                    finish();
                }

            } else {
                if (cursor != null) cursor.close();
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        tvForgetPassword.setOnClickListener(v ->
                Toast.makeText(this, "Forgot Password clicked", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}