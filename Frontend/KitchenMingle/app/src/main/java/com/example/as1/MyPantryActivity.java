package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;
import android.widget.LinearLayout;
import android.view.LayoutInflater;

/**
 *
 * This class represents the activity for managing pantry ingredients.
 * It allows users to view, add, and manage ingredients in their pantry.
 */
public class MyPantryActivity extends AppCompatActivity {
    private Map<String, Integer> pantry = new HashMap<>(); // Mock data structure for pantry items
    private LinearLayout ingredientsListLayout;
    private Long userId; // stores user ID from login
    private int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypantry);

        // Get intent from login
        Intent intent = getIntent();

        // Get user ID from intent
        userId = intent.getLongExtra("USER_ID", -1); // -1 as default

        // Check if userId is valid
        if (userId == -1) {
            // todo: handle case if id isn't properly passed
            // maybe redirect back to login or show an error message
        }

//        userId = setUserId;

        AutoCompleteTextView autoCompleteIngredient = findViewById(R.id.autoCompleteIngredient);
        EditText eTxtQuantity = findViewById(R.id.eTxt_quantity);
        Button addIngrBtn = findViewById(R.id.addIngrBtn);
        ingredientsListLayout = findViewById(R.id.ingredientsListLayout);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.nav_pantry);
        Button btnGoToRecipeSearch = findViewById(R.id.btnGoToRecipeSearch);

        btnGoToRecipeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                Intent intent = new Intent(MyPantryActivity.this, RecipeSearchActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    Intent discoverIntent = new Intent(MyPantryActivity.this, PickRecipeActivity.class);
                    discoverIntent.putExtra("USER_ID", userId);
                    startActivity(discoverIntent);
                    return true;
                case R.id.nav_favorites:
                    Intent favoritesIntent = new Intent(MyPantryActivity.this, FavoritesActivity.class);
                    favoritesIntent.putExtra("USER_ID", userId);
                    startActivity(favoritesIntent);
                    return true;
                case R.id.nav_pantry:
                    return true;
            }
            return false;
        });

        // Add ingredient button setup
        addIngrBtn.setOnClickListener(view -> {
            String ingredientName = autoCompleteIngredient.getText().toString();
            try {
                quantity = Integer.parseInt(eTxtQuantity.getText().toString());
                // Call the method to add the ingredient to the pantry
                addIngredientToPantry(ingredientName, quantity);
            } catch (NumberFormatException e) {
                Toast.makeText(MyPantryActivity.this, "Invalid quantity. Please enter a valid number.", Toast.LENGTH_SHORT).show();
            }
        });

        updateIngredientList();
    }

    // Method to add ingredient to the pantry
    private void addIngredientToPantry(String ingredientName, int quantity) {
        // TODO: Make API call to add the ingredient to the server

        if (pantry.containsKey(ingredientName)) {
            // If we already have this ingredient, just update the quantity
            pantry.put(ingredientName, pantry.get(ingredientName) + quantity);
        } else {
            // If it's a new ingredient, add it to the pantry and create a new view for it
            pantry.put(ingredientName, quantity);
            View ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
            setupIngredientView(ingredientView, ingredientName, quantity);
            ingredientsListLayout.addView(ingredientView);
        }

        // Update the UI after adding or updating the ingredient
        updateIngredientList();
    }



    private void addIngredient(String ingredientName, int quantity) {
        //  GetPantryIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
//        PantryIngredientApi pantryIngredientApi = ApiClientFactory.GetPantryIngredientAPI();
//
//            Call<String> call = pantryIngredientApi.addPantryIngredient(ingredientName);
//            Call<String> call2 = pantryIngredientApi.setQuantity(quantity);

        //     call.enqueue(new Callback<String>() {
        // }

//        newIngredient.setIngredientName(ingredientName);
//        newIngredient.setQuantity(quantity);
//        GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
//
//        }

        if (pantry.containsKey(ingredientName)) {
            // If we already have this ingredient, just update the quantity
            // todo: utilize setQuantity
            pantry.put(ingredientName, pantry.get(ingredientName) + quantity);
        } else {
            // todo: utilize addPantryIngredient and setQuantity
            // If it's a new ingredient, add it to the pantry and create a new view for it
            pantry.put(ingredientName, quantity);
            View ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
            setupIngredientView(ingredientView, ingredientName, quantity);
            ingredientsListLayout.addView(ingredientView);
        }
        // Update the UI after adding or updating the ingredient
        updateIngredientList();
    }

    private void setupIngredientView(View view, String ingredientName, int quantity) {
        TextView tvIngredientName = view.findViewById(R.id.tvIngredientName);
        TextView tvQuantity = view.findViewById(R.id.tvQuantity);
        Button btnDecrement = view.findViewById(R.id.btnDecrement);
        Button btnIncrement = view.findViewById(R.id.btnIncrement);

        tvIngredientName.setText(ingredientName);
        tvQuantity.setText(String.valueOf(quantity));

        btnDecrement.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(tvQuantity.getText().toString());
            if (currentQuantity > 1) {
                //todo: decrementQuantity
                pantry.put(ingredientName, currentQuantity - 1);
                tvQuantity.setText(String.valueOf(currentQuantity - 1));
            } else {
                //todo: deleteIngredient
                pantry.remove(ingredientName);
                ingredientsListLayout.removeView(view);
            }
        });

        btnIncrement.setOnClickListener(v -> {
            // todo: incrementQuantity/setQuantity/addPantryIngredient
            int currentQuantity = Integer.parseInt(tvQuantity.getText().toString());
            pantry.put(ingredientName, currentQuantity + 1);
            tvQuantity.setText(String.valueOf(currentQuantity + 1));
        });
    }

    /*
        @GET("ingredient/all")
    Call<List<PantryIngredient>> GetAllIngredients();

     GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
//            txtView_IngList.setText("");
//
//            for(int i = ingredients.size() - 1; i >= 0; i--){
//
//                txtView_IngList.append(ingredients.get(i).getIngredientName() + " " + ingredients.get(i).getQuantity() + "\n");
//            }
    /
     */
    // Todo: Add ingredients from backend
    /**
     * Refreshes the UI by fetching and displaying all pantry ingredients.
     */
    private void updateIngredientList() {
        // TODO: Make API call to get all pantry ingredients from the server
//        PantryIngredientApi pantryIngredientApi = ApiClientFactory.GetPantryIngredientAPI();

//        pantryIngredientApi.GetAllIngredients().enqueue(new SlimCallback<List<PantryIngredient>>(ingredients -> {
            // Clear the current list
//            ingredientsListLayout.removeAllViews();
//
//            // Iterate over the received ingredients and update the UI
//            for (PantryIngredient ingredient : ingredients) {
//                View ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
//                setupIngredientView(ingredientView, ingredient.getIngredientName(), ingredient.getQuantity());
//                ingredientsListLayout.addView(ingredientView);
//            }
//        }));
//        GetPantryAPI().getPantryForUser(userId).enqueue(new SlimCallback<Pantry>(pantry -> {
            // Clear the current list
            ingredientsListLayout.removeAllViews();
//
//            // Retrieve the set of ingredients from the pantry
//            Set<Ingredient> pantryIngredients = pantry.getPantryIngredients();
//
//            // Loop through the pantry ingredients and update ingredient list
//            for (Ingredient ingredient : pantryIngredients) {
//                String ingredientName = ingredient.getName();
//                int quantity = 1; // fixme
//
//                View ingredientView = LayoutInflater.from(MyPantryActivity.this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
//                setupIngredientView(ingredientView, ingredientName, quantity);
//                ingredientsListLayout.addView(ingredientView);
//            }
//        }));
       // GetPantryAPI().getPantryForUser().enqueue(new SlimCallback<List<PantryIngredient>>(ingredients -> {
            //for(int i = ingredients.size() - 1; i >= 0; i--){
            // txtView_IngList.append(ingredients.get(i).getIngredientName() + " " + ingredients.get(i).getQuantity() + "\n");
//        GetPantryAPI().getPantryForUser().enqueue(new SlimCallback<List<Pantry>>(ingredients -> {
//            ingredientsListLayout.removeAllViews(); // Clear the current list
//            for (PantryIngredient ingredient : ingredients) {
//                View ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
//                setupIngredientView(ingredientView, ingredient.getIngredientName(), ingredient.getQuantity());
//                ingredientsListLayout.addView(ingredientView);
//            }
//        }, "GetAllIngredients"));

        for (Map.Entry<String, Integer> entry : pantry.entrySet()) {
            View ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
            setupIngredientView(ingredientView, entry.getKey(), entry.getValue());
            ingredientsListLayout.addView(ingredientView);
        }
  //     }));
    }






    // RegenerateAllIngredientOnScreen(txtView_IngList);


