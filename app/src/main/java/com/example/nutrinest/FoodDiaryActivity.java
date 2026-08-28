package com.example.nutrinest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;wh
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodDiaryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView addBtn;

    ArrayList<Uri> list = new ArrayList<>();
    FoodAdapter adapter;

    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodiary);

        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addBtn);

        adapter = new FoodAdapter(list, position -> {
            list.remove(position);
            adapter.notifyDataSetChanged();
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        // 📸 Open gallery
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK
                            && result.getData() != null
                            && result.getData().getData() != null) {

                        Uri uri = result.getData().getData();
                        list.add(uri);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            galleryLauncher.launch(intent);
        });

        // ⭐ HEADER BACK BUTTON
        findViewById(R.id.backBtn).setOnClickListener(v -> {
            Intent intent = new Intent(FoodDiaryActivity.this, MeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // ============================
    // 📦 ADAPTER
    // ============================
    static class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

        ArrayList<Uri> list;
        OnDelete onDelete;

        interface OnDelete {
            void onDelete(int position);
        }

        FoodAdapter(ArrayList<Uri> list, OnDelete onDelete) {
            this.list = list;
            this.onDelete = onDelete;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            ImageView img, delete;

            ViewHolder(View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.foodImage);
                delete = itemView.findViewById(R.id.deleteBtn);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_food_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.img.setImageURI(list.get(position));

            holder.delete.setOnClickListener(v -> {
                onDelete.onDelete(position);
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}