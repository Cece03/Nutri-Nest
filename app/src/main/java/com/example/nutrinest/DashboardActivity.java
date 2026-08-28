package com.example.nutrinest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ViewPager2 sliderViewPager;
    private Button btnCaloriesTable;
    private Button btnMe; // 🔥 NEW PROFILE BUTTON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sliderViewPager = findViewById(R.id.sliderViewPager);
        btnCaloriesTable = findViewById(R.id.btnCaloriesTable);
        btnMe = findViewById(R.id.btnMe); // 🔥 NEW

        // ⭐ GET EMAIL FROM LOGIN
        String email = getIntent().getStringExtra("email");

        // -------- Slider Images --------
        List<Integer> sliderItems = new ArrayList<>();
        sliderItems.add(R.drawable.firstrect);
        sliderItems.add(R.drawable.breakfastrect);
        sliderItems.add(R.drawable.lunchrect);
        sliderItems.add(R.drawable.eveningrect);
        sliderItems.add(R.drawable.dinnerect);

        sliderViewPager.setAdapter(new SliderAdapter(sliderItems));

        // -------- Category Boxes --------
        findViewById(R.id.boxBreakfast).setOnClickListener(v -> openRecipe("Breakfast"));
        findViewById(R.id.boxLunch).setOnClickListener(v -> openRecipe("Lunch"));
        findViewById(R.id.boxDinner).setOnClickListener(v -> openRecipe("Dinner"));
        findViewById(R.id.boxSnacks).setOnClickListener(v -> openRecipe("Evening Snacks"));

        // -------- Popup Menu (ONLY LOGOUT NOW) --------
        findViewById(R.id.userProfile).setOnClickListener(v -> {
            android.widget.PopupMenu popup = new android.widget.PopupMenu(this, v);

            popup.getMenu().add("Logout");

            popup.setOnMenuItemClickListener(item -> {

                String title = item.getTitle().toString();

                // 🔥 LOGOUT ONLY
                if (title.equals("Logout")) {
                    Intent i = new Intent(this, ProfileActivity.class);
                    i.putExtra("openFragment", "logout");
                    startActivity(i);
                }

                return true;
            });

            popup.show();
        });

        // -------- NEW PROFILE BUTTON (🔥 FIXED HERE ONLY) --------
        btnMe.setOnClickListener(v -> {
            Intent i = new Intent(this, MeActivity.class);
            i.putExtra("email", email); // ✅ ADDED
            startActivity(i);
        });

        // -------- Calories Table --------
        btnCaloriesTable.setOnClickListener(v ->
                startActivity(new Intent(this, CaloriesTableActivity.class)));
    }

    private void openRecipe(String category) {
        if (category.equals("Breakfast")) {
            startActivity(new Intent(this, BreakfastActivity.class));
        } else if (category.equals("Lunch")) {
            startActivity(new Intent(this, LunchActivity.class));
        } else if (category.equals("Evening Snacks")) {
            startActivity(new Intent(this, EveningSnacksActivity.class));
        } else if (category.equals("Dinner")) {
            startActivity(new Intent(this, DinnerActivity.class));
        }
    }

    // -------- Slider Adapter --------
    private static class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

        private final List<Integer> items;

        SliderAdapter(List<Integer> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public SliderViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.MATCH_PARENT
            ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new SliderViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
            holder.imageView.setImageResource(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class SliderViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            SliderViewHolder(@NonNull ImageView view) {
                super(view);
                imageView = view;
            }
        }
    }
}