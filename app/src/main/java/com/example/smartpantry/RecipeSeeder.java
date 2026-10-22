package com.example.smartpantry;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

final class RecipeSeeder {

    private RecipeSeeder() {
    }

    static void seed(SQLiteDatabase db) {
        add(db, "Scrambled Eggs",
                "1. Crack the eggs into a bowl and whisk with the salt.\n2. Melt the butter in a pan over low heat.\n3. Pour in the eggs and stir gently until just set.",
                new String[][]{{"egg", "3", "pcs"}, {"butter", "10", "g"}, {"salt", "1", "tsp"}});

        add(db, "Cheese Omelette",
                "1. Whisk the eggs.\n2. Melt the butter in a pan and add the eggs.\n3. When almost set, sprinkle over the cheese and fold.\n4. Cook one more minute and serve.",
                new String[][]{{"egg", "3", "pcs"}, {"cheese", "50", "g"}, {"butter", "10", "g"}});

        add(db, "Tomato Pasta",
                "1. Boil the pasta until tender and drain.\n2. Fry the chopped onion and garlic in olive oil.\n3. Add chopped tomatoes and simmer for 15 minutes.\n4. Toss the pasta through the sauce.",
                new String[][]{{"pasta", "200", "g"}, {"tomato", "3", "pcs"}, {"onion", "1", "pcs"}, {"garlic", "2", "pcs"}, {"olive oil", "2", "tbsp"}});

        add(db, "Garlic Butter Rice",
                "1. Cook the rice until fluffy.\n2. Melt the butter and fry the crushed garlic for one minute.\n3. Stir the garlic butter through the rice.",
                new String[][]{{"rice", "200", "g"}, {"butter", "20", "g"}, {"garlic", "2", "pcs"}});

        add(db, "Egg Fried Rice",
                "1. Fry the chopped onion until soft.\n2. Push aside and scramble the eggs in the pan.\n3. Add the cooked rice and soy sauce.\n4. Stir fry for three minutes.",
                new String[][]{{"rice", "300", "g"}, {"egg", "2", "pcs"}, {"onion", "1", "pcs"}, {"soy sauce", "2", "tbsp"}});

        add(db, "Cheese Toast",
                "1. Butter the bread slices.\n2. Top with the cheese.\n3. Grill until golden and bubbling.",
                new String[][]{{"bread", "4", "pcs"}, {"cheese", "80", "g"}, {"butter", "20", "g"}});

        add(db, "French Toast",
                "1. Whisk the eggs, milk and sugar together.\n2. Dip each bread slice into the mixture.\n3. Fry in a pan until golden on both sides.",
                new String[][]{{"bread", "4", "pcs"}, {"egg", "2", "pcs"}, {"milk", "100", "ml"}, {"sugar", "1", "tbsp"}});

        add(db, "Simple Pancakes",
                "1. Mix the flour, sugar, eggs and milk into a smooth batter.\n2. Rest the batter for ten minutes.\n3. Fry ladles of batter in a hot pan, flipping once.",
                new String[][]{{"flour", "200", "g"}, {"milk", "300", "ml"}, {"egg", "2", "pcs"}, {"sugar", "2", "tbsp"}});

        add(db, "Mashed Potatoes",
                "1. Peel and boil the potatoes until soft.\n2. Drain and mash with the butter and milk.\n3. Season with the salt.",
                new String[][]{{"potato", "4", "pcs"}, {"butter", "30", "g"}, {"milk", "100", "ml"}, {"salt", "1", "tsp"}});

        add(db, "Roast Potatoes",
                "1. Heat the oven to 200 C.\n2. Cut the potatoes into chunks and toss in olive oil and salt.\n3. Roast for 40 minutes, turning once.",
                new String[][]{{"potato", "6", "pcs"}, {"olive oil", "3", "tbsp"}, {"salt", "1", "tsp"}});

        add(db, "Vegetable Soup",
                "1. Chop the carrots, potatoes and onion.\n2. Sweat the onion in a pot, then add the other vegetables.\n3. Pour in the stock and simmer for 25 minutes.",
                new String[][]{{"carrot", "2", "pcs"}, {"potato", "2", "pcs"}, {"onion", "1", "pcs"}, {"vegetable stock", "500", "ml"}});

        add(db, "Tomato Soup",
                "1. Fry the chopped onion in the butter.\n2. Add the tomatoes and stock and simmer for 20 minutes.\n3. Blend until smooth.",
                new String[][]{{"tomato", "6", "pcs"}, {"onion", "1", "pcs"}, {"vegetable stock", "500", "ml"}, {"butter", "20", "g"}});

        add(db, "Chicken Rice Bowl",
                "1. Cook the rice.\n2. Slice the chicken and fry with the garlic.\n3. Add the soy sauce and cook through.\n4. Serve over the rice.",
                new String[][]{{"chicken breast", "300", "g"}, {"rice", "200", "g"}, {"soy sauce", "2", "tbsp"}, {"garlic", "2", "pcs"}});

        add(db, "Garlic Lemon Chicken",
                "1. Rub the chicken with olive oil, crushed garlic and lemon juice.\n2. Bake at 190 C for 30 minutes.\n3. Rest for five minutes before serving.",
                new String[][]{{"chicken breast", "400", "g"}, {"garlic", "4", "pcs"}, {"olive oil", "2", "tbsp"}, {"lemon", "1", "pcs"}});

        add(db, "Lemon Tuna Pasta",
                "1. Boil and drain the pasta.\n2. Toss with the tuna, olive oil and a squeeze of lemon juice.\n3. Serve warm or cold.",
                new String[][]{{"pasta", "200", "g"}, {"tuna", "1", "pcs"}, {"olive oil", "1", "tbsp"}, {"lemon", "1", "pcs"}});

        add(db, "Beans on Toast",
                "1. Warm the baked beans in a small pot.\n2. Toast and butter the bread.\n3. Spoon the beans over the toast.",
                new String[][]{{"baked bean", "400", "g"}, {"bread", "2", "pcs"}, {"butter", "10", "g"}});

        add(db, "Banana Oat Porridge",
                "1. Simmer the oats in the milk for five minutes, stirring.\n2. Slice the banana on top.\n3. Drizzle with honey.",
                new String[][]{{"oat", "80", "g"}, {"milk", "300", "ml"}, {"banana", "1", "pcs"}, {"honey", "1", "tbsp"}});

        add(db, "Banana Pancakes",
                "1. Mash the bananas and mix with the eggs and flour.\n2. Fry small spoonfuls in a pan until golden on both sides.",
                new String[][]{{"banana", "2", "pcs"}, {"egg", "2", "pcs"}, {"flour", "50", "g"}});

        add(db, "Egg Fried Noodles",
                "1. Cook the noodles and drain.\n2. Fry the sliced onion, then scramble in the eggs.\n3. Add the noodles and soy sauce and toss well.",
                new String[][]{{"noodle", "200", "g"}, {"egg", "2", "pcs"}, {"soy sauce", "2", "tbsp"}, {"onion", "1", "pcs"}});

        add(db, "Cheesy Pasta Bake",
                "1. Boil the pasta until almost tender.\n2. Melt the butter, stir in the milk and half of the cheese.\n3. Mix with the pasta, top with the remaining cheese and bake at 190 C for 20 minutes.",
                new String[][]{{"pasta", "250", "g"}, {"cheese", "150", "g"}, {"milk", "200", "ml"}, {"butter", "30", "g"}});

        add(db, "Lentil Curry",
                "1. Fry the onion and garlic until soft.\n2. Stir in the curry powder, then add the lentils and chopped tomatoes.\n3. Cover with water and simmer for 25 minutes.",
                new String[][]{{"lentil", "200", "g"}, {"onion", "1", "pcs"}, {"tomato", "2", "pcs"}, {"garlic", "2", "pcs"}, {"curry powder", "2", "tsp"}});

        add(db, "Feta Salad",
                "1. Chop the tomatoes, cucumber and onion.\n2. Crumble the feta cheese over the top.\n3. Dress with olive oil.",
                new String[][]{{"tomato", "3", "pcs"}, {"cucumber", "1", "pcs"}, {"onion", "1", "pcs"}, {"feta cheese", "100", "g"}, {"olive oil", "2", "tbsp"}});
    }

    private static void add(SQLiteDatabase db, String name, String steps, String[][] ingredients) {
        ContentValues recipeValues = new ContentValues();
        recipeValues.put("name", name);
        recipeValues.put("steps", steps);
        long recipeId = db.insert("recipes", null, recipeValues);

        for (String[] ingredient : ingredients) {
            ContentValues values = new ContentValues();
            values.put("recipe_id", recipeId);
            values.put("name", ingredient[0]);
            values.put("quantity", Double.parseDouble(ingredient[1]));
            values.put("unit", ingredient[2]);
            db.insert("recipe_ingredients", null, values);
        }
    }
}
