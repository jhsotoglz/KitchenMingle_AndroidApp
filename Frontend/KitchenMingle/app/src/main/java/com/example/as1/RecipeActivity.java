package com.example.as1;

import static com.example.as1.api.ApiClientFactory.GetRecipeAPI;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;
import com.example.as1.model.SlimCallback;
import com.example.as1.model.Recipe;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        TextView apiText1 = findViewById(R.id.activity_main_textView1);

        apiText1.setMovementMethod(new ScrollingMovementMethod());
        apiText1.setHeight(350);

        Button PostByPathBtn = findViewById(R.id.activity_main_post_by_path_button);
        Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
        EditText recipeNameIn = findViewById(R.id.activity_main_recipename_editText);
        EditText instructionIn = findViewById(R.id.activity_main_instruction_editText);

        RegenerateAllRecipesOnScreen(apiText1);

        PostByPathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetRecipeAPI().PostRecipeByPath(recipeNameIn.getText().toString(), instructionIn.getText().toString()).enqueue(new SlimCallback<Recipe>(recipe->{
                    RegenerateAllRecipesOnScreen(apiText1);
                    recipeNameIn.setText("");
                    instructionIn.setText("");
                }));
            }
        });

        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe newRecipe = new Recipe();
                newRecipe.setRecipeName(recipeNameIn.getText().toString());
                newRecipe.setRecipeInstructions(instructionIn.getText().toString());
                GetRecipeAPI().PostRecipeByBody(newRecipe).enqueue(new SlimCallback<Recipe>(recipe->{
                    RegenerateAllRecipesOnScreen(apiText1);
                    recipeNameIn.setText("");
                    instructionIn.setText("");
                }));
            }
        });
    }

    void RegenerateAllRecipesOnScreen(TextView apiText1){
        GetRecipeAPI().GetAllRecipes().enqueue(new SlimCallback<List<Recipe>>(recipes ->{
            apiText1.setText("");

            for(int i = recipes.size() - 1; i >= 0; i--){
                apiText1.append(recipes.get(i).printable());
            }
        }, "GetAllRecipe"));
    }
}


