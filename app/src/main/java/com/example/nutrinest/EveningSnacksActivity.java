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

public class EveningSnacksActivity extends AppCompatActivity {

    private Button btnFilter;
    private ViewPager2 recipePager;
    private RecipeAdapter adapter;
    private final List<Integer> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evening_snacks);

        btnFilter = findViewById(R.id.btnFilter);
        recipePager = findViewById(R.id.recipePager);

        // -------- Evening Snacks Images --------
        recipes.add(R.drawable.esnack1); // Potato Wedges (~200)
        recipes.add(R.drawable.esnack2); // Veg Roll (~260)
        recipes.add(R.drawable.esnack3); // Mushroom (~230)
        recipes.add(R.drawable.esnack4); // Chicken Wrap (~240)
        recipes.add(R.drawable.esnack5); // Banana Toast (~280)

        adapter = new RecipeAdapter(recipes);
        recipePager.setAdapter(adapter);

        recipePager.setOffscreenPageLimit(recipes.size());

        // -------- FILTER BUTTON --------
        btnFilter.setOnClickListener(v -> {
            android.widget.PopupMenu popup = new android.widget.PopupMenu(this, btnFilter);
            popup.getMenu().add("Lowest → Highest Calories");
            popup.getMenu().add("Highest → Lowest Calories");

            popup.setOnMenuItemClickListener(item -> {
                String title = item.getTitle().toString();
                List<Integer> sorted = new ArrayList<>();

                if (title.equals("Lowest → Highest Calories")) {
                    sorted.add(R.drawable.esnack1); // ~200
                    sorted.add(R.drawable.esnack3); // ~230
                    sorted.add(R.drawable.esnack4); // ~240
                    sorted.add(R.drawable.esnack2); // ~260
                    sorted.add(R.drawable.esnack5); // ~280
                } else if (title.equals("Highest → Lowest Calories")) {
                    sorted.add(R.drawable.esnack5);
                    sorted.add(R.drawable.esnack2);
                    sorted.add(R.drawable.esnack4);
                    sorted.add(R.drawable.esnack3);
                    sorted.add(R.drawable.esnack1);
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

    // -------- Adapter (SAME AS YOURS) --------
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