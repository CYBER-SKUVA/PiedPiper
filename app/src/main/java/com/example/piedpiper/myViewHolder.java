package com.example.piedpiper;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView Name_, Username_, Title_, Description_, Instructions_, timePosted;
    Button reactions, comments;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initialize the views
        imageView = itemView.findViewById(R.id.imageviewer);
        Name_ = itemView.findViewById(R.id.NameofUser);
        Username_ = itemView.findViewById(R.id.UserNameofUser);
        Title_ = itemView.findViewById(R.id.recipetitle);
        Description_ = itemView.findViewById(R.id.recipedesc);
        Instructions_ = itemView.findViewById(R.id.recipeIns);
        timePosted = itemView.findViewById(R.id.timeposted);
        reactions = itemView.findViewById(R.id.lovebutton);
        comments = itemView.findViewById(R.id.commentbutton);
    }
}






/*trying out the new code*/


/*package com.example.piedpiper;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView Name_,Username_,Title_,Description_,Instructions_,timePosted;

    Button reactions;
    Button comments;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);


        Name_ = itemView.findViewById(R.id.NameofUser);
        Username_ = itemView.findViewById(R.id.UserNameofUser);
        Title_ = itemView.findViewById(R.id.recipetitle);
        Description_ = itemView.findViewById(R.id.recipedesc);
        Instructions_= itemView.findViewById(R.id.recipeIns);
        timePosted= itemView.findViewById(R.id.timeposted);
        imageView = itemView.findViewById(R.id.imageviewer);
        reactions = itemView.findViewById(R.id.lovebutton);
        comments = itemView.findViewById(R.id.commentbutton);
    }
}*/
