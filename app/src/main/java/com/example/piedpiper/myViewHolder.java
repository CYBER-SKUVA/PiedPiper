package com.example.piedpiper;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class myViewHolder extends RecyclerView.ViewHolder {


    ImageView imageView;
    TextView Name_, Username_,Ingredients_,Title_, Instructions_, timePosted, likesCount;
    Button reactions, comments;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initialize the views
        imageView = itemView.findViewById(R.id.imageviewer);
        Name_ = itemView.findViewById(R.id.NameofUser);
        Username_ = itemView.findViewById(R.id.UserNameofUser);
        Title_ = itemView.findViewById(R.id.recipetitle);
        Ingredients_ = itemView.findViewById(R.id.ingredients);
        Instructions_ = itemView.findViewById(R.id.recipeIns);
        likesCount = itemView.findViewById(R.id.likesCount);
        timePosted = itemView.findViewById(R.id.timeposted);
        reactions = itemView.findViewById(R.id.lovebutton);
        comments = itemView.findViewById(R.id.commentbutton);
    }
}
