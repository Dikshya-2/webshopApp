package dk.tec.webshopapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import dk.tec.webshopapp.model.Product;

public class ProductDetailActivity extends AppCompatActivity {
    private Product product;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        product = (Product) getIntent().getSerializableExtra("product");

        ImageView productImage = findViewById(R.id.product_detail_image);
        TextView productName = findViewById(R.id.product_detail_name);
        TextView productPrice = findViewById(R.id.product_detail_price);
        TextView productDescription = findViewById(R.id.product_detail_description);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);


        //productImage.setImageResource(product.getImageUrl());
        String imageUrl = product.getImageUrl(); // Ensure this returns a valid URL string
        Glide.with(this)
                .load(imageUrl)
                .into(productImage);
        productName.setText(product.getName());
        double price = product.getPrice();
        String priceText = String.format("Price: %.2f kr", price);
        productPrice.setText(priceText);
        productDescription.setText(product.getDescription());

        cart = Cart.getInstance();

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the product to the cart
                cart.addProduct(product);

                // Show a message to the user
                Toast.makeText(ProductDetailActivity.this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }
}