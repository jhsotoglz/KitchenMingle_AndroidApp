package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;
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
import com.example.as1.model.SlimCallback;
import java.util.List;


public class IngredientActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private int rowNumber = 1;
    private int quantity = 0;
    private EditText quantityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        TextView apiText1 = findViewById(R.id.activity_main_textView1);

        apiText1.setMovementMethod(new ScrollingMovementMethod());
        apiText1.setHeight(350);

        Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
        EditText ingredientNameIn = findViewById(R.id.activity_main_recipename_editText);

        tableLayout = findViewById(R.id.tableLayout); // Initialize TableLayout


        RegenerateAllIngredientsOnScreen(apiText1);
        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient newIngredient = new Ingredient();
                //Ingredient newQuantity = new Ingredient();
                String ingredientName = ingredientNameIn.getText().toString();
                String quantityString = quantityEditText.getText().toString();

                if (!quantityString.isEmpty()) {
                    quantity = Integer.parseInt(quantityString);
                    // Set the quantity directly in the Ingredient object
                    newIngredient.setQuantity(quantity);

                    GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient -> {
                        RegenerateAllIngredientsOnScreen(apiText1);
                        ingredientNameIn.setText("");
                        quantityEditText.setText(""); // Clear the quantity field

                        if (!ingredientName.isEmpty() && quantity > 0) {
                            addDataRow(ingredientName, quantity);
                        }
                    }));
                }
            }
        });


//        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Ingredient newIngredient = new Ingredient();
//                newIngredient.setIngredientName(ingredientNameIn.getText().toString());
//                GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient ->{
//                    RegenerateAllIngredientsOnScreen(apiText1);
//                    ingredientNameIn.setText("");
//
//
//                }));
//                String ingredientName = ingredientNameIn.getText().toString().trim();
//
//                if (!ingredientName.isEmpty()) {
//                    // Create a new row for the ingredient
//                    addDataRow(ingredientName);
//
//                    // Clear the input field
//                    ingredientNameIn.setText("");
//                }
//
//            }
//        });

    }

        private void addDataRow (String ingredientName,int quantity){
            TableRow row = new TableRow(this);

            TextView ingredientNameTextView = new TextView(this);
            ingredientNameTextView.setText(ingredientName);

            TextView quantityTextView = new TextView(this);
            quantityTextView.setText(String.valueOf(quantity));

            row.addView(ingredientNameTextView);
            row.addView(quantityTextView);

            tableLayout.addView(row, rowNumber++);
        }


    void RegenerateAllIngredientsOnScreen(TextView apiText1){
        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
            apiText1.setText("");

            for(int i = ingredients.size() - 1; i >= 0; i--){
                apiText1.append(ingredients.get(i).printable());
            }
        }, "GetAllIngredients"));
    }


//    private void addDataRow(String ingredientName) {
//        TableRow row = new TableRow(this);
//
//        TextView ingredientNameTextView = new TextView(this);
//        ingredientNameTextView.setText(ingredientName);
//
//        TextView quantityTextView = new TextView(this);
//        quantityTextView.setText("0");
//
//        Button increaseButton = new Button(this);
//        increaseButton.setText("+");
//        increaseButton.setTag("increase" + rowNumber);
//        increaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                increaseQuantity(view);
//            }
//        });
//
//        Button decreaseButton = new Button(this);
//        decreaseButton.setText("-");
//        decreaseButton.setTag("decrease" + rowNumber);
//        decreaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                decreaseQuantity(view);
//            }
//        });
//
//        row.addView(ingredientNameTextView);
//        row.addView(quantityTextView);
//        row.addView(increaseButton);
//        row.addView(decreaseButton);
//
//        tableLayout.addView(row);
//        rowNumber++; // Increment the row number
//    }


//    public void decreaseQuantity(View view) {
//        TableRow row = (TableRow) view.getParent(); // Get the parent row
//        TextView quantityTextView = row.findViewWithTag("quantityTextView");
//
//        // Get the current quantity as a string
//        String currentQuantityStr = quantityTextView.getText().toString();
//
//        try {
//            int currentQuantity = Integer.parseInt(currentQuantityStr);
//            if (currentQuantity > 0) {
//                currentQuantity--; // Decrement the quantity if it's greater than 0
//                quantityTextView.setText(String.valueOf(currentQuantity)); // Update the TextView
//            }
//        } catch (NumberFormatException e) {
//            // Handle parsing error if the quantity is not a valid integer
//        }
//    }

//    public void increaseQuantity(View view) {
//        TableRow row = (TableRow) view.getParent(); // Get the parent row
//        TextView quantityTextView = row.findViewWithTag("quantityTextView");
//
//        // Get the current quantity as a string
//        String currentQuantityStr = quantityTextView.getText().toString();
//
//        try {
//            int currentQuantity = Integer.parseInt(currentQuantityStr);
//            if (currentQuantity > 0) {
//                currentQuantity++; // Increase the quantity if it's greater than 0
//                quantityTextView.setText(String.valueOf(currentQuantity)); // Update the TextView
//            }
//        } catch (NumberFormatException e) {
//            // Handle parsing error if the quantity is not a valid integer
//        }
//    }


}


