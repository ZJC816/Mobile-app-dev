package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "quantity REAL NOT NULL, "
                + "unit TEXT NOT NULL, "
                + "expiry TEXT)");

        db.execSQL("CREATE TABLE recipes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "steps TEXT NOT NULL)");

        db.execSQL("CREATE TABLE recipe_ingredients ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "recipe_id INTEGER NOT NULL, "
                + "name TEXT NOT NULL, "
                + "quantity REAL NOT NULL, "
                + "unit TEXT NOT NULL, "
                + "FOREIGN KEY(recipe_id) REFERENCES recipes(id) ON DELETE CASCADE)");

        RecipeSeeder.seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS pantry");
        onCreate(db);
    }

    public long insertPantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("pantry", null, toValues(item));
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.query("pantry", null, null, null, null, null, "name COLLATE NOCASE ASC")) {
            while (cursor.moveToNext()) {
                items.add(fromCursor(cursor));
            }
        }
        return items;
    }

    public PantryItem getPantryItem(long id) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.query("pantry", null, "id = ?", new String[]{String.valueOf(id)},
                null, null, null)) {
            if (cursor.moveToFirst()) {
                return fromCursor(cursor);
            }
        }
        return null;
    }

    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update("pantry", toValues(item), "id = ?", new String[]{String.valueOf(item.getId())});
    }

    public int deletePantryItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("pantry", "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllPantryItems() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("pantry", null, null);
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.query("recipes", null, null, null, null, null, "name COLLATE NOCASE ASC")) {
            while (cursor.moveToNext()) {
                Recipe recipe = recipeFromCursor(cursor);
                loadIngredients(db, recipe);
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    public Recipe getRecipe(long id) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.query("recipes", null, "id = ?", new String[]{String.valueOf(id)},
                null, null, null)) {
            if (cursor.moveToFirst()) {
                Recipe recipe = recipeFromCursor(cursor);
                loadIngredients(db, recipe);
                return recipe;
            }
        }
        return null;
    }

    private void loadIngredients(SQLiteDatabase db, Recipe recipe) {
        try (Cursor cursor = db.query("recipe_ingredients", null, "recipe_id = ?",
                new String[]{String.valueOf(recipe.getId())}, null, null, "id ASC")) {
            while (cursor.moveToNext()) {
                recipe.addIngredient(new RecipeIngredient(
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("quantity")),
                        cursor.getString(cursor.getColumnIndexOrThrow("unit"))));
            }
        }
    }

    private Recipe recipeFromCursor(Cursor cursor) {
        return new Recipe(
                cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("steps")));
    }

    private PantryItem fromCursor(Cursor cursor) {
        return new PantryItem(
                cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("quantity")),
                cursor.getString(cursor.getColumnIndexOrThrow("unit")),
                cursor.getString(cursor.getColumnIndexOrThrow("expiry")));
    }

    private ContentValues toValues(PantryItem item) {
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("quantity", item.getQuantity());
        values.put("unit", item.getUnit());
        values.put("expiry", item.getExpiry());
        return values;
    }
}
