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
import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TableRow;
import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;
import java.util.List;


import jakarta.persistence.criteria.CriteriaBuilder;




public class IngredientActivity extends AppCompatActivity {




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
        setContentView(R.layout.activity_ingredient);


        TextView apiText1 = findViewById(R.id.activity_main_textView1);


//        apiText1.setMovementMethod(new ScrollingMovementMethod());
//        apiText1.setHeight(350);


        Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
        EditText ingredientNameIn = findViewById(R.id.activity_main_ingredientname_editText);
        EditText quantityIn = findViewById(R.id.activity_main_quantity_editText);


        RegenerateAllIngredientOnScreen(apiText1);


        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                // Extract the ingredient name from the EditText field
//                String ingredientName = ingredientNameIn.getText().toString();
//                // Extract the quantity as a string from the EditText
//                String quantityString = quantityIn.getText().toString();
//
//                if(!ingredientName.isEmpty() && !quantityString.isEmpty()) {
//                    try {
//                        // Parse the quantityString to an integer
//                        int quantity = Integer.parseInt(quantityString);
//
//                        // Create a new Ingredient object
//                        Ingredient newIngredient = new Ingredient();
//                        newIngredient.setIngredientName(ingredientName);
//                        newIngredient.setQuantity(quantity);
//
//                        GetIngredientAPI().PostRecipeByPath(ingredientNameIn.getText().toString().enqueue(new SlimCallback<Recipe>(recipe -> {
//                            RegenerateAllIngredientOnScreen(apiText1);
//                            ingredientNameIn.setText("");
//                            quantityIn.setText("");
//                        }));
//                    } catch (NumberFormatException e) {
//                        // Handle the case where the user entered a non-integer value for quantity
//                        // You can display an error message or take appropriate action
//                    }
//                }
//            }
                Ingredient newIngredient = new Ingredient();
                newIngredient.setIngredientName(ingredientNameIn.getText().toString());
                GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
                    RegenerateAllIngredientOnScreen(apiText1);
                    ingredientNameIn.setText("");
                }));
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


    void RegenerateAllIngredientOnScreen(TextView apiText1){
        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
            apiText1.setText("");


            for(int i = ingredients.size() - 1; i >= 0; i--){
                apiText1.append(ingredients.get(i).printable());
            }
        }, "GetAllIngredient"));
    }






}











