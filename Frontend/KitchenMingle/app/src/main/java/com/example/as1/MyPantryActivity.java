//package com.example.as1;
//
//
//import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.method.ScrollingMovementMethod;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TableLayout;
//import android.widget.TextView;
//import android.widget.TableRow;
//import com.example.as1.model.Ingredient;
//import com.example.as1.model.SlimCallback;
//import java.util.List;
//
//
//
//
//public class IngredientActivity extends AppCompatActivity {
//
//
//    private TableLayout tableLayout;
//    private EditText quantityEditText;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ingredient);
//
//
//        TextView apiText1 = findViewById(R.id.activity_main_textView1);
//
//
//        apiText1.setMovementMethod(new ScrollingMovementMethod());
//        apiText1.setHeight(350);
//
//
//        Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
//        EditText ingredientNameIn = findViewById(R.id.activity_main_recipename_editText);
//        quantityEditText = findViewById(R.id.activity_main_quantity_editText);
//
//        RegenerateAllIngredientsOnScreen(apiText1);
//
//
////        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                // Get the ingredient name from the EditText field
////                String ingredientName = ingredientNameIn.getText().toString().trim();
////                // Get the quantity from the EditText field
////                String quantityString = quantityEditText.getText().toString();
////
////
////                if (!ingredientName.isEmpty() && !quantityString.isEmpty()) {
////                    int quantity = Integer.parseInt(quantityString);
////                    // Create a new Ingredient and send it to the server
////                    Ingredient newIngredient = new Ingredient();
////                    newIngredient.setIngredientName(ingredientName);
////                    newIngredient.setQuantity(quantity);
////
////
////                    GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
////                        //addDataRow(ingredient.getIngredientName(), ingredient.getQuantity());
////                        RegenerateAllIngredientsOnScreen(apiText1);
////                    }));
////
////
////                    // Clear the input fields
////                    ingredientNameIn.setText("");
////                    quantityEditText.setText("");
////                }
////            }
////        });
//
//
//
//
////        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Ingredient newIngredient = new Ingredient();
////                newIngredient.setIngredientName(ingredientNameIn.getText().toString());
////                GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient ->{
////                    RegenerateAllIngredientsOnScreen(apiText1);
////                    ingredientNameIn.setText("");
////
////
////                }));
////                String ingredientName = ingredientNameIn.getText().toString().trim();
////
////                if (!ingredientName.isEmpty()) {
////                    // Create a new row for the ingredient
////
////                    // Clear the input field
////                    ingredientNameIn.setText("");
////                }
////
////            }
////        });
////
////
////    }
////
//
//
//    void RegenerateAllIngredientsOnScreen(TextView apiText1) {
//        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
//            apiText1.setText("");
//
//
//            for (int i = ingredients.size() - 1; i >= 0; i--) {
//                Ingredient ingredient = ingredients.get(i);
//                String ingredientName = ingredient.getIngredientName();
//                int quantity = ingredient.getQuantity();
//                String ingredientInfo = ingredientName + " Quantity: " + quantity;
//
//                // Display the existing ingredients in the TextView
//                apiText1.append(ingredientInfo + "\n");
//
//
////                Ingredient ingredient = ingredients.get(i); // new
////                String ingredientInfo = ingredient.getIngredientName() + " Quantity: " + ingredient.getQuantity(); //new
////                //apiText1.append(ingredients.get(i).printable());
////                apiText1.append(ingredientInfo + "\n");
//            }
//        }, "GetAllIngredients"));
//    }
//
//
//
//
//}
//
//
//
//
//
package com.example.as1;




import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as1.model.Ingredient;
import com.example.as1.model.SlimCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/**
 *
 * This class represents the activity for managing pantry ingredients.
 * It allows users to view, add, and manage ingredients in their pantry.
 */
