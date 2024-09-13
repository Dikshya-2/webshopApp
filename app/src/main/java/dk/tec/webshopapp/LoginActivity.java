package dk.tec.webshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import dk.tec.webshopapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText emailField, passwordField;
    private Retrofit retrofit;
    private UserAPI userAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(view -> {
            // Start Registration Activity
            Intent intent = new Intent(LoginActivity.this, UserRegisterActivity.class);
            startActivity(intent);
        });

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://your-backend-url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userAPI = retrofit.create(UserAPI.class);


        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        // Create User object with email and password
        User user = new User(email, password);

        // Make API call using Retrofit
        Call<String> call = userAPI.login(user);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Display success message from the response
                    Toast.makeText(LoginActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                } else {
                    // Display failure message
                    Toast.makeText(LoginActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Display network error
                Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
