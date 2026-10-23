package com.example.smartpantry;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddEditIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "item_id";

    private DatabaseHelper database;
    private TextInputLayout nameLayout;
    private TextInputLayout quantityLayout;
    private TextInputEditText nameInput;
    private TextInputEditText quantityInput;
    private TextInputEditText expiryInput;
    private Spinner unitSpinner;
    private String[] units;
    private long itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        database = new DatabaseHelper(this);
        nameLayout = findViewById(R.id.name_layout);
        quantityLayout = findViewById(R.id.quantity_layout);
        nameInput = findViewById(R.id.name_input);
        quantityInput = findViewById(R.id.quantity_input);
        expiryInput = findViewById(R.id.expiry_input);
        unitSpinner = findViewById(R.id.unit_spinner);
        Button saveButton = findViewById(R.id.save_button);
        Button cancelButton = findViewById(R.id.cancel_button);
        Button clearExpiryButton = findViewById(R.id.clear_expiry_button);

        units = getResources().getStringArray(R.array.units);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this, R.array.units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);

        itemId = getIntent().getLongExtra(EXTRA_ITEM_ID, -1);
        if (itemId != -1) {
            loadExistingItem();
        }

        expiryInput.setOnClickListener(v -> showDatePicker());
        clearExpiryButton.setOnClickListener(v -> expiryInput.setText(""));
        saveButton.setOnClickListener(v -> save());
        cancelButton.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

    private void loadExistingItem() {
        PantryItem item = database.getPantryItem(itemId);
        if (item == null) {
            Toast.makeText(this, "Ingredient not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setTitle(R.string.title_edit_ingredient);
        nameInput.setText(item.getName());
        quantityInput.setText(AppUtils.formatQuantity(item.getQuantity()));
        if (item.getExpiry() != null) {
            expiryInput.setText(item.getExpiry());
        }
        for (int i = 0; i < units.length; i++) {
            if (units[i].equals(item.getUnit())) {
                unitSpinner.setSelection(i);
                break;
            }
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        String current = textOf(expiryInput);
        if (current.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] parts = current.split("-");
            calendar.set(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) - 1, Integer.parseInt(parts[2]));
        }
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> expiryInput.setText(AppUtils.formatIsoDate(year, month, day)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void save() {
        nameLayout.setError(null);
        quantityLayout.setError(null);

        String name = textOf(nameInput);
        String quantityText = textOf(quantityInput);
        boolean valid = true;

        if (name.isEmpty()) {
            nameLayout.setError("Ingredient name is required");
            valid = false;
        } else if (name.length() > 40) {
            nameLayout.setError("Name must be 40 characters or fewer");
            valid = false;
        } else if (!name.matches("[\\p{L}][\\p{L} '\\-]*")) {
            nameLayout.setError("Use letters, spaces, apostrophes or hyphens only");
            valid = false;
        }

        double quantity = 0;
        if (quantityText.isEmpty()) {
            quantityLayout.setError("Quantity is required");
            valid = false;
        } else {
            try {
                quantity = Double.parseDouble(quantityText);
                if (quantity <= 0 || quantity > 100000) {
                    quantityLayout.setError("Enter a quantity greater than 0 and up to 100000");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                quantityLayout.setError("Enter a valid number");
                valid = false;
            }
        }

        if (!valid) {
            return;
        }

        String unit = unitSpinner.getSelectedItem().toString();
        String expiry = textOf(expiryInput);
        if (expiry.isEmpty()) {
            expiry = null;
        }

        PantryItem item = new PantryItem(itemId, name, quantity, unit, expiry);
        if (itemId == -1) {
            database.insertPantryItem(item);
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
        } else {
            database.updatePantryItem(item);
            Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private String textOf(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }
}
