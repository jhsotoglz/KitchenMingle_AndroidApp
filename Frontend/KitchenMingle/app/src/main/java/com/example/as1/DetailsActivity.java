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
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatRatingBar;
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
import retrofit2.http.Path;

import static com.example.as1.api.ApiClientFactory.GetUsersApi;


/**
 * DetailsActivity displays details of a recipe, allows users to add live comments and ratings through an established WebSocket connection.
 */
public class DetailsActivity extends AppCompatActivity implements WebSocketListener {
    // Views and UI elements
    private TextView ingredientsTextView, directionsTextView, recipeNameTextView;
    private EditText commentEditText, recipeIdEditText;
    private Button sendCommentButton, connectBtn;
    private RatingBar ratingBar;
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();
    private ImageButton favoriteButton;
    private boolean isFavorited = false;
    private long recipeId;
    private Long userId; // stores user ID from login



    // WebSocket server URL
    private String BASE_URL = "ws://coms-309-033.class.las.iastate.edu:8080/comment/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get intent from login
        Intent intent = getIntent();

        // Get user ID from intent
        userId = intent.getLongExtra("USER_ID", -1); // -1 is default

        // Check if userId is valid
        if (userId == -1) {
            // todo: handle case if id isn't properly passed
            // maybe redirect back to login or show an error message
        }


        // Initialize views and UI elements
        commentEditText = findViewById(R.id.commentEditText);
        sendCommentButton = findViewById(R.id.sendCommentButton);
        recipeNameTextView = findViewById(R.id.recipeName);
        recipeIdEditText = findViewById(R.id.recipeIdEditText);
        ratingBar = findViewById(R.id.ratingBar);
        connectBtn = findViewById(R.id.connectBtn);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(commentList);
        commentsRecyclerView.setAdapter(commentAdapter);
        directionsTextView = findViewById(R.id.directionsTextView);
        ingredientsTextView = findViewById(R.id.ingredientsListTextView);
        recipeId = getIntent().getLongExtra("recipe_id", 0);
        favoriteButton = findViewById(R.id.favoriteButton);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(View.NO_ID);


        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_discover:
                    Intent discoverIntent = new Intent(DetailsActivity.this, DiscoverActivity.class);
                    discoverIntent.putExtra("USER_ID", userId);
                    startActivity(discoverIntent);
                    return true;
                case R.id.nav_favorites:
                    Intent favoritesIntent = new Intent(DetailsActivity.this, FavoritesActivity.class);
                    favoritesIntent.putExtra("USER_ID", userId);
                    startActivity(favoritesIntent);
                    return true;
                case R.id.nav_pantry:
                    Intent pantryIntent = new Intent(DetailsActivity.this, MyPantryActivity.class);
                    pantryIntent.putExtra("USER_ID", userId);
                    startActivity(pantryIntent);
                    return true;
            }
            return false;
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorited = !isFavorited; // Toggle the state
                updateFavoriteButton();
            }
        });

        // Set initial state of fav button
        updateFavoriteButton();

        // Fetch comments and update the RecyclerView
        loadComments();

        Intent intent1 = getIntent();
        String recipeName = intent1.getStringExtra("recipe_name");
        String ingredients = intent1.getStringExtra("ingredients");
        String directions = intent1.getStringExtra("directions");
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
            String userID = userId.toString();
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

    //todo:
    private void updateFavoriteButton() {
        if (isFavorited) {
            favoriteButton.setImageResource(R.drawable.ic_favorited);
            addToFavorites(recipeId);
        } else {
            favoriteButton.setImageResource(R.drawable.ic_unfavorited);
            removeFromFavorites(recipeId);
        }
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
            commentList.add(new Comment(userId.toString(), message, rating ));
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

    private void removeFromFavorites(long recipeId) {
        UsersApi usersApi = GetUsersApi();
        usersApi.deleteFavoriteRecipe(userId, recipeId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Handle success (e.g., update UI)
                    Toast.makeText(DetailsActivity.this, "Recipe removed from Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error (e.g., show an error message)
                    Log.e("Remove from Favorites", "Failed to remove a recipe from favorites. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle failure (e.g., show an error message)
                Log.e("Remove from Favorites", "Failed to remove a recipe from favorites: " + t.getMessage());
            }
        });
    }

    private void addToFavorites(long recipeId) {
        UsersApi usersApi = GetUsersApi();
        usersApi.addFavoriteRecipe(userId, recipeId).enqueue(new Callback<Void>() {
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


