package com.example.piedpiper;

public class Item {
    private String full_name;
    private String username;
    private String title;
    private String description;
    private String instructions;
    private String time;
    private int imageResource;

    public Item(String name, String username, String title, String description, String instructions, String time, int imageResource) {
        this.full_name = name;
        this.username = username;
        this.title = title;
        this.description = description;
        this.instructions = instructions;
        this.time = time;
        this.imageResource = imageResource;
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

    public String getDescription() {
        return description;
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
}


/*package com.example.piedpiper;

public class Item {
    String Name,Username,title,description,instructions,time;
    int image;

    public Item(String name, String username, String title, String description, String instructions, String time, int image) {
        Name = name;
        Username = username;
        this.title = title;
        this.description = description;
        this.instructions = instructions;
        this.time = time;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}*/
