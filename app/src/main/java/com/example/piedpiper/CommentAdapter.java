package com.example.piedpiper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.commentUserName.setText(comment.getUsername());
        holder.commentText.setText(comment.getText());
        holder.commentTime.setText(comment.getTime());
        holder.commentUserImage.setImageResource(comment.getUserImage());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView commentUserImage;
        TextView commentUserName;
        TextView commentText;
        TextView commentTime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentUserImage = itemView.findViewById(R.id.userProfileImage);
            commentUserName = itemView.findViewById(R.id.commentUserName);
            commentText = itemView.findViewById(R.id.commentText);
            commentTime = itemView.findViewById(R.id.commentTime);
        }
    }
}
