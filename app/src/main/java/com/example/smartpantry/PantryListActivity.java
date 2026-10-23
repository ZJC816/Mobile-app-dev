package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PantryListActivity extends BaseNavActivity implements PantryAdapter.Listener {

    private DatabaseHelper database;
    private PantryAdapter adapter;
    private TextView emptyView;
    private TextView expiryBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        database = new DatabaseHelper(this);
        emptyView = findViewById(R.id.empty_view);
        expiryBanner = findViewById(R.id.expiry_banner);

        adapter = new PantryAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.pantry_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FloatingActionButton addButton = findViewById(R.id.fab_add);
        addButton.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditIngredientActivity.class)));

        setupBottomNav(R.id.nav_pantry);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

    private void refreshList() {
        List<PantryItem> items = database.getAllPantryItems();
        adapter.setItems(items);
        emptyView.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);

        int expiringCount = 0;
        if (SettingsActivity.isExpiryAlertEnabled(this)) {
            for (PantryItem item : items) {
                if (AppUtils.isExpiringSoon(item.getExpiry())) {
                    expiringCount++;
                }
            }
        }

        if (expiringCount > 0) {
            expiryBanner.setText(expiringCount + (expiringCount == 1 ? " ingredient is" : " ingredients are")
                    + " expiring within " + AppUtils.EXPIRY_WARNING_DAYS + " days or already expired");
            expiryBanner.setVisibility(View.VISIBLE);
        } else {
            expiryBanner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEdit(PantryItem item) {
        Intent intent = new Intent(this, AddEditIngredientActivity.class);
        intent.putExtra(AddEditIngredientActivity.EXTRA_ITEM_ID, item.getId());
        startActivity(intent);
    }

    @Override
    public void onDelete(PantryItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete ingredient")
                .setMessage("Remove " + AppUtils.capitalize(item.getName()) + " from your pantry?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    database.deletePantryItem(item.getId());
                    Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                    refreshList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
