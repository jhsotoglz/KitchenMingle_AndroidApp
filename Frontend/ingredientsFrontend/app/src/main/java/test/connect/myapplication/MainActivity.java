package test.connect.myapplication;

import static test.connect.myapplication.api.ApiClientFactory.GetIngredientAPI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import test.connect.myapplication.api.SlimCallback;
import test.connect.myapplication.model.Ingredient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView apiText1 = findViewById(R.id.activity_main_textView1);

        apiText1.setMovementMethod(new ScrollingMovementMethod());
        apiText1.setHeight(350);

        Button PostByPathBtn = findViewById(R.id.activity_main_post_by_path_button);
        Button PostByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
        EditText ingredientNameIn = findViewById(R.id.activity_main_recipename_editText);
        //Button deleteButton = findViewById(R.id.activity_main_delete_button);

        RegenerateAllIngredientsOnScreen(apiText1);

        PostByPathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetIngredientAPI().PostIngredientByPath(ingredientNameIn.getText().toString()).enqueue(new SlimCallback<Ingredient>(ingredient ->{
                    RegenerateAllIngredientsOnScreen(apiText1);
                    ingredientNameIn.setText("");
                }));
            }
        });

        PostByBodyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setIngredientName(ingredientNameIn.getText().toString());
                GetIngredientAPI().PostIngredientByBody(newIngredient).enqueue(new SlimCallback<Ingredient>(ingredient ->{
                    RegenerateAllIngredientsOnScreen(apiText1);
                    ingredientNameIn.setText("");
                }));
            }
        });

//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the ingredient ID that you want to delete (you can retrieve it from user input or elsewhere)
//                String ingredientIdToDelete = ingredientNameIn.getText().toString();
//
//                if (!ingredientIdToDelete.isEmpty()) {
//                    // Convert the ingredient ID to Long if needed
//                    Long idToDelete = Long.parseLong(ingredientIdToDelete);
//
//                    // Call the API to delete the ingredient by ID
//                    GetIngredientAPI().deleteIngredientById(idToDelete).enqueue(new SlimCallback<Void>(response -> {
//                        // Handle the success or error response as needed
//                        RegenerateAllIngredientsOnScreen(apiText1);
//                        ingredientNameIn.setText(""); // Clear the input field
//                    }));
//                } else {
//                    // Handle the case where the input is empty or invalid
//                    // You can display a message to the user or perform other actions
//                    Toast.makeText(MainActivity.this, "Please enter a valid ingredient ID.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    // This method prints all the current stored ingredients in the database
    void RegenerateAllIngredientsOnScreen(TextView apiText1){
        GetIngredientAPI().GetAllIngredients().enqueue(new SlimCallback<List<Ingredient>>(ingredients ->{
            apiText1.setText("");

            for(int i = ingredients.size() - 1; i >= 0; i--){
                apiText1.append(ingredients.get(i).printable());
            }
        }, "GetAllIngredients"));
    }
}


