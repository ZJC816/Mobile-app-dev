Smart Pantry Manager

A Java Android application that helps reduce food waste. The user records the ingredients they currently have at home, and the app suggests only the recipes that can be cooked using strictly those ingredients.

Features of the App

- Add, edit and delete pantry items (name, quantity, unit, optional expiry date)
- Pantry list screen backed by a RecyclerView and custom adapter
- 22 pre-loaded recipes, seeded into the database on first run
- Suggested Recipes screen using the strict-matching rule: a recipe appears only if every ingredient is in the pantry in at least the required quantity
- Matching tolerates singular/plural names (tomato / tomatoes) and unit differences (g / kg, ml / l, tsp / tbsp / cup)
- Recipe detail screen with ingredients and method
- Settings screen with an expiring-soon alert toggle and a clear-pantry option
- Friendly message when no recipes match
- Input validation on the ingredient form

Database

SQLite through SQLiteOpenHelper. It runs locally on the device, needs no account or internet connection, and persists data between app launches, which suits a personal pantry tracker.

