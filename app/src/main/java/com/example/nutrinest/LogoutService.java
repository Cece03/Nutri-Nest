package com.example.nutrinest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LogoutService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 🔥 CLEAR USER SESSION (optional but good)
        getSharedPreferences("user", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        // 🔥 GO TO AUTH SCREEN
        Intent i = new Intent(this, AuthActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

        // 🔥 STOP SERVICE
        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}