//        addIngrBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the text from the EditText fields
//                String ingredientName = eTxt_ingr.getText().toString();
//                String quantityText = eTxt_quantity.getText().toString();
//
//               try {
//                    // Parse the text to an integer
//                    int quantity = Integer.parseInt(quantityText);
//
//                    // Create a new Ingredient object
//                    Ingredient newIngredient = new Ingredient();
//                    newIngredient.setIngredientName(ingredientName);
//                    newIngredient.setQuantity(quantity);
//                    // Clear the EditText fields after a successful post
//                    eTxt_ingr.setText("");
//                    eTxt_quantity.setText("");
//
//
//                    // Make a POST request to create the new Ingredient
//                    GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
//                        // Callback for success
//                        RegenerateAllIngredientOnScreen(txtView_IngList);
//
//                        // Clear the EditText fields after a successful post
//                        eTxt_ingr.setText("");
//                        eTxt_quantity.setText("");
//                    }));
//                } catch (NumberFormatException e) {
//                    // Handle the case where the input is not a valid integer
//                    // For example, display an error message to the user
//                    Toast.makeText(MyPantryActivity.this, "Invalid quantity. Please enter a valid number.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//    /**
//     * Refreshes the UI by fetching and displaying all pantry ingredients.
//     *
//     * @param txtView_IngList The TextView where the ingredient list is displayed.
//     */
//    void RegenerateAllIngredientOnScreen(TextView txtView_IngList){
//        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
//            txtView_IngList.setText("");
//
//            for(int i = ingredients.size() - 1; i >= 0; i--){
//
//                txtView_IngList.append(ingredients.get(i).getIngredientName() + " " + ingredients.get(i).getQuantity() + "\n");
//            }
//        }, "GetAllIngredient"));
//    }
}