public class MyPantryActivity extends AppCompatActivity {




//    private EditText quantityEditText;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ingredient);
//
//
//        TextView apiText1 = findViewById(R.id.activity_main_textView1);
//
//
//        apiText1.setMovementMethod(new ScrollingMovementMethod());
//       tentapiText1.setHeight(350);
//
//
//        Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
//        EditText ingredientNameIn = findViewById(R.id.activity_main_recipename_editText);
//        quantityEditText = findViewById(R.id.activity_main_quantity_editText);
//
//
//
//
//        RegenerateAllIngredientsOnScreen(apiText1);
//
//
//        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the ingredient name from the EditText field
//                String ingredientName = ingredientNameIn.getText().toString().trim();
//                // Get the quantity from the EditText field
//                String quantityString = quantityEditText.getText().toString();
//
//
//                if (!ingredientName.isEmpty() && !quantityString.isEmpty()) {
//                    int quantity = Integer.parseInt(quantityString);
//
//                    // Create a new Ingredient and send it to the server
//                    Ingredient newIngredient = new Ingredient();
//                    newIngredient.setIngredientName(ingredientName);
//                    newIngredient.setQuantity(quantity);
//
//
//
//
//                    GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
//                        //addDataRow(ingredient.getIngredientName(), ingredient.getQuantity());
//                        RegenerateAllIngredientsOnScreen(apiText1);
//                    }));
//
//
//                    // Clear the input fields
//                    ingredientNameIn.setText("");
//                    quantityEditText.setText("");
//                }
//            }
//        });
//
//
//
//
////        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Ingredient newIngredient = new Ingredient();
////                newIngredient.setIngredientName(ingredientNameIn.getText().toString());
////                GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient ->{
////                    RegenerateAllIngredientsOnScreen(apiText1);
////                    ingredientNameIn.setText("");
////
////
////                }));
////                String ingredientName = ingredientNameIn.getText().toString().trim();
////
////                if (!ingredientName.isEmpty()) {
////                    // Create a new row for the ingredient
////                    addDataRow(ingredientName);
////
////                    // Clear the input field
////                    ingredientNameIn.setText("");
////                }
////
////            }
////        });
//
//
//    }
//
//
//    void RegenerateAllIngredientsOnScreen(TextView apiText1) {
//        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
//            apiText1.setText("");
//
//
//            for (int i = ingredients.size() - 1; i >= 0; i--) {
//                Ingredient ingredient = ingredients.get(i);
//                String ingredientName = ingredient.getIngredientName();
//                int quantity = ingredient.getQuantity();
//                String ingredientInfo = ingredientName + " " + quantity;
//
//                // Display the existing ingredients in the TextView
//                apiText1.append(ingredientInfo + "\n");
//
//
////                Ingredient ingredient = ingredients.get(i); // new
////                String ingredientInfo = ingredient.getIngredientName() + " Quantity: " + ingredient.getQuantity(); //new
////                //apiText1.append(ingredients.get(i).printable());
////                apiText1.append(ingredientInfo + "\n");
//            }
//        }, "GetAllIngredients"));
//    }
//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypantry);


        TextView txtView_IngList = findViewById(R.id.txtView_IngList);
        Button addIngrBtn = findViewById(R.id.addIngrBtn);
        EditText eTxt_ingr = findViewById(R.id.eTxt_ingr);
        EditText eTxt_quantity = findViewById(R.id.eTxt_quantity);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.nav_pantry);

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    startActivity(new Intent(MyPantryActivity.this, DiscoverActivity.class));
                    return true;
                case R.id.nav_favorites:
                    startActivity(new Intent(MyPantryActivity.this, FavoritesActivity.class));
                    return true;
                case R.id.nav_pantry:
                    return true;
            }
            return false;
        });

        RegenerateAllIngredientOnScreen(txtView_IngList);


        addIngrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the EditText fields
                String ingredientName = eTxt_ingr.getText().toString();
                String quantityText = eTxt_quantity.getText().toString();

                try {
                    // Parse the text to an integer
                    int quantity = Integer.parseInt(quantityText);

                    // Create a new Ingredient object
                    Ingredient newIngredient = new Ingredient();
                    newIngredient.setIngredientName(ingredientName);
                    newIngredient.setQuantity(quantity);
                    // Clear the EditText fields after a successful post
                    eTxt_ingr.setText("");
                    eTxt_quantity.setText("");


                    // Make a POST request to create the new Ingredient
                    GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
                        // Callback for success
                        RegenerateAllIngredientOnScreen(txtView_IngList);

                        // Clear the EditText fields after a successful post
                        eTxt_ingr.setText("");
                        eTxt_quantity.setText("");
                    }));
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid integer
                    // For example, display an error message to the user
                    Toast.makeText(MyPantryActivity.this, "Invalid quantity. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String quantityString = quantityIn.getText().toString();
////                int quantity = Integer.parseInt(quantityString);
//
//                Ingredient newIngredient = new Ingredient();
//                newIngredient.setIngredientName(ingredientNameIn.getText().toString());
//                newIngredient.setQuantity(quantityIn);
//                GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient->{
//                    RegenerateAllIngredientOnScreen(apiText1);
//                    ingredientNameIn.setText("");
//                    quantityIn.setText("");
//                }));
//            }
//        });
    }


    /**
     * Refreshes the UI by fetching and displaying all pantry ingredients.
     *
     * @param txtView_IngList The TextView where the ingredient list is displayed.
     */
    void RegenerateAllIngredientOnScreen(TextView txtView_IngList){
        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
            txtView_IngList.setText("");

            for(int i = ingredients.size() - 1; i >= 0; i--){

                txtView_IngList.append(ingredients.get(i).getIngredientName() + " " + ingredients.get(i).getQuantity() + "\n");
            }
        }, "GetAllIngredient"));
    }






}











