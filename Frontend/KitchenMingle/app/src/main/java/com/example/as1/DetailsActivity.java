package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import org.java_websocket.handshake.ServerHandshake;

/**
 * DetailsActivity displays details of a recipe, allows users to add live comments and ratings through an established WebSocket connection.
 */
public class DetailsActivity extends AppCompatActivity implements WebSocketListener {
    // Views and UI elements
    private TextView commentText, ingredientsTextView, directionsTextView, recipeNameTextView, commentUserName, btnToPickRecipe;
    private EditText commentEditText, userIdEditText, recipeIdEditText;
    private Button sendCommentButton, connectBtn;
    private RatingBar ratingBar, commentRatingBar;
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();

    // WebSocket server URL
    private String BASE_URL = "ws://coms-309-033.class.las.iastate.edu:8080/comment/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
      //  BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // Initialize views and UI elements
        commentEditText = findViewById(R.id.commentEditText);
        sendCommentButton = findViewById(R.id.sendCommentButton);
        recipeNameTextView = findViewById(R.id.recipeName);
        userIdEditText = findViewById(R.id.userIdEditText);
        recipeIdEditText = findViewById(R.id.recipeIdEditText);
        ratingBar = findViewById(R.id.ratingBar);
        connectBtn = findViewById(R.id.connectBtn);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(commentList);
        commentsRecyclerView.setAdapter(commentAdapter);
        directionsTextView = findViewById(R.id.directionsTextView);
        ingredientsTextView = findViewById(R.id.ingredientsListTextView);
        btnToPickRecipe = findViewById(R.id.btnToPickRecipe1);

        // Set up click listener to go to the pick recipe button
        btnToPickRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DetailsActivity.this, PickRecipeActivity.class);
                startActivity(intent);
            }
        });

        // Fetch comments and update the RecyclerView
        loadComments();

        // Retrieve recipe information from the Intent
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipe_name");
        String ingredients = intent.getStringExtra("ingredients");
        String directions = intent.getStringExtra("directions");

        // Set the recipe name in the TextView
        recipeNameTextView.setText(recipeName);
        directionsTextView.setText(ingredients);
        ingredientsTextView.setText(directions);

        // todo: onStart() make page start listening instead of when clicking button

        // Set up click listener for connect button to establish WebSocket connection
        connectBtn.setOnClickListener(view -> {
            String userID = userIdEditText.getText().toString();
            String recipeID = recipeIdEditText.getText().toString();
            // Appends server url with the rest of the endpoint (/{userID}/{recipeID}/)
            String serverUrl = BASE_URL + userID + "/" + recipeID; // TODO: Pull ID's instead of dummy variables

            // Establish WebSocket connection and set listener
            WebSocketManager.getInstance().connectWebSocket(serverUrl);
            WebSocketManager.getInstance().setWebSocketListener(this);
        });

        // Set up click listener for send comment button to send in a review
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
    }

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
        // Handle WebSocket close event
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        // Handle WebSocket open event
    }

    @Override
    public void onWebSocketError(Exception ex) {
        // Handle WebSocket error
    }

    @Override
    public void onWebSocketMessage(String message) {
        // Handle WebSocket message received event
        float floatingRating = ratingBar.getRating();
        int rating = Math.round(floatingRating);

        runOnUiThread(() -> {
          commentList.add(new Comment(userIdEditText.getText().toString(), message, rating ));
          //  commentList.add(new Comment(userID, text, rating));
               commentAdapter.notifyDataSetChanged();
        });
    }


    /**
     * Method to load previous recipe reviews (not live).
     */
    private void loadComments() {
        // TODO: Load (real) previously commented variables
        // FIXME: recycler view??
//        setContentView(R.layout.comment_item);
//        commentText = findViewById(R.id.commentText);
//        commentRatingBar = findViewById(R.id.commentRatingBar);
//        commentUserName = findViewById(R.id.commentUserName);
        // commentsTextView.append("\n" + text);

        runOnUiThread(() -> {
           // commentList.add(new Comment("User1", "This is a great recipe!", 5));
           // commentAdapter.notifyDataSetChanged();
        });
    }
}


