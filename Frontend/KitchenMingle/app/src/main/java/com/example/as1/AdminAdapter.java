package com.example.as1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.as1.api.AdminApi;
import com.example.as1.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Make sure to import Retrofit and related classes

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    private List<Users> userList; // Now using List<Users>
    private AdminApi adminApi; // Your Retrofit API interface

    public AdminAdapter(List<Users> userList, AdminApi adminApi) {
        this.userList = userList;
        this.adminApi = adminApi;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each item (assuming it's named admin_item.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Users user = userList.get(position);
        holder.usernameTextView.setText(user.getUsername());
        holder.emailTextView.setText(user.getEmail());

        holder.deleteButton.setOnClickListener(v -> {
            // Get the current position
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                // Now use currentPosition instead of position
                Users userToDelete = userList.get(currentPosition);

                // Call the API to delete the user
                adminApi.deleteUser(userToDelete.getUserId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Remove the admin from the list and notify the adapter
                            userList.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                        } else {
                            // Handle API error
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle network error
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView emailTextView;
        Button deleteButton; // Delete button

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton); // Initialize the delete button
        }
    }
}
