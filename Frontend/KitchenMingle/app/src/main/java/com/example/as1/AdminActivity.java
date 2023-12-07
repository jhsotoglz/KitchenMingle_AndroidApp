package com.example.as1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as1.api.AdminApi;
import com.example.as1.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView adminRecyclerView;
    private AdminAdapter adminAdapter;
    private AdminApi adminApi; // Retrofit API interface
    private List<Users> userList; // User data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialize RecyclerView
        adminRecyclerView = findViewById(R.id.adminRecyclerView);
        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Retrofit and AdminApi
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("BASE_URL") // API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        adminApi = retrofit.create(AdminApi.class);

        // Fetch the user list
        fetchUsers();
    }

    private void fetchUsers() {
        adminApi.getAllUsers().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList = response.body();
                    adminAdapter = new AdminAdapter(userList, adminApi);
                    adminRecyclerView.setAdapter(adminAdapter);
                } else {
                    // Handle response error
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                // Handle network error
            }
        });
    }
}