//package com.example.as1;
//
//import static com.example.as1.api.ApiClientFactory.GetPantryIngredientAPI;
//import androidx.appcompat.app.AppCompatActivity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.AutoCompleteTextView;
//
//import com.example.as1.model.PantryIngredient;
//import com.example.as1.model.SlimCallback;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import java.util.List;
//import java.util.HashMap;
//import java.util.Map;
//import android.widget.LinearLayout;
//import android.view.LayoutInflater;
//
///**
// *
// * This class represents the activity for managing pantry ingredients.
// * It allows users to view, add, and manage ingredients in their pantry.
// */
//public class MyPantryActivity extends AppCompatActivity {
//    private Map<String, Integer> pantry = new HashMap<>(); // Mock data structure for pantry items
//    private LinearLayout ingredientsListLayout;
//    private Long userId; // stores user ID from login
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mypantry);
//
//        // Get intent from login
//        Intent intent = getIntent();
//
//        // Get user ID from intent
//        userId = intent.getLongExtra("USER_ID", -1); // -1 as default
//
//        // Check if userId is valid
//        if (userId == -1) {
//            // todo: handle case if id isn't properly passed
//            // maybe redirect back to login or show an error message
//        }
//
//        AutoCompleteTextView autoCompleteIngredient = findViewById(R.id.autoCompleteIngredient);
//        EditText eTxtQuantity = findViewById(R.id.eTxt_quantity);
//        Button addIngrBtn = findViewById(R.id.addIngrBtn);
//        ingredientsListLayout = findViewById(R.id.ingredientsListLayout);
//        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
//        bottomNavigation.setSelectedItemId(R.id.nav_pantry);
//        Button btnGoToRecipeSearch = findViewById(R.id.btnGoToRecipeSearch);
//
//        btnGoToRecipeSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle button click
//                Intent intent = new Intent(MyPantryActivity.this, RecipeSearchActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_discover:
//                    Intent discoverIntent = new Intent(MyPantryActivity.this, DiscoverActivity.class);
//                    discoverIntent.putExtra("USER_ID", userId);
//                    startActivity(discoverIntent);
//                    return true;
//                case R.id.nav_favorites:
//                    Intent favoritesIntent = new Intent(MyPantryActivity.this, FavoritesActivity.class);
//                    favoritesIntent.putExtra("USER_ID", userId);
//                    startActivity(favoritesIntent);
//                    return true;
//                case R.id.nav_pantry:
//                    return true;
//            }
//            return false;
//        });
//
//        // Add ingredient button setup
//        addIngrBtn.setOnClickListener(view -> {
//            String ingredientName = autoCompleteIngredient.getText().toString();
//            try {
//                int quantity = Integer.parseInt(eTxtQuantity.getText().toString());
//                addIngredient(ingredientName, quantity);
//            } catch (NumberFormatException e) {
//                Toast.makeText(MyPantryActivity.this, "Invalid quantity. Please enter a valid number.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        updateIngredientList();
//    }
//
//
//    private void addIngredient(String ingredientName, int quantity) {
//        //  GetPantryIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
////        PantryIngredientApi pantryIngredientApi = ApiClientFactory.GetPantryIngredientAPI();
////
////            Call<String> call = pantryIngredientApi.addPantryIngredient(ingredientName);
////            Call<String> call2 = pantryIngredientApi.setQuantity(quantity);
//
//        //     call.enqueue(new Callback<String>() {
//        // }
//
////        newIngredient.setIngredientName(ingredientName);
////        newIngredient.setQuantity(quantity);
////        GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
////
////        }
//
//        if (pantry.containsKey(ingredientName)) {
//            // If we already have this ingredient, just update the quantity
//            // todo: utilize setQuantity
//            pantry.put(ingredientName, pantry.get(ingredientName) + quantity);
//        } else {
//            // todo: utilize addPantryIngredient and setQuantity
//            // If it's a new ingredient, add it to the pantry and create a new view for it
//            pantry.put(ingredientName, quantity);
//            View ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
//            setupIngredientView(ingredientView, ingredientName, quantity);
//            ingredientsListLayout.addView(ingredientView);
//        }
//        // Update the UI after adding or updating the ingredient
//        updateIngredientList();
//    }
//
//    private void setupIngredientView(View view, String ingredientName, int quantity) {
//        TextView tvIngredientName = view.findViewById(R.id.tvIngredientName);
//        TextView tvQuantity = view.findViewById(R.id.tvQuantity);
//        Button btnDecrement = view.findViewById(R.id.btnDecrement);
//        Button btnIncrement = view.findViewById(R.id.btnIncrement);
//
//        tvIngredientName.setText(ingredientName);
//        tvQuantity.setText(String.valueOf(quantity));
//
//        btnDecrement.setOnClickListener(v -> {
//            int currentQuantity = Integer.parseInt(tvQuantity.getText().toString());
//            if (currentQuantity > 1) {
//                pantry.put(ingredientName, currentQuantity - 1);
//                tvQuantity.setText(String.valueOf(currentQuantity - 1));
//            } else {
//                pantry.remove(ingredientName);
//                ingredientsListLayout.removeView(view);
//            }
//        });
//
//        btnIncrement.setOnClickListener(v -> {
//            int currentQuantity = Integer.parseInt(tvQuantity.getText().toString());
//            pantry.put(ingredientName, currentQuantity + 1);
//            tvQuantity.setText(String.valueOf(currentQuantity + 1));
//        });
//    }
//
//    /*
//        @GET("ingredient/all")
//    Call<List<PantryIngredient>> GetAllIngredients();
//
//     GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
////            txtView_IngList.setText("");
////
////            for(int i = ingredients.size() - 1; i >= 0; i--){
////
////                txtView_IngList.append(ingredients.get(i).getIngredientName() + " " + ingredients.get(i).getQuantity() + "\n");
////            }
//    /
//     */
//    // Todo: Add ingredients from backend
//    private void updateIngredientList() {
//        ingredientsListLayout.removeAllViews(); // Clear the current list
//       // GetPantryIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<PantryIngredient>>(ingredients -> {
//            //for(int i = ingredients.size() - 1; i >= 0; i--){
//            // txtView_IngList.append(ingredients.get(i).getIngredientName() + " " + ingredients.get(i).getQuantity() + "\n");
//        GetPantryIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<PantryIngredient>>(ingredients -> {
//            ingredientsListLayout.removeAllViews(); // Clear the current list
//            for (PantryIngredient ingredient : ingredients) {
//                View ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
//                setupIngredientView(ingredientView, ingredient.getIngredientName(), ingredient.getQuantity());
//                ingredientsListLayout.addView(ingredientView);
//            }
//        }, "GetAllIngredients"));
////            for (Map.Entry<String, Integer> entry : pantry.entrySet()) {
////                View ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, ingredientsListLayout, false);
////                setupIngredientView(ingredientView, entry.getKey(), entry.getValue());
////                ingredientsListLayout.addView(ingredientView);
////            }
//  //     }));
//    }
//
//    // RegenerateAllIngredientOnScreen(txtView_IngList);
//
//
////        addIngrBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                // Get the text from the EditText fields
////                String ingredientName = eTxt_ingr.getText().toString();
////                String quantityText = eTxt_quantity.getText().toString();
////
////               try {
////                    // Parse the text to an integer
////                    int quantity = Integer.parseInt(quantityText);
////
////                    // Create a new Ingredient object
////                    Ingredient newIngredient = new Ingredient();
////                    newIngredient.setIngredientName(ingredientName);
////                    newIngredient.setQuantity(quantity);
////                    // Clear the EditText fields after a successful post
////                    eTxt_ingr.setText("");
////                    eTxt_quantity.setText("");
////
////
////                    // Make a POST request to create the new Ingredient
////                    GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
////                        // Callback for success
////                        RegenerateAllIngredientOnScreen(txtView_IngList);
////
////                        // Clear the EditText fields after a successful post
////                        eTxt_ingr.setText("");
////                        eTxt_quantity.setText("");
////                    }));
////                } catch (NumberFormatException e) {
////                    // Handle the case where the input is not a valid integer
////                    // For example, display an error message to the user
////                    Toast.makeText(MyPantryActivity.this, "Invalid quantity. Please enter a valid number.", Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
//
////    /**
////     * Refreshes the UI by fetching and displaying all pantry ingredients.
////     *
////     * @param txtView_IngList The TextView where the ingredient list is displayed.
////     */
////    void RegenerateAllIngredientOnScreen(TextView txtView_IngList){
////        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
////            txtView_IngList.setText("");
////
////            for(int i = ingredients.size() - 1; i >= 0; i--){
////
////                txtView_IngList.append(ingredients.get(i).getIngredientName() + " " + ingredients.get(i).getQuantity() + "\n");
////            }
////        }, "GetAllIngredient"));
////    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
