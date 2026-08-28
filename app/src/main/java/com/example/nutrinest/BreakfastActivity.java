package com.example.nutrinest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class BreakfastActivity extends AppCompatActivity {

    private Button btnFilter;
    private ViewPager2 recipePager;
    private RecipeAdapter adapter;
    private final List<Integer> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        btnFilter = findViewById(R.id.btnFilter);
        recipePager = findViewById(R.id.recipePager);

        // -------- Recipes --------
        recipes.add(R.drawable.oats);       // 395 cal
        recipes.add(R.drawable.omlette);    // 223 cal
        recipes.add(R.drawable.potato);     // 108 cal
        recipes.add(R.drawable.sandwich);   // 316 cal
        recipes.add(R.drawable.cornsalad);  // 128 cal

        adapter = new RecipeAdapter(recipes);
        recipePager.setAdapter(adapter);

        // Make images bigger (full width, more height)
        recipePager.setOffscreenPageLimit(recipes.size());

        // -------- FILTER BUTTON LOGIC --------
        btnFilter.setOnClickListener(v -> {
            android.widget.PopupMenu popup = new android.widget.PopupMenu(this, btnFilter);
            popup.getMenu().add("Lowest → Highest Calories");
            popup.getMenu().add("Highest → Lowest Calories");

            popup.setOnMenuItemClickListener(item -> {
                String title = item.getTitle().toString();
                List<Integer> sorted = new ArrayList<>();

                if (title.equals("Lowest → Highest Calories")) {
                    sorted.add(R.drawable.potato);      // 108
                    sorted.add(R.drawable.cornsalad);   // 128
                    sorted.add(R.drawable.omlette);     // 223
                    sorted.add(R.drawable.sandwich);    // 316
                    sorted.add(R.drawable.oats);        // 395
                } else if (title.equals("Highest → Lowest Calories")) {
                    sorted.add(R.drawable.oats);        // 395
                    sorted.add(R.drawable.sandwich);    // 316
                    sorted.add(R.drawable.omlette);     // 223
                    sorted.add(R.drawable.cornsalad);   // 128
                    sorted.add(R.drawable.potato);      // 108
                }

                recipes.clear();
                recipes.addAll(sorted);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Sorted by calories", Toast.LENGTH_SHORT).show();
                return true;
            });

            popup.show();
        });

        // -------- Back Button --------
        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
    }

    // -------- Adapter --------
    private static class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

        private final List<Integer> items;

        RecipeAdapter(List<Integer> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView img = new ImageView(parent.getContext());
            img.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new RecipeViewHolder(img);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
            holder.imageView.setImageResource(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class RecipeViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            RecipeViewHolder(@NonNull ImageView itemView) {
                super(itemView);
                imageView = itemView;
            }
        }
    }
}