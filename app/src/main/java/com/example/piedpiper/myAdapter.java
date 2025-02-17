package com.example.piedpiper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myViewHolder> {

    private Context context;
    private List<Item> items;

    public myAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipeview, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Item item = items.get(position);
        holder.Name_.setText(item.getName());
        holder.Username_.setText(item.getUsername());
        holder.Title_.setText(item.getTitle());
        holder.Ingredients_.setText(item.getIngredients());
        holder.Instructions_.setText(item.getInstructions());
        holder.timePosted.setText(item.getTime());
        holder.imageView.setImageResource(item.getImageResource());
        holder.likesCount.setText(String.valueOf(item.getLikes()));

        holder.reactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setLikes(item.getLikes() + 1);
                // Update the UI
                holder.likesCount.setText(String.valueOf(item.getLikes()));
                // Change the like button appearance
                holder.reactions.setBackgroundResource(R.drawable.heart_filled);
            }
        });

        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, commentActivity.class);
                // Ensure the context is correctly used when starting the activity
                if (context instanceof MainActivity) {
                    ((MainActivity) context).startActivity(intent);
                } else {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
