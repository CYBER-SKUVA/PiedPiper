package com.example.piedpiper;

import android.content.Context;
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
        holder.Description_.setText(item.getDescription());
        holder.Instructions_.setText(item.getInstructions());
        holder.timePosted.setText(item.getTime());
        holder.imageView.setImageResource(item.getImageResource());

        holder.reactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.reactions.setBackgroundResource(R.drawable.heart_filled);
            }
        });

        // You can add a click listener for comments if needed
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle comment button click
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


/*package com.example.piedpiper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myAdapter extends RecyclerView.Adapter<myViewHolder> {

    Context context;
    List<Item> items;

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
        holder.Description_.setText(item.getDescription());
        holder.Instructions_.setText(item.getInstructions());
        holder.timePosted.setText(item.getTime());

        holder.imageView.setImageResource(item.getImage());

        // Set up click listeners for buttons if necessary
        holder.reactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.reactions.setBackgroundColor(R.drawable.heart_filled);
            }
        });

        }

    @Override
    public int getItemCount() {
        return items.size();
    }
}*/
