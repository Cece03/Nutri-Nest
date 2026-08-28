package com.example.nutrinest;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity {

    VideoView videoView;
    Button btnSpeak, btnStart;

    TextToSpeech tts;
    MediaPlayer clickSound;

    android.hardware.SensorManager sensorManager;
    android.hardware.Sensor sensor;
    android.hardware.SensorEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        videoView = findViewById(R.id.videoView);
        btnSpeak = findViewById(R.id.btnSpeak);
        btnStart = findViewById(R.id.btnStart);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bgvideo);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });

        btnSpeak.setOnClickListener(v -> {
            tts.speak("Welcome to Nutri Nest. Your journey starts here",
                    TextToSpeech.QUEUE_FLUSH, null, null);
        });

        // ✅ FIX ADDED (ONLY CHANGE)
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, AuthActivity.class);
            startActivity(intent);
        });

        sensorManager = (android.hardware.SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);

        listener = new android.hardware.SensorEventListener() {
            long lastShakeTime = 0;

            @Override
            public void onSensorChanged(android.hardware.SensorEvent event) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float shake = Math.abs(x + y + z);

                if (shake > 25) {
                    long now = System.currentTimeMillis();

                    if (now - lastShakeTime > 1500) {
                        lastShakeTime = now;

                        tts.speak("Welcome to Nutri Nest. Your journey starts here",
                                TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {}
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensor,
                android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }
}