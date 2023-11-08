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
import java.util.List;
import org.java_websocket.handshake.ServerHandshake;




public class DetailsActivity extends AppCompatActivity implements WebSocketListener {
    private TextView commentText, ingredientsTextView, directionsTextView, recipeNameTextView, commentUserName, btnToPickRecipe;
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
        ratingBar = findViewById(R.id.ratingBar);
        connectBtn = findViewById(R.id.connectBtn);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(commentList);
        commentsRecyclerView.setAdapter(commentAdapter);
        directionsTextView = findViewById(R.id.directionsTextView);
        ingredientsTextView = findViewById(R.id.ingredientsListTextView);
        btnToPickRecipe = findViewById(R.id.btnToPickRecipe1);

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
//
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
    }

    @Override
    public void onWebSocketMessage(String message) {
        // FIXME: send messages to recycler thing
        // TODO: parse string "Received message: user1: 4.5one"

        runOnUiThread(() -> {
            commentList.add(new Comment(message));
            commentAdapter.notifyDataSetChanged();
        });
    }



    private void loadComments() {
        // TODO: Load (real) previously commented variables
        // FIXME: recycler view??
//        setContentView(R.layout.comment_item);
//        commentText = findViewById(R.id.commentText);
//        commentRatingBar = findViewById(R.id.commentRatingBar);
//        commentUserName = findViewById(R.id.commentUserName);
        // commentsTextView.append("\n" + text);

        runOnUiThread(() -> {
         //   commentList.add(new Comment("User1", "This is a great recipe!", 5));
        //    commentAdapter.notifyDataSetChanged();
        });
    }

}


