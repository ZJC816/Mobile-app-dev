package com.example.smartpantry;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseNavActivity extends AppCompatActivity {

    protected void setupBottomNav(int selectedId) {
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setSelectedItemId(selectedId);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == selectedId) {
                return true;
            }
            Class<?> target;
            if (id == R.id.nav_pantry) {
                target = PantryListActivity.class;
            } else if (id == R.id.nav_recipes) {
                target = SuggestedRecipesActivity.class;
            } else {
                target = SettingsActivity.class;
            }
            Intent intent = new Intent(this, target);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            if (selectedId != R.id.nav_pantry) {
                finish();
            }
            return true;
        });
    }
}
