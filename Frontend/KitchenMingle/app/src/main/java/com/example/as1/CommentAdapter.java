package com.example.as1;
import com.example.as1.model.Comment;
import android.widget.TextView;
import android.widget.RatingBar;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import java.util.List;
import androidx.annotation.NonNull;
import android.view.ViewGroup;


/**
 * CommentAdapter manages and displays a list of comments in the RecyclerView of DetailsActivity.
 * It binds comment data to individual ViewHolder items and handles the creation and rendering of comment views.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;

    /**
     * Constructor for CommentAdapter
     * @param commentList The list of comments to be displayed in the RecyclerView
     */
    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.commentUserName.setText(comment.getUserName());
        holder.commentText.setText(comment.getContent());
        holder.commentRatingBar.setRating(comment.getRating()); // if using ratings
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    /**
     * ViewHolder class representing individual comment items in the RecyclerView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentUserName, commentText;
        RatingBar commentRatingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentUserName = itemView.findViewById(R.id.commentUserName);
            commentText = itemView.findViewById(R.id.commentText);
            commentRatingBar = itemView.findViewById(R.id.commentRatingBar); // if using ratings
        }
    }
}
