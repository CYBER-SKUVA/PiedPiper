package com.example.piedpiper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class signupActivity extends AppCompatActivity {

    EditText S_fullname, S_username, S_email, S_password, S_confirmpassword;
    Button S_butt;
    TextView S_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        S_fullname = findViewById(R.id.fullname);
        S_username = findViewById(R.id.username);
        S_email = findViewById(R.id.email);
        S_password = findViewById(R.id.password);
        S_confirmpassword = findViewById(R.id.confrimpass);
        S_butt = findViewById(R.id.signup);

        S_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = S_fullname.getText().toString();
                String username = S_username.getText().toString();
                String email = S_email.getText().toString();
                String password = S_password.getText().toString();
                String confirmation = S_confirmpassword.getText().toString();

                if (fullname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmation.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(confirmation) == 0) {
                        if (isValid(password)) {
                            // Call AsyncTask to send sign-up data to PHP script
                            new SignupTask().execute(fullname, username, email, password);
                        } else {
                            Toast.makeText(getApplicationContext(), "Password must meet criteria", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static boolean isValid(String password) {
        // Validate password criteria
        return password.length() >= 8 && password.length() <= 20 &&
                password.matches(".*\\d.*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[!@#$%^&*()-_=+\\|[{]};:'\",<.>/?`~].*");
    }

    private class SignupTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String fullname = params[0];
            String username = params[1];
            String email = params[2];
            String password = params[3];

            String result = "";

            try {
                URL url = new URL("https://lamp.ms.wits.ac.za/home/s2678460/signupusers.php");  // Replace with your PHP script URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                // Prepare data
                String postData = "fullname=" + fullname + "&username=" + username + "&email=" + email + "&password=" + password;

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
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signupActivity.this, loginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_SHORT).show();
            }
        }
    }
}











/*package com.example.piedpiper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class signupActivity extends AppCompatActivity {

    EditText S_fullname,S_username,S_email,S_password,S_confirmpassword;
    Button S_butt;
    TextView S_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        S_fullname = findViewById(R.id.fullname);
        S_username = findViewById(R.id.username);
        S_email = findViewById(R.id.email);
        S_password = findViewById(R.id.password);
        S_confirmpassword = findViewById(R.id.confrimpass);
        S_butt =findViewById(R.id.signup);

        S_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = S_fullname.getText().toString();
                String username = S_username.getText().toString();
                String email = S_email.getText().toString();
                String password = S_password.getText().toString();
                String confirmation = S_confirmpassword.getText().toString();

                if(fullname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmation.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                }
                else{
                    if( password.compareTo(confirmation)==0){
                        if(isValid(password)) {
                            //create calls to add info to database
                            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();

                            Intent intent;
                            intent = new Intent(signupActivity.this , loginActivity.class);
                            startActivity(intent);
                            finish();


                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Password must be between 8 and 20 characters, " +
                                    "contain at least one uppercase letter, one lowercase letter, one digit, " +
                                    "and one special character.", Toast.LENGTH_LONG).show();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
   public static boolean isValid(String password) {

            if (password.length() < 8 || password.length() > 20) {
                return false;
            }
            boolean hasUppercase = !password.equals(password.toLowerCase());
            if (!hasUppercase) {
                return false;
            }
            boolean hasLowercase = !password.equals(password.toUpperCase());
            if (!hasLowercase) {
                return false;
            }
            boolean hasDigit = password.matches(".*\\d.*");
            if (!hasDigit) {
                return false;
            }
            boolean hasSpecialChar = !password.matches("[A-Za-z0-9 ]*");
            if (!hasSpecialChar) {
                return false;
            }
            return true;
        }

    }
*/