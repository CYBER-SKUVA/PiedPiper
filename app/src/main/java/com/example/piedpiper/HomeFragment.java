package com.example.piedpiper;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private myAdapter adapter;
    private List<Item> items;
    private List<Item> allItems;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        items = new ArrayList<>();
        allItems = new ArrayList<>();
        adapter = new myAdapter(requireActivity().getApplicationContext(), items);
        recyclerView.setAdapter(adapter);

        fetchRecipes();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
                return true;
            }
        });
    }

    private void fetchRecipes() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2678460/get_recipes.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        items.clear();
                        allItems.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Item item = new Item(
                                    jsonObject.getString("full_name"),
                                    jsonObject.getString("username"),
                                    jsonObject.getString("title"),
                                    jsonObject.getString("ingredients"),
                                    jsonObject.getString("instructions"),
                                    jsonObject.getString("time"),
                                    R.drawable.profile // Assuming you have a placeholder image
                            );

                            items.add(item);
                            allItems.add(item);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void filterRecipes(String query) {
        List<Item> filteredList = new ArrayList<>();
        for (Item item : allItems) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    item.getIngredients().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        items.clear();
        items.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

    public void CommentActivity(View view) {
        Intent intent = new Intent(getActivity(), commentActivity.class);
        startActivity(intent);
    }
}
