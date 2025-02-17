package com.example.piedpiper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class addrecipeFragment extends Fragment {
    private final List<ListItem> selectedItem = new ArrayList<>();
    private final ArrayList<ListItem> spinnerListItem = new ArrayList<>();
    private Spinner spinner;
    private EditText editTextTitle, editTextInstructions;
    private String username;
    private MultiSelectSpinnerAdapter adapter;

    public addrecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addrecipe, container, false);
        spinner = view.findViewById(R.id.select_spinner);
        editTextTitle = view.findViewById(R.id.AddTitle);
        editTextInstructions = view.findViewById(R.id.AddInstructions);

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = prefs.getString("username", null);

        fetchIngredients();

        Button postButton = view.findViewById(R.id.CreateButton);
        postButton.setOnClickListener(v -> postRecipe());

        return view;
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
                Log.e("Fetch Ingredients", "Network error: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        spinnerListItem.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("ing_name");
                            spinnerListItem.add(new ListItem(name));
                        }

                        getActivity().runOnUiThread(() -> {
                            adapter = new MultiSelectSpinnerAdapter(
                                    getActivity(),
                                    spinnerListItem,
                                    selectedItem
                            );
                            spinner.setAdapter(adapter);
                            adapter.setOnItemSelectedListener(selectedItems -> {
                                Log.e("getSelectedItems", selectedItems.toString());
                                Log.e("getSelectedItems", String.valueOf(selectedItems.size()));
                            });
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Fetch Ingredients", "Response not successful");
                }
            }
        });
    }

    private void postRecipe() {
        String title = editTextTitle.getText().toString().trim();
        String instructions = editTextInstructions.getText().toString().trim();
        String selectedIngredients = getSelectedIngredientsAsString();

        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2678460/addrecipes.php";

        RequestBody formBody = new FormBody.Builder()
                .add("title", title)
                .add("instructions", instructions)
                .add("ingredients", selectedIngredients)
                .add("username", username)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getActivity(), "Failed to post recipe", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        String status = jsonObject.getString("success");
                        getActivity().runOnUiThread(() -> {
                            if (status.equals("true")) {
                                Toast.makeText(getActivity(), "Recipe successfully created!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed to create recipe", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(), "Response not successful", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }

    private String getSelectedIngredientsAsString() {
        StringBuilder builder = new StringBuilder();
        for (ListItem item : selectedItem) {
            builder.append(item.getName()).append(", ");
        }
        if (builder.length() > 2) {
            builder.delete(builder.length() - 2, builder.length());
        }
        return builder.toString();
    }
}
