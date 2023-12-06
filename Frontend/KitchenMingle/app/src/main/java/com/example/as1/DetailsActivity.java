//
//
//package com.example.as1;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import android.widget.EditText;
//import android.widget.Button;
//import android.widget.RatingBar;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import java.util.ArrayList;
//
//import com.example.as1.api.UsersApi;
//import com.example.as1.model.Comment;
//import com.example.as1.api.WebSocketListener;
//import com.example.as1.model.Users;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import java.util.List;
//import org.java_websocket.handshake.ServerHandshake;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static com.example.as1.api.ApiClientFactory.GetUsersApi;
//
//
///**
// * DetailsActivity displays details of a recipe, allows users to add live comments and ratings through an established WebSocket connection.
// */
//public class DetailsActivity extends AppCompatActivity implements WebSocketListener {
//    // Views and UI elements
//    private TextView ingredientsTextView, directionsTextView, recipeNameTextView, commentUserName, btnToPickRecipe;
//    private EditText commentEditText, userIdEditText, recipeIdEditText;
//    private Button sendCommentButton, connectBtn;
//    private RatingBar ratingBar;
//    private RecyclerView commentsRecyclerView;
//    private CommentAdapter commentAdapter;
//    private List<Comment> commentList = new ArrayList<>();
//    private long userIdLong;
//    private long recipeId;
//
//
//    // WebSocket server URL
//    private String BASE_URL = "ws://coms-309-033.class.las.iastate.edu:8080/comment/";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details);
//        userIdLong = getIntent().getLongExtra("user_id", 0);
//
//
//        // Initialize views and UI elements
//        commentEditText = findViewById(R.id.commentEditText);
//        sendCommentButton = findViewById(R.id.sendCommentButton);
//        recipeNameTextView = findViewById(R.id.recipeName);
//        userIdEditText = findViewById(R.id.userIdEditText);
//        recipeIdEditText = findViewById(R.id.recipeIdEditText);
//        ratingBar = findViewById(R.id.ratingBar);
//        connectBtn = findViewById(R.id.connectBtn);
//        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
//        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        commentAdapter = new CommentAdapter(commentList);
//        commentsRecyclerView.setAdapter(commentAdapter);
//        directionsTextView = findViewById(R.id.directionsTextView);
//        ingredientsTextView = findViewById(R.id.ingredientsListTextView);
//        btnToPickRecipe = findViewById(R.id.btnToPickRecipe1);
//        userIdLong = getIntent().getLongExtra("user_id", 0);
//        recipeId = getIntent().getLongExtra("recipe_id", 0);
//        Button addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
//        Button goToFavoritesButton = findViewById(R.id.goToFavoritesButton);
//        //ToggleButton addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
//
//
//
//        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
//        bottomNavigation.setSelectedItemId(View.NO_ID);
//
//        bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_discover:
//                    startActivity(new Intent(DetailsActivity.this, DiscoverActivity.class));
//                    return true;
//                case R.id.nav_favorites:
//                    startActivity(new Intent(DetailsActivity.this, FavoritesActivity.class));
//                    return true;
//                case R.id.nav_pantry:
//                    startActivity(new Intent(DetailsActivity.this, MyPantryActivity.class));
//                    return true;
//            }
//            return false;
//        });
//
//
//
//        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addToFavorites(recipeId);
//            }
//        });
//
//        goToFavoritesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Start the FavoritesActivity
//                Intent intent = new Intent(DetailsActivity.this, FavoritesActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Set up click listener to go to the pick recipe button
//        btnToPickRecipe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(DetailsActivity.this, PickRecipeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Fetch comments and update the RecyclerView
//        loadComments();
//
//        Intent intent = getIntent();
//        String recipeName = intent.getStringExtra("recipe_name");
//        String ingredients = intent.getStringExtra("ingredients");
//        String directions = intent.getStringExtra("directions");
//        Log.d("DetailsActivity", "Ingredients: " + ingredients);
//        Log.d("DetailsActivity", "Directions: " + directions);
////        if (directions != null) {
////            directionsTextView.setText(directions);
////        } else {
////            directionsTextView.setText("No directions available");
////        }
//
//
//
//// Set the recipe name in the TextView
//        recipeNameTextView.setText(recipeName);
//        ingredientsTextView.setText(ingredients);
//        directionsTextView.setText(directions);
//
//
//        // todo: onStart() make page start listening instead of when clicking button
//
//        // Set up click listener for connect button to establish WebSocket connection
//        connectBtn.setOnClickListener(view -> {
//            String userID = userIdEditText.getText().toString();
//            String recipeID = recipeIdEditText.getText().toString();
//            // Appends server url with the rest of the endpoint (/{userID}/{recipeID}/)
//            String serverUrl = BASE_URL + userID + "/" + recipeID; // TODO: Pull ID's instead of dummy variables
//
//            // Establish WebSocket connection and set listener
//            WebSocketManager.getInstance().connectWebSocket(serverUrl);
//            WebSocketManager.getInstance().setWebSocketListener(this);
//        });
//
//        // Set up click listener for send comment button to send in a review
//        sendCommentButton.setOnClickListener(v -> {
//            try {
//                float rating = ratingBar.getRating();
//                String message = commentEditText.getText().toString();
//                String review = rating + message;
//                WebSocketManager.getInstance().sendMessage(review);
//            } catch (Exception e) {
//                Log.d("ExceptionSendMessage:", e.getMessage().toString());
//            }
//        });
//    }
//
//
//    /*
//     * On page close, WebSocket stops listening
//     */
//    @Override
//    protected void onStop() {
//        super.onStop();
//        WebSocketManager.getInstance().disconnectWebSocket();
//    }
//
//    @Override
//    public void onWebSocketClose(int code, String reason, boolean remote) {
//        // Handle WebSocket close event
//    }
//
//    @Override
//    public void onWebSocketOpen(ServerHandshake handshakedata) {
//        // Handle WebSocket open event
//    }
//
//    @Override
//    public void onWebSocketError(Exception ex) {
//        // Handle WebSocket error
//    }
//
//    @Override
//    public void onWebSocketMessage(String message) {
//        // Handle WebSocket message received event
//        float floatingRating = ratingBar.getRating();
//        int rating = Math.round(floatingRating);
//
//        runOnUiThread(() -> {
//            commentList.add(new Comment(userIdEditText.getText().toString(), message, rating ));
//            //  commentList.add(new Comment(userID, text, rating));
//            commentAdapter.notifyDataSetChanged();
//        });
//    }
//
//
//    /**
//     * Method to load previous recipe reviews (not live).
//     */
//    private void loadComments() {
//        // TODO: Load (real) previously commented variables
//        // FIXME: recycler view??
////        setContentView(R.layout.comment_item);
////        commentText = findViewById(R.id.commentText);
////        commentRatingBar = findViewById(R.id.commentRatingBar);
////        commentUserName = findViewById(R.id.commentUserName);
//        // commentsTextView.append("\n" + text);
//
//        runOnUiThread(() -> {
//            // commentList.add(new Comment("User1", "This is a great recipe!", 5));
//            // commentAdapter.notifyDataSetChanged();
//        });
//    }
//
//
//    private void addToFavorites(long recipeId) {
//        UsersApi usersApi = GetUsersApi();
//        usersApi.addFavoriteRecipe(userIdLong, recipeId).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    // Handle success (e.g., update UI)
//                    Toast.makeText(DetailsActivity.this, "Recipe added to Favorites", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle error (e.g., show an error message)
//                    Log.e("Add to Favorites", "Failed to add recipe to favorites. Code: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e("Add to Favorites", "Failed to add recipe to favorites: " + t.getMessage());
//                t.printStackTrace();  // Log the stack trace to get more details
//            }
//        });
//    }
//
//
//
//
//
//}
//
//
//
//


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
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;

