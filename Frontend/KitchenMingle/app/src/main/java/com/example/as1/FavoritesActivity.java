package com.example.as1;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.GridView;
        import androidx.appcompat.app.AppCompatActivity;

        import com.example.as1.api.ApiClientFactory;
        import com.example.as1.api.UsersApi;

        import retrofit2.Call;

public class FavoritesActivity extends AppCompatActivity {


    private GridView gridViewRecipes;
    // TODO: Pull specific user's recipes
    private String[] recipes = {"Recipe1", "Recipe2", "Recipe3", "Recipe4", "Recipe5", "Recipe6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        UsersApi usersApi = ApiClientFactory.GetUsersApi(); // initializing retrofit service
  //      Call<String> call = usersApi.getFavoriteRecipes(getUser());

        gridViewRecipes = findViewById(R.id.gridViewRecipes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipes);
        gridViewRecipes.setAdapter(adapter);

/*        gridViewRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavoritesActivity.this, DetailsActivity.class);
                intent.putExtra("recipe_name", recipes[position]);
                startActivity(intent);
            }
        });*/
//        getFavRecipesCall.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                if (response.isSuccessful()) {
//                    List<Recipe> favoriteRecipes = response.body();
//                    favoriteRecipes.forEach(recipe -> System.out.println("Recipe: " + recipe.getName()));
//                } else {
//                    System.out.println("Failed to get favorite recipes: " + response.errorBody().string());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }
}
