package com.example.smartpantry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class RecipeMatcher {

    private static final double TOLERANCE = 1e-6;
    private static final Map<String, String> UNIT_GROUPS = new HashMap<>();
    private static final Map<String, Double> UNIT_FACTORS = new HashMap<>();

    static {
        register("mass", 1.0, "g", "gram", "grams");
        register("mass", 1000.0, "kg", "kgs", "kilogram", "kilograms");
        register("mass", 0.001, "mg", "milligram", "milligrams");
        register("mass", 28.3495, "oz", "ounce", "ounces");
        register("mass", 453.592, "lb", "lbs", "pound", "pounds");
        register("volume", 1.0, "ml", "millilitre", "millilitres", "milliliter", "milliliters");
        register("volume", 1000.0, "l", "litre", "litres", "liter", "liters");
        register("volume", 5.0, "tsp", "teaspoon", "teaspoons");
        register("volume", 15.0, "tbsp", "tablespoon", "tablespoons");
        register("volume", 240.0, "cup", "cups");
        register("count", 1.0, "pcs", "pc", "piece", "pieces", "unit", "units", "whole", "each", "");
    }

    private RecipeMatcher() {
    }

    private static void register(String group, double factor, String... aliases) {
        for (String alias : aliases) {
            UNIT_GROUPS.put(alias, group);
            UNIT_FACTORS.put(alias, factor);
        }
    }

    public static String normalizeUnit(String rawUnit) {
        if (rawUnit == null) {
            return "";
        }
        String unit = rawUnit.trim().toLowerCase(Locale.ROOT);
        if (unit.endsWith(".")) {
            unit = unit.substring(0, unit.length() - 1);
        }
        return unit;
    }

    public static String unitGroup(String rawUnit) {
        String unit = normalizeUnit(rawUnit);
        String group = UNIT_GROUPS.get(unit);
        return group != null ? group : "other:" + unit;
    }

    public static double toBaseQuantity(double quantity, String rawUnit) {
        Double factor = UNIT_FACTORS.get(normalizeUnit(rawUnit));
        return quantity * (factor != null ? factor : 1.0);
    }

    public static String singularize(String word) {
        int length = word.length();
        if (length > 3 && word.endsWith("ies")) {
            return word.substring(0, length - 3) + "y";
        }
        if (length > 3 && word.endsWith("oes")) {
            return word.substring(0, length - 2);
        }
        if (length > 4 && (word.endsWith("ches") || word.endsWith("shes")
                || word.endsWith("sses") || word.endsWith("xes"))) {
            return word.substring(0, length - 2);
        }
        if (length > 2 && word.endsWith("s") && !word.endsWith("ss") && !word.endsWith("us")) {
            return word.substring(0, length - 1);
        }
        return word;
    }

    public static String normalizeName(String rawName) {
        if (rawName == null) {
            return "";
        }
        String cleaned = rawName.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
        if (cleaned.isEmpty()) {
            return "";
        }
        int lastSpace = cleaned.lastIndexOf(' ');
        String head = lastSpace >= 0 ? cleaned.substring(0, lastSpace + 1) : "";
        String lastWord = lastSpace >= 0 ? cleaned.substring(lastSpace + 1) : cleaned;
        return head + singularize(lastWord);
    }

    private static String key(String name, String unit) {
        return normalizeName(name) + "|" + unitGroup(unit);
    }

    public static Map<String, Double> buildPantryTotals(List<PantryItem> pantry) {
        Map<String, Double> totals = new HashMap<>();
        for (PantryItem item : pantry) {
            String itemKey = key(item.getName(), item.getUnit());
            double base = toBaseQuantity(item.getQuantity(), item.getUnit());
            Double existing = totals.get(itemKey);
            totals.put(itemKey, (existing != null ? existing : 0.0) + base);
        }
        return totals;
    }

    public static boolean canMake(Recipe recipe, Map<String, Double> pantryTotals) {
        if (recipe.getIngredients().isEmpty()) {
            return false;
        }
        for (RecipeIngredient needed : recipe.getIngredients()) {
            Double available = pantryTotals.get(key(needed.getName(), needed.getUnit()));
            if (available == null) {
                return false;
            }
            double required = toBaseQuantity(needed.getQuantity(), needed.getUnit());
            if (available + TOLERANCE < required) {
                return false;
            }
        }
        return true;
    }

    public static List<Recipe> findStrictMatches(List<Recipe> recipes, List<PantryItem> pantry) {
        Map<String, Double> totals = buildPantryTotals(pantry);
        List<Recipe> matches = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (canMake(recipe, totals)) {
                matches.add(recipe);
            }
        }
        return matches;
    }
}
