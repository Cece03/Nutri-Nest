package com.example.nutrinest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ProfileActivity extends AppCompatActivity {

    private boolean receiverRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔥 ONLY HANDLE LOGOUT — NO UI NEEDED
        registerLogoutReceiver();
    }

    // ---------------- LOGOUT RECEIVER ----------------
    private void registerLogoutReceiver() {
        if (!receiverRegistered) {

            IntentFilter filter = new IntentFilter("com.example.nutrinest.LOGOUT_ACTION");

            if (android.os.Build.VERSION.SDK_INT >= 33) {
                registerReceiver(logoutReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
            } else {
                ContextCompat.registerReceiver(
                        this,
                        logoutReceiver,
                        filter,
                        ContextCompat.RECEIVER_NOT_EXPORTED
                );
            }

            receiverRegistered = true;
        }
    }

    private final BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null &&
                    "com.example.nutrinest.LOGOUT_ACTION".equals(intent.getAction())) {

                Intent i = new Intent(ProfileActivity.this, AuthActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (receiverRegistered) {
            unregisterReceiver(logoutReceiver);
            receiverRegistered = false;
        }
    }
}