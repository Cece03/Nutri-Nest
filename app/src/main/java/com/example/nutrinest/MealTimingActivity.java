package com.example.nutrinest;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MealTimingActivity extends AppCompatActivity {

    EditText etBreakfast, etLunch, etSnacks, etDinner;
    Button btnCheck;

    TextView resultBreakfast, resultLunch, resultSnacks, resultDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealtime);

        etBreakfast = findViewById(R.id.etBreakfast);
        etLunch = findViewById(R.id.etLunch);
        etSnacks = findViewById(R.id.etSnacks);
        etDinner = findViewById(R.id.etDinner);

        btnCheck = findViewById(R.id.btnCheck);

        resultBreakfast = findViewById(R.id.resultBreakfast);
        resultLunch = findViewById(R.id.resultLunch);
        resultSnacks = findViewById(R.id.resultSnacks);
        resultDinner = findViewById(R.id.resultDinner);

        // ✅ ADDED CODE (force black text on device)
        resultBreakfast.setTextColor(getResources().getColor(android.R.color.black));
        resultLunch.setTextColor(getResources().getColor(android.R.color.black));
        resultSnacks.setTextColor(getResources().getColor(android.R.color.black));
        resultDinner.setTextColor(getResources().getColor(android.R.color.black));

        etBreakfast.setTextColor(getResources().getColor(android.R.color.black));
        etLunch.setTextColor(getResources().getColor(android.R.color.black));
        etSnacks.setTextColor(getResources().getColor(android.R.color.black));
        etDinner.setTextColor(getResources().getColor(android.R.color.black));

        etBreakfast.setHintTextColor(getResources().getColor(android.R.color.black));
        etLunch.setHintTextColor(getResources().getColor(android.R.color.black));
        etSnacks.setHintTextColor(getResources().getColor(android.R.color.black));
        etDinner.setHintTextColor(getResources().getColor(android.R.color.black));
        // ✅ END

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        btnCheck.setOnClickListener(v -> {

            checkTime(etBreakfast.getText().toString(), "Breakfast", resultBreakfast, 7, 10);
            checkTime(etLunch.getText().toString(), "Lunch", resultLunch, 12, 3);
            checkTime(etSnacks.getText().toString(), "Snacks", resultSnacks, 4, 6);
            checkTime(etDinner.getText().toString(), "Dinner", resultDinner, 7, 10);
        });
    }

    private void checkTime(String input, String meal, TextView result, int start, int end){

        try{
            int time = Integer.parseInt(input);

            if(time >= start && time <= end){
                result.setText(meal + ": ✅ On Time");
            } else {
                result.setText(meal + ": ❌ Not Ideal Time");
            }

        } catch (Exception e){
            result.setText(meal + ": ⚠️ Enter valid hour (1-12)");
        }
    }
}