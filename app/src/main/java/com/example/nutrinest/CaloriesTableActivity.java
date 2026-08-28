package com.example.nutrinest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent; // ⭐ ADDED (THIS WAS MISSING)
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class CaloriesTableActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private LinearLayout mealLayout;
    private Button btnCalculator;
    private Button btnMealTiming; // ⭐ ADDED
    private TextView headerTitle;
    private ImageView backBtn;

    private boolean notificationEnabled = false;
    private boolean notificationShown = false;
    private double limit = 500;

    private static final String CHANNEL_ID = "calorie_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_table);

        tableLayout = findViewById(R.id.tableLayout);
        mealLayout = findViewById(R.id.mealLayout);
        btnCalculator = findViewById(R.id.btnCalculator);
        btnMealTiming = findViewById(R.id.btnMealTiming); // ⭐ ADDED
        headerTitle = findViewById(R.id.headerTitle);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> finish());

        createNotificationChannel();
        requestNotificationPermission();

        setupMealButtons();

        // ⭐ NEW BUTTON ACTION
        btnMealTiming.setOnClickListener(v -> {
            Intent intent = new Intent(CaloriesTableActivity.this, MealTimingActivity.class);
            startActivity(intent);
        });

        btnCalculator.setOnClickListener(v -> askNotification());
    }

    // ---------------- NOTIFICATION CHANNEL ----------------
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifsound);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Calorie Alerts",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("Alerts when calorie limit is exceeded");
            channel.setSound(soundUri, audioAttributes);
            channel.enableVibration(true);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
    }

    // ---------------- MEAL BUTTONS ----------------
    private void setupMealButtons() {

        Button btnBreakfast = findViewById(R.id.btnBreakfast);
        Button btnLunch = findViewById(R.id.btnLunch);
        Button btnSnacks = findViewById(R.id.btnSnacks);
        Button btnDinner = findViewById(R.id.btnDinner);

        btnBreakfast.setOnClickListener(v -> {
            headerTitle.setText("Breakfast");
            mealLayout.setVisibility(View.GONE);
            tableLayout.setVisibility(View.VISIBLE);
            loadMeal("Breakfast");
        });

        btnLunch.setOnClickListener(v -> {
            headerTitle.setText("Lunch");
            mealLayout.setVisibility(View.GONE);
            tableLayout.setVisibility(View.VISIBLE);
            loadMeal("Lunch");
        });

        btnSnacks.setOnClickListener(v -> {
            headerTitle.setText("Snacks");
            mealLayout.setVisibility(View.GONE);
            tableLayout.setVisibility(View.VISIBLE);
            loadMeal("Snacks");
        });

        btnDinner.setOnClickListener(v -> {
            headerTitle.setText("Dinner");
            mealLayout.setVisibility(View.GONE);
            tableLayout.setVisibility(View.VISIBLE);
            loadMeal("Dinner");
        });
    }

    // ---------------- REST OF YOUR CODE (UNCHANGED) ----------------
    private void loadMeal(String meal){
        tableLayout.removeAllViews();
        addHeader();

        if(meal.equals("Breakfast")){
            limit = 400;
            addRow("Oats (40g)", "150");
            addRow("Milk (100ml)", "60");
            addRow("Banana", "90");
            addRow("Apple", "50");
            addRow("Sweet Potato (100g)", "86");
            addRow("Onion", "20");
            addRow("Lemon", "5");
            addRow("Egg Whites (2)", "34");
            addRow("Onion", "20");
            addRow("Tomato", "10");
            addRow("Bread (2)", "140");
            addRow("Paneer (50g)", "145");
            addRow("Butter", "36");
            addRow("Corn (100g)", "96");
            addRow("Cucumber", "16");
            addRow("Tomato", "10");
        }

        else if(meal.equals("Lunch")){
            limit = 610;
            addRow("Paneer (60g)", "180");
            addRow("Tomato Gravy", "80");
            addRow("Butter (1 tsp)", "45");
            addRow("Oil (1 tsp)", "45");
            addRow("Cream (1 tbsp)", "50");
            addRow("Roti (2)", "200");
            addRow("Rice (1 cup)", "200");
            addRow("Jeera + Ghee", "50");
            addRow("Dal", "150");
            addRow("Oil", "45");
            addRow("Garlic + Spices", "20");
            addRow("Chicken (100g)", "165");
            addRow("Curd Marinade", "40");
            addRow("Oil", "45");
            addRow("Whole Wheat Wrap", "150");
            addRow("Onion + Capsicum", "30");
            addRow("Mint Chutney", "20");
            addRow("Whole Wheat Pasta", "220");
            addRow("Tomato Sauce", "80");
            addRow("Veggies", "80");
            addRow("Cheese", "60");
            addRow("Oil", "45");
            addRow("Rice", "200");
            addRow("Rajma", "220");
            addRow("Oil", "45");
            addRow("Onion + Tomato Gravy", "50");
        }

        else if(meal.equals("Snacks")){
            limit = 300;
            addRow("Potato (150g)", "130");
            addRow("Olive Oil (1 tsp)", "40");
            addRow("Chilli Powder", "5");
            addRow("Salt", "0");
            addRow("Oregano", "5");
            addRow("Whole Wheat Roti", "150");
            addRow("Capsicum", "20");
            addRow("Onion", "20");
            addRow("Cabbage", "15");
            addRow("Green Chutney", "30");
            addRow("Oil (1 tsp)", "45");
            addRow("Mushroom", "20");
            addRow("Cheese (20g)", "80");
            addRow("Garlic", "5");
            addRow("Pepper", "2");
            addRow("Olive Oil (1 tsp)", "40");
            addRow("Chicken (100g)", "165");
            addRow("Lettuce", "10");
            addRow("Light Sauce", "30");
            addRow("Garlic", "5");
            addRow("Pepper", "2");
            addRow("Brown Bread", "120");
            addRow("Peanut Butter", "95");
            addRow("Banana", "90");
            addRow("Honey", "20");
        }

        else if(meal.equals("Dinner")){
            limit = 400;
            addRow("Chicken breast (100g)", "165");
            addRow("Olive oil (1 tsp)", "40");
            addRow("Garlic", "5");
            addRow("Broccoli", "30");
            addRow("Carrot", "25");
            addRow("Beans", "20");
            addRow("Spices + pepper", "5");
            addRow("Paneer (50g)", "145");
            addRow("Spinach", "40");
            addRow("Garlic", "5");
            addRow("Onion", "20");
            addRow("Tomato", "20");
            addRow("Oil", "45");
            addRow("Roti", "150");
            addRow("Mixed vegetables", "80");
            addRow("Garlic", "5");
            addRow("Soy sauce", "10");
            addRow("Whole wheat noodles", "180");
            addRow("Oil", "45");
            addRow("Fish (100g)", "200");
            addRow("Lemon", "5");
            addRow("Garlic", "5");
            addRow("Olive oil", "40");
            addRow("Cucumber", "15");
            addRow("Lettuce", "10");
            addRow("Eggs (2)", "140");
            addRow("Tomato gravy", "60");
            addRow("Onion", "20");
            addRow("Spices", "10");
            addRow("Oil", "45");
            addRow("Roti", "150");
        }
    }

    private void addHeader(){
        TableRow row = new TableRow(this);

        TextView t1 = new TextView(this);
        t1.setText("Ingredient");
        t1.setTextColor(Color.WHITE);
        t1.setBackgroundColor(Color.parseColor("#9C27B0"));
        t1.setPadding(16,16,16,16);
        t1.setGravity(Gravity.CENTER);

        TextView t2 = new TextView(this);
        t2.setText("Calories");
        t2.setTextColor(Color.WHITE);
        t2.setBackgroundColor(Color.parseColor("#9C27B0"));
        t2.setPadding(16,16,16,16);
        t2.setGravity(Gravity.CENTER);

        row.addView(t1);
        row.addView(t2);

        tableLayout.addView(row);
    }

    private void addRow(String item, String cal){
        TableRow row = new TableRow(this);

        TextView t1 = new TextView(this);
        t1.setText(item);
        t1.setPadding(16,16,16,16);
        t1.setTextColor(Color.parseColor("#FF4081"));

        TextView t2 = new TextView(this);
        t2.setText(cal);
        t2.setPadding(16,16,16,16);
        t2.setTextColor(Color.parseColor("#FF4081"));

        row.addView(t1);
        row.addView(t2);

        tableLayout.addView(row);
    }

    private void askNotification(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Enable Notification?");
        b.setMessage("Alert when exceeding calorie limit?");
        b.setPositiveButton("Yes",(d,w)->{
            notificationEnabled = true;
            openCalculator();
        });
        b.setNegativeButton("No",(d,w)->{
            notificationEnabled = false;
            openCalculator();
        });
        b.show();
    }

    private void openCalculator(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_calculator, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText input = view.findViewById(R.id.calcInput);

        int[] ids = {
                R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
                R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9,
                R.id.btnPlus,R.id.btnMinus,R.id.btnMultiply,R.id.btnDivide,
                R.id.btnClear,R.id.btnEquals
        };

        for(int id : ids){
            view.findViewById(id).setOnClickListener(v -> {

                Button b = (Button) v;
                String val = b.getText().toString();

                if(val.equals("C")){
                    input.setText("");
                }
                else if(val.equals("=")){
                    try{
                        double result = eval(input.getText().toString());
                        input.setText(String.valueOf(result));
                        checkLimit(result);
                    }catch(Exception e){
                        input.setText("Error");
                    }
                }
                else{
                    input.append(val);
                }
            });
        }
    }

    private void checkLimit(double result){

        if(notificationEnabled && !notificationShown && result > limit){

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Limit Exceeded ⚠️")
                            .setContentText("You crossed " + limit + " calories!")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);

            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            manager.notify(1001, builder.build());

            notificationShown = true;
        }
    }

    private double eval(String expr){
        return new Object() {
            int pos=-1,ch;
            void nextChar(){ ch=(++pos<expr.length())?expr.charAt(pos):-1; }
            boolean eat(int c){ while(ch==' ') nextChar(); if(ch==c){ nextChar(); return true;} return false;}
            double parse(){ nextChar(); return parseExpression();}
            double parseExpression(){ double x=parseTerm(); for(;;){ if(eat('+')) x+=parseTerm(); else if(eat('-')) x-=parseTerm(); else return x;}}
            double parseTerm(){ double x=parseFactor(); for(;;){ if(eat('*')) x*=parseFactor(); else if(eat('/')) x/=parseFactor(); else return x;}}
            double parseFactor(){
                if(eat('+')) return parseFactor();
                if(eat('-')) return -parseFactor();

                double x;
                int start=pos;
                while((ch>='0' && ch<='9')||ch=='.') nextChar();
                x=Double.parseDouble(expr.substring(start,pos));
                return x;
            }
        }.parse();
    }
}