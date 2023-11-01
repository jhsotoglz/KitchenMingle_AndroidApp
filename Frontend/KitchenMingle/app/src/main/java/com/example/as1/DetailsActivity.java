package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import android.widget.LinearLayout;
import static com.example.as1.api.ApiClientFactory.GetIngredientAPI;
import android.widget.TextView;
import com.example.as1.model.Ingredient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.view.View;



public class DetailsActivity extends AppCompatActivity {
    private LinearLayout ingredientListLayout;
    private LinearLayout directionsListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ingredientListLayout = findViewById(R.id.ingredientListLayout);
        directionsListLayout = findViewById(R.id.directionsListLayout);

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
                }
            }
            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                // Handle network or other errors
            }
        });



    }

    private void displayIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            // Create a TextView for each ingredient and add it to the layout
            TextView textView = new TextView(this);
            textView.setText(ingredient.getIngredientName());
            ingredientListLayout.addView(textView);
        }
    }
}
