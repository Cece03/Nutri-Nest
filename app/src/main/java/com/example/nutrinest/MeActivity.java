package com.example.nutrinest;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MeActivity extends AppCompatActivity {

    ImageView profileImage;
    Button btnCamera, btnFoodDiary, btnViewDetails; // ⭐ ADDED

    TextView welcomeText;
    ListView photoListView;

    ArrayList<Bitmap> photoList = new ArrayList<>();
    ArrayList<String> photoLabels = new ArrayList<>();

    ArrayAdapter<String> adapter;

    ActivityResultLauncher<Intent> cameraLauncher;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        profileImage = findViewById(R.id.profileImage);
        btnCamera = findViewById(R.id.btnCamera);
        btnFoodDiary = findViewById(R.id.btnFoodDiary);
        btnViewDetails = findViewById(R.id.btnViewDetails); // ⭐ ADDED

        photoListView = findViewById(R.id.photoListView);
        welcomeText = findViewById(R.id.welcomeText);

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        // 💜 TEXT
        welcomeText.setText("Hey HealthFreak");
        welcomeText.setTextColor(0xFF4A148C);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                photoLabels);

        photoListView.setAdapter(adapter);

        // 📷 CAMERA RESULT
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && imageUri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                    getContentResolver(), imageUri);

                            photoList.add(bitmap);
                            photoLabels.add("Meal " + photoList.size());

                            adapter.notifyDataSetChanged();
                            profileImage.setImageBitmap(bitmap);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        // 📷 OPEN CAMERA
        btnCamera.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, 101);
                return;
            }

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,
                    "Meal_" + System.currentTimeMillis());

            imageUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
            );

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            cameraLauncher.launch(intent);
        });

        // 🍱 FOOD DIARY
        btnFoodDiary.setText("Food Diary");

        btnFoodDiary.setOnClickListener(v -> {
            Intent intent = new Intent(MeActivity.this, FoodDiaryActivity.class);
            startActivity(intent);
        });

        // ⭐ NEW BUTTON ACTION (OPEN USER DETAILS PAGE)
        btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(MeActivity.this, UserDetailsActivity.class);
            startActivity(intent);
        });

        // 🖼️ PREVIEW
        photoListView.setOnItemClickListener((parent, view, position, id) ->
                profileImage.setImageBitmap(photoList.get(position))
        );

        // 🗑️ DELETE
        photoListView.setOnItemLongClickListener((parent, view, position, id) -> {
            photoList.remove(position);
            photoLabels.remove(position);
            adapter.notifyDataSetChanged();
            return true;
        });
    }
}