//package com.example.as1;
//
//import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;
//import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.as1.model.Ingredient;
//import com.example.as1.model.Recipe;
//import java.util.List;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.WebSocket;
//import okhttp3.WebSocketListener;
//import android.widget.EditText;
//import android.widget.Button;
//import okio.ByteString;
//
//public class DetailsActivity extends AppCompatActivity {
//    private OkHttpClient client;
//    private WebSocket webSocket;
//    private TextView commentsTextView;
//    private EditText commentEditText;
//    private Button sendCommentButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details);
//
//        // Initialize views
//        commentsTextView = findViewById(R.id.commentsRecyclerView);
//        commentEditText = findViewById(R.id.commentEditText);
//        sendCommentButton = findViewById(R.id.sendCommentButton);
//        TextView ingredientsListTextView = findViewById(R.id.ingredientsListTextView);
//        TextView directionsTextView = findViewById(R.id.directionsTextView);
//        TextView recipeNameTextView = findViewById(R.id.recipeName);
//
//
//        // Retrieve recipe information from the Intent
//        Intent intent = getIntent();
//        int recipeId = intent.getIntExtra("recipe_id", -1);
//        String recipeName = intent.getStringExtra("recipe_name");
//        String ingredients = intent.getStringExtra("ingredients");
//        String directions = intent.getStringExtra("directions");
//
//        recipeNameTextView = findViewById(R.id.recipeName);
//
//        // Set the recipe name in the TextView
//        recipeNameTextView.setText(recipeName);
//
//        // Set the ingredients in the ingredientsListTextView
//        ingredientsListTextView.setText(ingredients);
//
//        // Set the directions in the directionsTextView
//        directionsTextView.setText(directions);
//
//        sendCommentButton.setOnClickListener(v -> {
//            String comment = commentEditText.getText().toString();
//            if (!comment.isEmpty()) {
//                sendComment(comment);
//            }
//        });
//
//        // Retrofit code to fetch ingredients from the backend
//        Call<List<Ingredient>> call = GetIngredientAPI().getIngredientsForRecipe(recipeId); // Pass the recipe ID
//
//        call.enqueue(new Callback<List<Ingredient>>() {
//            @Override
//            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
//                if (response.isSuccessful()) {
//                    List<Ingredient> ingredients = response.body();
//                    String ingredientsText = formatIngredients(ingredients);
//                    ingredientsListTextView.setText(ingredientsText);
//                } else {
//                    // Handle API error
//                    Log.e("API Error", "Failed to retrieve ingredients: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
//                // Handle network or other errors
//                Log.e("API Error", "Failed to retrieve ingredients: " + t.getMessage());
//            }
//        });
//
//
////        // Retrofit code to fetch ingredients from the backend
////        Call<List<Ingredient>> call = GetIngredientAPI().getIngredientsForRecipe();
////
////        call.enqueue(new Callback<List<Ingredient>>() {
////            @Override
////            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
////                if (response.isSuccessful()) {
////                    List<Ingredient> ingredients = response.body();
////                    displayIngredients(ingredients);
////                } else {
////                    // Handle API error
////                    Log.e("API Error", "Failed to retrieve ingredients: " + response.message());
////                }
////            }
////
////
////            @Override
////            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
////                // Handle network or other errors
////                Log.e("API Error", "Failed to retrieve ingredients: " + t.getMessage());
////            }
////        });
////
////        Call<Recipe> call1 = GetRecipeAPI().getRecipeByName(recipeName);
////
////        call1.enqueue(new Callback<Recipe>() {
////            @Override
////            public void onResponse(Call<Recipe> call1, Response<Recipe> response) {
////                if (response.isSuccessful()) {
////                    Recipe recipe = response.body();
////                    // Check if the Recipe class has a getRecipeInstructions() method that returns a single String
////                    if (recipe != null) {
////                        String directions = recipe.getRecipeInstructions();
//////                        List<String> directionsList = Collections.singletonList(directions);
//////                        displayDirections(directionsList);
////
////                    }
////                } else {
////                    // Handle API error
////                }
////            }
////
////
////            @Override
////            public void onFailure(Call<Recipe> call, Throwable t) {
////                // Handle network or other errors
////            }
////        });
//
//        // Create a WebSocket client
//        client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://coms-309-033.class.las.iastate.edu:8080/comment/{username}")
//                .build();
//
//        webSocket = client.newWebSocket(request, new WebSocketListener() {
//            public void onMessage(WebSocket webSocket, String text) {
//                super.onMessage(webSocket, text);
//                // Handle incoming WebSocket messages (ratings) here
//                runOnUiThread(() -> {
//                    // Update the UI with the received rating information
//                    // Update the UI with the received comment
//                    commentsTextView.append("\n" + text);
//                });
//            }
//
//            @Override
//            public void onClosed(WebSocket webSocket, int code, String reason) {
//                super.onClosed(webSocket, code, reason);
//                // WebSocket connection is closed
//            }
//        });
//
//    }
//    private void sendComment(String comment) {
//        // Send the comment through the WebSocket
//        if (webSocket != null) {
//            webSocket.send(comment);
//        }
//    }
//
//    private void displayIngredients(List<Ingredient> ingredients) {
//        for (Ingredient ingredient : ingredients) {
//            // Create a TextView for each ingredient and add it to the layout
//            TextView textView = new TextView(this);
//            textView.setText(ingredient.getIngredientName());
//             //ingredientListLayout.addView(textView);
//        }
//    }
//
//    private String formatIngredients(List<Ingredient> ingredients) {
//        StringBuilder formattedIngredients = new StringBuilder();
//
//        for (Ingredient ingredient : ingredients) {
//            formattedIngredients.append(ingredient.getIngredientName()).append("\n");
//        }
//
//        return formattedIngredients.toString();
//    }
//
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

