package com.example.piedpiper;

public class Item {
    private final String ingredients;
    private String full_name;
    private String username;
    private String title;
    private String instructions;
    private String time;
    private int imageResource;
    private int likes;

    public Item(String name, String username, String title, String ingredients, String instructions, String time, int imageResource) {
        this.full_name = name;
        this.username = username;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.time = time;
        this.imageResource = imageResource;
        this.likes = likes;
    }

    public String getName() {
        return full_name;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }


    public String getInstructions() {
        return instructions;
    }

    public String getTime() {
        return time;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int i) {
    }

}
