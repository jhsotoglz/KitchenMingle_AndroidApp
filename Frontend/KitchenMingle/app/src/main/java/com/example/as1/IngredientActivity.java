package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.as1.model.Ingredient;
import com.example.as1.model.SlimCallback;
import java.util.List;


public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        TextView apiText1 = findViewById(R.id.activity_main_textView1);

        apiText1.setMovementMethod(new ScrollingMovementMethod());
        apiText1.setHeight(350);

        Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
        EditText ingredientNameIn = findViewById(R.id.activity_main_ingredientname_editText);
        EditText quantityIn = findViewById(R.id.activity_main_quantity_editText);

        RegenerateAllIngredientsOnScreen(apiText1);

        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setIngredientName(ingredientNameIn.getText().toString());

                // Retrieve the quantity as a string from the EditText
                String quantityText = quantityIn.getText().toString();

                int quantity = Integer.parseInt(quantityText);
                newIngredient.setQuantity(quantity);

                // Make the API call with the newIngredient
                GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
                    RegenerateAllIngredientsOnScreen(apiText1);
                    ingredientNameIn.setText("");
                    quantityIn.setText("");
                }));
            }
        });
    }

    void RegenerateAllIngredientsOnScreen(TextView apiText1) {
        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
            apiText1.setText("");

            for (int i = ingredients.size() - 1; i >= 0; i--) {
                apiText1.append(ingredients.get(i).printable());
            }
        }, "GetAllIngredients"));
    }
}

//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_ingredient);
//
//
//            TextView apiText1 = findViewById(R.id.activity_main_textView1);
//
//
//            apiText1.setMovementMethod(new ScrollingMovementMethod());
//            apiText1.setHeight(350);
//
//
//            Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
//            EditText ingredientNameIn = findViewById(R.id.activity_main_recipename_editText);
//            EditText quantityIn = findViewById(R.id.activity_main_quantity_editText);
//

         //   RegenerateAllIngredientsOnScreen(apiText1);
//            PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Ingredient newIngredient = new Ingredient();
//                    newIngredient.setIngredientName(ingredientNameIn.getText().toString());
//                    //newIngredient.setQuantity(quantityIn.getInputType());
//
//
//                    // Call the API to add the new ingredient
//                    GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new Callback<Ingredient>() {
//                        @Override
//                        public void onResponse(retrofit2.Call<Ingredient> call, retrofit2.Response<Ingredient> response) {
//                            if (response.isSuccessful()) {
//                                // Handle the successful response here, if needed
//                                RegenerateAllIngredientsOnScreen(apiText1);
//                                ingredientNameIn.setText("");
//                                quantityIn.setText("");
//                            } else {
//                                // Handle the error response here, if the POST request was not successful
//                                // You can check the response code and error message
//                            }
//
//
//                        }
//
//
//                        @Override
//                        public void onFailure(retrofit2.Call<Ingredient> call, Throwable t) {
//
//
//                        }
//
//
//                    });
//
//
//                    String ingredientName = ingredientNameIn.getText().toString().trim();
//
//
//                    if (!ingredientName.isEmpty()) {
//                        // Clear the input field
//                        ingredientNameIn.setText("");
//                        quantityIn.setText("");
//                    }
//                }
//            });
//

//        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Ingredient newIngredient = new Ingredient();
//                newIngredient.setIngredientName(ingredientNameIn.getText().toString());
//                newIngredient.setQuantity(quantityIn.getInputType());
//                GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient ->{
//                    RegenerateAllIngredientsOnScreen(apiText1);
//                    ingredientNameIn.setText("");
//                    quantityIn.setText("");
//
//
//                }));
//                String ingredientName = ingredientNameIn.getText().toString().trim();
//
//                if (!ingredientName.isEmpty()) {
//
//                    // Clear the input field
//                    ingredientNameIn.setText("");
//                    quantityIn.setText("");
//                }
//
//            }
//        });
//
//
//        }
//
//
//        void RegenerateAllIngredientsOnScreen(TextView apiText1) {
//            GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
//                apiText1.setText("");
//
//
//                for (int i = ingredients.size() - 1; i >= 0; i--) {
//                    apiText1.append(ingredients.get(i).printable());
//                }
//            }, "GetAllIngredients"));
//        }
//
//
//    }
//

//            ////////////////////////////////

//    private EditText quantityEditText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ingredient);
//
//        TextView apiText1 = findViewById(R.id.activity_main_textView1);
//
//        apiText1.setMovementMethod(new ScrollingMovementMethod());
//        apiText1.setHeight(350);
//
//        Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
//        EditText ingredientNameIn = findViewById(R.id.activity_main_recipename_editText);
//        EditText quantityEditText = findViewById(R.id.activity_main_quantity_editText);
//
//
//        RegenerateAllIngredientsOnScreen(apiText1);
//
//        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the ingredient name from the EditText field
//                String ingredientName = ingredientNameIn.getText().toString().trim();
//                // Get the quantity from the EditText field
//                String quantityString = quantityEditText.getText().toString().trim();
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
//                    GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
//                        //addDataRow(ingredient.getIngredientName(), ingredient.getQuantity());
//                        RegenerateAllIngredientsOnScreen(apiText1);
//                    }));
//
//                    // Clear the input fields
//                    ingredientNameIn.setText("");
//                    quantityEditText.setText("");
//                }
//            }
//        });
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
//
//    }
//
//
//
//    void RegenerateAllIngredientsOnScreen(TextView apiText1) {
//        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients -> {
//            //apiText1.setText("");
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
////                Ingredient ingredient = ingredients.get(i); // new
////                String ingredientInfo = ingredient.getIngredientName() + " Quantity: " + ingredient.getQuantity(); //new
////                //apiText1.append(ingredients.get(i).printable());
////                apiText1.append(ingredientInfo + "\n");
//            }
//        }, "GetAllIngredients"));
//    }
//
//
//}
//
//