package com.example.as1;

<<<<<<< HEAD
import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;
=======
>>>>>>> 66a54517829cf65b2191aefab884032b95c5d89d
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
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
=======
>>>>>>> 66a54517829cf65b2191aefab884032b95c5d89d
import android.widget.EditText;
import android.widget.Button;
import android.widget.RatingBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import com.example.as1.model.Comment;
import com.example.as1.api.WebSocketListener;
import java.util.List;
import org.java_websocket.handshake.ServerHandshake;




public class DetailsActivity extends AppCompatActivity implements WebSocketListener {
    private TextView commentText, ingredientListLayout, directionsListLayout, recipeNameTextView, commentUserName;
    private EditText commentEditText, userIdEditText, recipeIdEditText;
    private Button sendCommentButton, connectBtn;
    private RatingBar ratingBar, commentRatingBar;
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();

    private String BASE_URL = "ws://coms-309-033.class.las.iastate.edu:8080/comment/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize views
        // commentText = findViewById(R.id.commentText);
        commentEditText = findViewById(R.id.commentEditText);
        sendCommentButton = findViewById(R.id.sendCommentButton);
<<<<<<< HEAD
        TextView ingredientsListTextView = findViewById(R.id.ingredientsListTextView);
        TextView directionsTextView = findViewById(R.id.directionsTextView);
        TextView recipeNameTextView = findViewById(R.id.recipeName);
=======
        recipeNameTextView = findViewById(R.id.recipeName);
        userIdEditText = findViewById(R.id.userIdEditText);
        recipeIdEditText = findViewById(R.id.recipeIdEditText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        connectBtn = findViewById(R.id.connectBtn);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(commentList);
        commentsRecyclerView.setAdapter(commentAdapter);

        // Fetch comments and update the RecyclerView
        loadComments();
>>>>>>> 66a54517829cf65b2191aefab884032b95c5d89d

        // Retrieve recipe information from the Intent
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipe_name");
<<<<<<< HEAD
        String ingredients = intent.getStringExtra("ingredients");
        String directions = intent.getStringExtra("directions");
=======

>>>>>>> 66a54517829cf65b2191aefab884032b95c5d89d

        // Set the recipe name in the TextView
        recipeNameTextView.setText(recipeName);

<<<<<<< HEAD
        // Set the ingredients in the ingredientsListTextView
        ingredientsListTextView.setText(ingredients);

        // Set the directions in the directionsTextView
        directionsTextView.setText(directions);

=======


        /* connect button listener */
        connectBtn.setOnClickListener(view -> {
            String userID = userIdEditText.getText().toString();
            String recipeID = recipeIdEditText.getText().toString();
            String serverUrl = BASE_URL + userID + "/" + recipeID; // TODO: Pull ID's instead of dummy variables

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(this);
        });


        /* send button listener */
>>>>>>> 66a54517829cf65b2191aefab884032b95c5d89d
        sendCommentButton.setOnClickListener(v -> {
            try {
                float rating = ratingBar.getRating();
                String message = commentEditText.getText().toString();
                String review = rating + message;
                WebSocketManager.getInstance().sendMessage(review);
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });

<<<<<<< HEAD
        // Retrofit code to fetch ingredients from the backend
        Call<List<Ingredient>> call = GetIngredientAPI().getIngredientsForRecipe(recipeId); // Pass the recipe ID

        call.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                if (response.isSuccessful()) {
                    List<Ingredient> ingredients = response.body();
                    String ingredientsText = formatIngredients(ingredients);
                    ingredientsListTextView.setText(ingredientsText);
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

    private String formatIngredients(List<Ingredient> ingredients) {
        StringBuilder formattedIngredients = new StringBuilder();

        for (Ingredient ingredient : ingredients) {
            formattedIngredients.append(ingredient.getIngredientName()).append("\n");
        }

        return formattedIngredients.toString();
=======
//
//
//
//        call.enqueue(new Callback<List<Ingredient>>() {
//            @Override
//            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
//                if (response.isSuccessful()) {
//                    List<Ingredient> ingredients = response.body();
//                    displayIngredients(ingredients);
//                } else {
//                    // Handle API error
//                    Log.e("API Error", "Failed to retrieve ingredients: " + response.message());
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
//                // Handle network or other errors
//                Log.e("API Error", "Failed to retrieve ingredients: " + t.getMessage());
//            }
//        });
//
//        Call<Recipe> call1 = GetRecipeAPI().getRecipeByName(recipeName);
//
//        call1.enqueue(new Callback<Recipe>() {
//            @Override
//            public void onResponse(Call<Recipe> call1, Response<Recipe> response) {
//                if (response.isSuccessful()) {
//                    Recipe recipe = response.body();
//                    // Check if the Recipe class has a getRecipeInstructions() method that returns a single String
//                    if (recipe != null) {
//                        String directions = recipe.getRecipeInstructions();
////                        List<String> directionsList = Collections.singletonList(directions);
////                        displayDirections(directionsList);
//
//                    }
//                } else {
//                    // Handle API error
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<Recipe> call, Throwable t) {
//                // Handle network or other errors
//            }
//        });
//
//        webSocket = client.newWebSocket(request, new WebSocketListener() {
//            public void onMessage(WebSocket webSocket, String text) {
//                super.onMessage(webSocket, text);
//                // Handle incoming WebSocket messages (ratings) here
//                runOnUiThread(() -> {
//                    // Update the UI with the received rating information
//                    // Update the UI with the received comment
//                    commentsTextView.append("\n" + text);
//                });
//            }
//
//            @Override
//            public void onClosed(WebSocket webSocket, int code, String reason) {
//                super.onClosed(webSocket, code, reason);
//                // WebSocket connection is closed
//            }
//        });

    }

    /*
     * On page start, WebSocket begins listening
     */
//    @Override
//    protected void onStart() {
//        super.onStart();
//        String userID = userIdEditText.getText().toString();
//        String recipeID = recipeIdEditText.getText().toString();
//        String serverUrl = BASE_URL + userID + "/" + recipeID; // TODO: Pull ID's instead of dummy variables
//
//        WebSocketManager.getInstance().connectWebSocket(serverUrl);
//        WebSocketManager.getInstance().setWebSocketListener(this);
//    }

    /*
     * On page close, WebSocket stops listening
     */
    @Override
    protected void onStop() {
        super.onStop();
        WebSocketManager.getInstance().disconnectWebSocket();
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
//        runOnUiThread(() -> {
//            String currentText = msgTv.getText().toString();
//            String closedBy = remote ? "server" : "local";
//            msgTv.setText(String.format("%s---\nConnection closed by %s\nReason: %s", currentText, closedBy, reason));
//        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        // logic for when the connection is opened
    }

    @Override
    public void onWebSocketError(Exception ex) {
        // logic to handle web socket errors
    }

    @Override
    public void onWebSocketMessage(String message) {
        // logic to handle messages
        // FIXME: send messages to recycler thing
    }


//    private void sendComment(String comment) {
//        // Send the comment through the WebSocket
//        if (webSocket != null) {
//            webSocket.send(comment);
//        }
//    }

//    private void displayIngredients(List<Ingredient> ingredients) {
//        for (Ingredient ingredient : ingredients) {
//            // Create a TextView for each ingredient and add it to the layout
//            TextView textView = new TextView(this);
//            textView.setText(ingredient.getIngredientName());
//            // ingredientListLayout.addView(textView);
//        }
//    }


    private void loadComments() {
        // TODO: Load (real) previously commented variables
        // FIXME: recycler view??
//        setContentView(R.layout.comment_item);
//        commentText = findViewById(R.id.commentText);
//        commentRatingBar = findViewById(R.id.commentRatingBar);
//        commentUserName = findViewById(R.id.commentUserName);

        runOnUiThread(() -> {
            commentList.add(new Comment("User1", "This is a great recipe!", 5));
            commentList.add(new Comment("User2", "Thanks for sharing!", 4));
            commentAdapter.notifyDataSetChanged();
        });
>>>>>>> 66a54517829cf65b2191aefab884032b95c5d89d
    }
}

