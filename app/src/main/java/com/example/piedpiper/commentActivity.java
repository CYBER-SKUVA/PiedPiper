package com.example.piedpiper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class commentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;
    private TextView recipeDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // Initialize RecyclerView
        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        View otherLayout = LayoutInflater.from(this).inflate(R.layout.recipeview, null);

        // Initialize Comment List and Adapter
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerViewComments.setAdapter(commentAdapter);

        // Load comments (dummy data for now)
        loadComments();
        recipeDesc = otherLayout.findViewById(R.id.recipedesc);
    }

    private void loadComments() {
        // Add some dummy comments
        commentList.add(new Comment("User1", "This is a comment.", "1h ago", R.drawable.profile));
        commentList.add(new Comment("User2", "Nice post!", "2h ago", R.drawable.profile));
        commentAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        // Handle back button press here (if needed)
        // In this example, we let the back button behave normally (go back to previous screen)
        super.onBackPressed();
    }
}
