# Smart Pantry Manager

A Java Android application that helps reduce food waste. The user records the ingredients they currently have at home, and the app suggests only the recipes that can be cooked using strictly those ingredients.

## Features

- Add, edit and delete pantry items (name, quantity, unit, optional expiry date)
- Pantry list screen backed by a RecyclerView and custom adapter
- 22 pre-loaded recipes, seeded into the database on first run
- Suggested Recipes screen using the strict-matching rule: a recipe appears only if every ingredient is in the pantry in at least the required quantity
- Matching tolerates singular/plural names (tomato / tomatoes) and unit differences (g / kg, ml / l, tsp / tbsp / cup)
- Recipe detail screen with ingredients and method
- Settings screen with an expiring-soon alert toggle and a clear-pantry option
- Friendly message when no recipes match
- Input validation on the ingredient form

## Database

SQLite through SQLiteOpenHelper. It runs locally on the device, needs no account or internet connection, and persists data between app launches, which suits a personal pantry tracker.

## Setup and Run

1. Install the latest Android Studio.
2. Choose File > Open and select the SmartPantryManager folder.
3. Wait for the Gradle sync to finish (internet is needed the first time). Accept any prompt to update the Android Gradle Plugin.
4. Create an emulator in Device Manager (any Pixel with API 34 or newer) or connect a phone with USB debugging enabled.
5. Press the green Run button.

## Testing the strict-matching rule

1. Open the Pantry tab and add: Egg 3 pcs, Butter 10 g, Salt 1 tsp.
2. Open the Recipes tab and Scrambled Eggs appears.
3. Return to the pantry and delete Salt. Scrambled Eggs disappears.
