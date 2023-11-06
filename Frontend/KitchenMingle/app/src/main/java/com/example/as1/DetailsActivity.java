package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;
import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import android.widget.EditText;
import android.widget.Button;
import okio.ByteString;

public class DetailsActivity extends AppCompatActivity {
    private OkHttpClient client;
    private WebSocket webSocket;
    private TextView commentsTextView;
    private EditText commentEditText;
    private Button sendCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize views
        commentsTextView = findViewById(R.id.commentsRecyclerView);
        commentEditText = findViewById(R.id.commentEditText);
        sendCommentButton = findViewById(R.id.sendCommentButton);

        TextView ingredientsListTextView = findViewById(R.id.ingredientsListTextView);
        TextView directionsTextView = findViewById(R.id.directionsTextView);
        TextView recipeNameTextView;


        // Retrieve recipe information from the Intent
        Intent intent = getIntent();
        int recipeId = intent.getIntExtra("recipe_id", -1);
        String recipeName = intent.getStringExtra("recipe_name");
        String ingredients = intent.getStringExtra("ingredients");
        String directions = intent.getStringExtra("directions");

        recipeNameTextView = findViewById(R.id.recipeName);

        // Set the recipe name in the TextView
        recipeNameTextView.setText(recipeName);

        // Set the ingredients in the ingredientsListTextView
        ingredientsListTextView.setText(ingredients);

        // Set the directions in the directionsTextView
        directionsTextView.setText(directions);

        sendCommentButton.setOnClickListener(v -> {
            String comment = commentEditText.getText().toString();
            if (!comment.isEmpty()) {
                sendComment(comment);
            }
        });


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
                .url("http://coms-309-033.class.las.iastate.edu:8080/comment/{username}")
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                // Handle incoming WebSocket messages (ratings) here
                runOnUiThread(() -> {
                    // Update the UI with the received rating information
                    // Update the UI with the received comment
                    commentsTextView.append("\n" + text);
                });
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                // WebSocket connection is closed
            }
        });

    }
    private void sendComment(String comment) {
        // Send the comment through the WebSocket
        if (webSocket != null) {
            webSocket.send(comment);
        }
    }

    private void displayIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            // Create a TextView for each ingredient and add it to the layout
            TextView textView = new TextView(this);
            textView.setText(ingredient.getIngredientName());
             //ingredientListLayout.addView(textView);
        }
    }
}