import com.example.as1.api.UsersApi;
import com.example.as1.model.Comment;
import com.example.as1.api.WebSocketListener;
import com.example.as1.model.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import org.java_websocket.handshake.ServerHandshake;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.as1.api.ApiClientFactory.GetUsersApi;


/**
 * DetailsActivity displays details of a recipe, allows users to add live comments and ratings through an established WebSocket connection.
 */
public class DetailsActivity extends AppCompatActivity implements WebSocketListener {
    // Views and UI elements
    private TextView ingredientsTextView, directionsTextView, recipeNameTextView, commentUserName, btnToPickRecipe;
    private EditText commentEditText, userIdEditText, recipeIdEditText;
    private Button sendCommentButton, connectBtn;
    private RatingBar ratingBar;
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();
    private long userIdLong;
    private long recipeId;


    // WebSocket server URL
    private String BASE_URL = "ws://coms-309-033.class.las.iastate.edu:8080/comment/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


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
        userIdLong = getIntent().getLongExtra("user_id", 0);
        recipeId = getIntent().getLongExtra("recipe_id", 0);
        Button addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
        Button goToFavoritesButton = findViewById(R.id.goToFavoritesButton);
        //ToggleButton addToFavoritesButton = findViewById(R.id.addToFavoritesButton);



        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(View.NO_ID);

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    startActivity(new Intent(DetailsActivity.this, DiscoverActivity.class));
                    return true;
                case R.id.nav_favorites:
                    startActivity(new Intent(DetailsActivity.this, FavoritesActivity.class));
                    return true;
                case R.id.nav_pantry:
                    startActivity(new Intent(DetailsActivity.this, MyPantryActivity.class));
                    return true;
            }
            return false;
        });



        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorites(recipeId);
            }
        });

        goToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the FavoritesActivity
                Intent intent = new Intent(DetailsActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

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

        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipe_name");
        String ingredients = intent.getStringExtra("ingredients");
        String directions = intent.getStringExtra("directions");
        Log.d("DetailsActivity", "Ingredients: " + ingredients);
        Log.d("DetailsActivity", "Directions: " + directions);
//        if (directions != null) {
//            directionsTextView.setText(directions);
//        } else {
//            directionsTextView.setText("No directions available");
//        }



// Set the recipe name in the TextView
        recipeNameTextView.setText(recipeName);
        ingredientsTextView.setText(ingredients);
        directionsTextView.setText(directions);


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


    private void addToFavorites(long recipeId) {
        UsersApi usersApi = GetUsersApi();
        usersApi.addFavoriteRecipe(userIdLong, recipeId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle success (e.g., update UI)
                    Toast.makeText(DetailsActivity.this, "Recipe added to Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error (e.g., show an error message)
                    Log.e("Add to Favorites", "Failed to add recipe to favorites. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure (e.g., show an error message)
                Log.e("Add to Favorites", "Failed to add recipe to favorites: " + t.getMessage());
            }
        });
    }





}


