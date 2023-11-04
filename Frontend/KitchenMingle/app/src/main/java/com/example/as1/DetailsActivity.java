package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;
import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;
import com.example.as1.model.SlimCallback;


import org.w3c.dom.Text;


import java.util.Collections;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class DetailsActivity extends AppCompatActivity {
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details);
//
//        // Retrieve the recipe ID from the Intent
//        int recipeId = getIntent().getIntExtra("recipe_id", -1); // -1 is a default value in case the extra is not found
//
//        TextView apiText1 = findViewById(R.id.textView1);
//        TextView apiText2 = findViewById(R.id.textView2);
//        TextView apiText3 = findViewById(R.id.textView3);
//
//        apiText1.setMovementMethod(new ScrollingMovementMethod());
//        apiText1.setHeight(350);
//
//
//        RegenerateAllRecipesOnScreen(apiText1);
//
//        PostByPathBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GetRecipeAPI().PostRecipeByPath(recipeNameIn.getText().toString(), instructionIn.getText().toString()).enqueue(new SlimCallback<Recipe>(recipe -> {
//                    RegenerateAllRecipesOnScreen(apiText1);
//                    recipeNameIn.setText("");
//                    instructionIn.setText("");
//                }));
//            }
//        });
//
//        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Recipe newRecipe = new Recipe();
//                newRecipe.setRecipeName(recipeNameIn.getText().toString());
//                newRecipe.setRecipeInstructions(instructionIn.getText().toString());
//                GetRecipeAPI().PostRecipeByBody(newRecipe).enqueue(new SlimCallback<Recipe>(recipe -> {
//                    RegenerateAllRecipesOnScreen(apiText1);
//                    recipeNameIn.setText("");
//                    instructionIn.setText("");
//                }));
//            }
//        });
//    }
//
//    void RegenerateAllRecipesOnScreen(TextView apiText1) {
//        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes -> {
//            apiText1.setText("");
//
//            for (int i = recipes.size() - 1; i >= 0; i--) {
//                apiText1.append(recipes.get(i).printable());
//            }
//        }, "GetAllRecipe"));
//    }
//
//
//
//}
//    private TextView ingredientListLayout;
//    private TextView directionsListLayout;
//    private TextView recipeNameTextView;

    private OkHttpClient client;
    private WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView ingredientListLayout;
        TextView directionsListLayout;
        TextView recipeNameTextView;

        // Retrieve recipe information from the Intent
        Intent intent = getIntent();
        int recipeId = intent.getIntExtra("recipe_id", -1);
        String recipeName = intent.getStringExtra("recipe_name");

        recipeNameTextView = findViewById(R.id.recipeName);
        ingredientListLayout = findViewById(R.id.textView1);
        directionsListLayout = findViewById(R.id.textView2);

        // Set the recipe name in the TextView
        recipeNameTextView.setText(recipeName);

        // Retrofit code to fetch ingredients from the backend
        Call<List<Ingredient>> call = GetIngredientAPI().getIngredientsForRecipe();

        call.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                if (response.isSuccessful()) {
                    List<Ingredient> ingredients = response.body();
                    displayIngredients(ingredients);
                } else {
                    // Handle API error
                    Log.e("API Error", "Failed to retrieve ingredients: " + response.message());
                }
            }


            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                // Handle network or other errors
                Log.e("API Error", "Failed to retrieve ingredients: " + t.getMessage());
            }
        });

        Call<Recipe> call1 = GetRecipeAPI().getRecipeByName(recipeName);
        //Call<Recipe> call1 = GetRecipeAPI().getRecipeByName("yourRecipeName"); // Replace "yourRecipeName" with the actual recipe name you want to retrieve.

        call1.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call1, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Recipe recipe = response.body();
                    // Check if the Recipe class has a getRecipeInstructions() method that returns a single String
                    if (recipe != null) {
                        String directions = recipe.getRecipeInstructions();
//                        List<String> directionsList = Collections.singletonList(directions);
//                        displayDirections(directionsList);

                    }
                } else {
                    // Handle API error
                }
            }


            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                // Handle network or other errors
            }
        });

        // Create a WebSocket client
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("") // Replace with your WebSocket server URL
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                // Handle incoming WebSocket messages (ratings) here
                runOnUiThread(() -> {
                    // Update the UI with the received rating information
                    // For example, update a TextView or a rating widget.
                    // Example: textView.setText("New Rating: " + text);
                });
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                // WebSocket connection is closed
            }
        });

    }


    private void displayIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            // Create a TextView for each ingredient and add it to the layout
            TextView textView = new TextView(this);
            textView.setText(ingredient.getIngredientName());
            // ingredientListLayout.addView(textView);
        }
    }


}









