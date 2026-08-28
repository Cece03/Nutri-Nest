package com.example.nutrinest;

import android.content.Intent; //
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    TextView tvEmail, tvPassword;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);

        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);

        // BACK BUTTON
        findViewById(R.id.backBtn).setOnClickListener(v -> {
            Intent intent = new Intent(UserDetailsActivity.this, MeActivity.class);
            startActivity(intent);
            finish();
        });

        // DB setup (same as login)
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this, "UserDB", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS users(email TEXT PRIMARY KEY, password TEXT)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
        };

        db = helper.getReadableDatabase();

        // FETCH LAST LOGGED-IN USER
        Cursor cursor = db.rawQuery("SELECT * FROM users LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {

            String email = cursor.getString(0);
            String password = cursor.getString(1);

            tvEmail.setText("Email: " + email);
            tvPassword.setText("Password: " + password);

            cursor.close();
        }
    }
}