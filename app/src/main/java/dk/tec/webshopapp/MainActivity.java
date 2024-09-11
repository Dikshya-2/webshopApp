package dk.tec.webshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import dk.tec.webshopapp.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<Product> productList;
    private ProductAdapter adapter;
    // private ArrayAdapter<String> adapter;
    String url= "http://192.168.0.233:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.product_list);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList);
        // adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        Retrofit retrofit= new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        ProductApi productApi =retrofit.create(ProductApi.class);
        Call<List<Product>> call =productApi.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> data = response.body();
                    productList.clear();  // Clear existing data
                    productList.addAll(data);  // Add new data
                    adapter.notifyDataSetChanged();  // Notify adapter of data change
//                    for (Product product : data) {
//                        adapter.add("SL.No: " + product.getId() + " Name: " + product.getName() + " Description: " + product.getDescription());
//                    }
//                    adapter.notifyDataSetChanged();
                } else {
                    // Handle the case where the response is not successful
                    Log.e("MainActivity", "Response not successful");
                }
                //productList.append("SL.No:"+ data.get(i).getId()+" Name:"+data.get(i).getName()+"Description:"+data.get(i).getDescription());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.e("MainActivity", "API call failed", throwable);

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position);
                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", selectedProduct);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.main) {
                    // Navigate to HomeActivity
                    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (item.getItemId() == R.id.menu_item_cart) {
                    // Navigate to CartActivity
                    Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(cartIntent);
                    return true;
                }
                // Handle other menu items or return false if no item matches
                return false;
            }
        });
    }
}