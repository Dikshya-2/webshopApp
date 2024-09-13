package dk.tec.webshopapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dk.tec.webshopapp.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminFormActivity extends AppCompatActivity {

    private EditText productId, productName, productPrice, productDescription;
    private Retrofit retrofit;
    private ProductApi productApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_form);

        // Initialize Retrofit and API
        retrofit = RetrofitClient.getClient();
        productApi = retrofit.create(ProductApi.class);

        // Initialize UI elements
        productId = findViewById(R.id.product_id);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);

        Button addProductButton = findViewById(R.id.add_product_button);
        Button updateProductButton = findViewById(R.id.update_product_button);
        Button deleteProductButton = findViewById(R.id.delete_product_button);

        addProductButton.setOnClickListener(this::onAddProductClicked);
        updateProductButton.setOnClickListener(this::onUpdateProductClicked);
        deleteProductButton.setOnClickListener(this::onDeleteProductClicked);
    }

    // Add Product
    private void onAddProductClicked(View view) {
        String name = productName.getText().toString();
        String priceStr = productPrice.getText().toString();
        String description = productDescription.getText().toString();
        String imageUrl = ""; // default or empty URL for new products

        if (name.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Name and price are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);  // Parse the price
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Assuming ID is not required when creating a new product
        Product product = new Product(0, name, description, price, imageUrl);

        Call<Void> call = productApi.createProduct(product);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminFormActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminFormActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminFormActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Update Product
    private void onUpdateProductClicked(View view) {
        String idStr = productId.getText().toString();
        String name = productName.getText().toString();
        String priceStr = productPrice.getText().toString();
        String description = productDescription.getText().toString();
        String imageUrl = "";

        if (idStr.isEmpty() || name.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "ID, name, and price are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int id;
        double price;
        try {
            id = Integer.parseInt(idStr);  // Parse the ID as an integer
            price = Double.parseDouble(priceStr);  // Parse the price as a double
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid ID or price format", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product(id, name, description, price, imageUrl);

        Call<Void> call = productApi.updateProduct(id, product);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminFormActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminFormActivity.this, "Failed to update product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminFormActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Delete Product
    private void onDeleteProductClicked(View view) {
        String idStr = productId.getText().toString();

        if (idStr.isEmpty()) {
            Toast.makeText(this, "ID is required", Toast.LENGTH_SHORT).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);  // Parse the ID
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid ID format", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Void> call = productApi.deleteProduct(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminFormActivity.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminFormActivity.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminFormActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

