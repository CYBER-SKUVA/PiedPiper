package com.example.piedpiper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class profileFragment extends Fragment {

    private TextView profileUsername, profileName, profileEmail;
    private String username;

    public profileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the username from SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        username = prefs.getString("username", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileUsername = view.findViewById(R.id.ProfileUsername);
        profileName = view.findViewById(R.id.Profilename);
        profileEmail = view.findViewById(R.id.ProfileEmail);
        Button logoutButton = view.findViewById(R.id.LogoutButton);

        // Fetch user details
        if (username != null) {
            new FetchUserProfileTask().execute(username);
        }

        logoutButton.setOnClickListener(v -> logout());

        return view;
    }

    private class FetchUserProfileTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String result = "";

            try {
                URL url = new URL("https://lamp.ms.wits.ac.za/home/s2678460/get_profile.php"); // Replace with your PHP script URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                // Prepare data
                String postData = "username=" + username;

                // Send data
                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                // Get the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                result = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                if (status.equals("success")) {
                    String fullName = jsonResponse.getString("fullname");
                    String email = jsonResponse.getString("email");

                    profileUsername.setText(username);
                    profileName.setText(fullName);
                    profileEmail.setText(email);
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch user details", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error parsing server response", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void logout() {
        // Clear SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(getActivity(), loginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
