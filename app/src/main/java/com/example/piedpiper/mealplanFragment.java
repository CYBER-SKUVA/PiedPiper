package com.example.piedpiper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class mealplanFragment extends Fragment {

    private final List<ListItem> mealOptions = new ArrayList<>();
    private ArrayAdapter<ListItem> adapter;
    private Spinner[] breakfastSpinners;
    private Spinner[] lunchSpinners;
    private Spinner[] dinnerSpinners;
    private Button generateListButton;
    private ListView groceryListView;
    private List<String> groceryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mealplan, container, false);

        breakfastSpinners = new Spinner[7];
        lunchSpinners = new Spinner[7];
        dinnerSpinners = new Spinner[7];

        for (int i = 0; i < 7; i++) {
            String dayName = getDayName(i);
            int breakfastId = getResources().getIdentifier("breakfast_spinner_" + dayName.toLowerCase(), "id", requireActivity().getPackageName());
            breakfastSpinners[i] = view.findViewById(breakfastId);
            int lunchId = getResources().getIdentifier("lunch_spinner_" + dayName.toLowerCase(), "id", requireActivity().getPackageName());
            lunchSpinners[i] = view.findViewById(lunchId);
            int dinnerId = getResources().getIdentifier("dinner_spinner_" + dayName.toLowerCase(), "id", requireActivity().getPackageName());
            dinnerSpinners[i] = view.findViewById(dinnerId);

            setupSpinner(breakfastSpinners[i], dayName, "Breakfast");
            setupSpinner(lunchSpinners[i], dayName, "Lunch");
            setupSpinner(dinnerSpinners[i], dayName, "Dinner");
        }

        return view;
    }

    private void setupSpinner(Spinner spinner, String day, String mealType) {
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mealOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        fetchRecipes(day, mealType, spinner);
    }

    private String getDayName(int dayIndex) {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return days[dayIndex];
    }



    private void fetchRecipes(String day, String mealType, Spinner spinner) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2678460/get_recipes.php?day=" + day + "&meal_type=" + mealType;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showToast("Failed to fetch data");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        mealOptions.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String recipeTitle = jsonObject.getString("title");
                            ListItem item = new ListItem(recipeTitle);
                            mealOptions.add(item);
                        }

                        getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast("Error parsing JSON");
                    }
                } else {
                    showToast("Error: " + response.code());
                }
            }
        });
    }

    private void generateGroceryList() {
        groceryList.clear();


       /* addIngredientsToList(breakfastSpinner.getSelectedItem());
        addIngredientsToList(lunchSpinner.getSelectedItem());
        addIngredientsToList(dinnerSpinner.getSelectedItem()); */

        // Fetch all ingredients from the database
        fetchIngredients();

        // Display the grocery list
        ArrayAdapter<String> groceryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, groceryList);
        groceryListView.setAdapter(groceryAdapter);
    }


    private void fetchIngredients() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2678460/get_Ing.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("ing_name");
                            Log.d("Ingredient", name); // Output each ingredient to logcat
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Fetch Ingredients", "Response not successful");
                }
            }
        });
    }



    private void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }
}
