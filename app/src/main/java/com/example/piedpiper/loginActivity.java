package com.example.piedpiper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class loginActivity extends AppCompatActivity {

    EditText L_username, L_password;
    Button L_butt;
    TextView L_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        L_username = findViewById(R.id.login_user);
        L_password = findViewById(R.id.login_pass);
        L_butt = findViewById(R.id.login);

        L_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String logUser = L_username.getText().toString();
                String logPas = L_password.getText().toString();

                // Start the AsyncTask to verify login credentials
                new LoginTask().execute(logUser, logPas);
            }
        });
    }



    // AsyncTask to handle network operation in background thread
    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String logUser = params[0];
            String logPas = params[1];
            String result = "";

            try {
                URL url = new URL("https://lamp.ms.wits.ac.za/home/s2678460/loginusers.php");  // Replace with your PHP script URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                // Prepare data
                String postData = "username=" + logUser + "&password=" + logPas;

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
                String message = jsonResponse.getString("message");

                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                    intent.putExtra("username", L_username.getText().toString().trim());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong password or username!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startSignupActivity(View view) {
        Intent intent = new Intent(this, signupActivity.class);
        startActivity(intent);
        finish();
    }

    public void startHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
