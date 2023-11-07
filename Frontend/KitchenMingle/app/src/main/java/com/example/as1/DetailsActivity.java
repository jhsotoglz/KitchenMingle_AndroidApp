package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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

        // Retrieve recipe information from the Intent
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipe_name");


        // Set the recipe name in the TextView
        recipeNameTextView.setText(recipeName);



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
    }
}









