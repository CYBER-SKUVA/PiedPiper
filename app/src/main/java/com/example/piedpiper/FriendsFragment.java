package com.example.piedpiper;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

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

public class FriendsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FriendsAdapter adapter;
    private List<Friend> friendsList;
    private List<Friend> allFriends;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerViewFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        friendsList = new ArrayList<>();
        allFriends = new ArrayList<>();
        adapter = new FriendsAdapter(requireActivity().getApplicationContext(), friendsList);
        recyclerView.setAdapter(adapter);

        fetchFriends();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterFriends(newText);
                return true;
            }
        });
    }

    private void fetchFriends() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2678460/get_friends.php"; // Replace with your server URL

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Handle the error here
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        friendsList.clear();
                        allFriends.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Friend friend = new Friend(
                                    jsonObject.getString("username"),
                                    jsonObject.getString("full_name"),
                                    R.drawable.profile // Assuming you have a placeholder image
                            );

                            friendsList.add(friend);
                            allFriends.add(friend);
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

    private void filterFriends(String query) {
        List<Friend> filteredList = new ArrayList<>();
        for (Friend friend : allFriends) {
            if (friend.getFullName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(friend);
            }
        }
        friendsList.clear();
        friendsList.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }
}
