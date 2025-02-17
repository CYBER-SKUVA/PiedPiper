package com.example.piedpiper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class commentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private EditText editTextComment;
    private Button buttonPostComment;
    private ImageView userProfileImage;
    private String username; // Variable to store the username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Initialize views
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        editTextComment = findViewById(R.id.editTextComment);
        buttonPostComment = findViewById(R.id.buttonPostComment);
        userProfileImage = findViewById(R.id.userProfileImage);

        // Initialize RecyclerView
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Comment List and Adapter
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerViewComments.setAdapter(commentAdapter);

        // Load initial comments (dummy data for now)
        loadComments();

        // Retrieve username from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        username = prefs.getString("username", null);

        // Add text watcher to enable the post button when there's text
        editTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonPostComment.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text changes
            }
        });

        // Handle post comment button click
        buttonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });
    }

    private void loadComments() {
        // Add some dummy comments
        commentList.add(new Comment("mufasa", "Adding the cayenne pepper makes the dish absolutely delicious!", "1h ago", R.drawable.profile));
        commentList.add(new Comment("skuva", "My grannies favourite!", "2h ago", R.drawable.profile));
        commentAdapter.notifyDataSetChanged();
    }

    private void postComment() {
        String commentText = editTextComment.getText().toString().trim();
        if (!commentText.isEmpty()) {
            // Create a new comment with the username retrieved from SharedPreferences
            Comment newComment = new Comment(username, commentText, "Just now", R.drawable.profile);
            commentList.add(0, newComment); // Add new comment to the top of the list
            commentAdapter.notifyItemInserted(0); // Notify adapter about the new item
            recyclerViewComments.scrollToPosition(0); // Scroll to the top
            editTextComment.setText(""); // Clear the input field

            // You can also send this comment to the server if needed
            // Example: sendCommentToServer(newComment);
        }
    }

    // Additional method to send the comment to the server if needed
    private void sendCommentToServer(Comment comment) {
        // Implement your logic to send the comment to the server here
    }
}
