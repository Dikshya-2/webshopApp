package dk.tec.webshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dk.tec.webshopapp.model.Product;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private TextView totalPriceTextView;
    private Button checkoutButton;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        cart = Cart.getInstance();

        // FindviewsbyID
        cartListView = findViewById(R.id.cart_list);
        totalPriceTextView = findViewById(R.id.total_price);
        checkoutButton = findViewById(R.id.checkout_button);

        // Set up the cart, ListView and adapter
        List<Product> cartProducts = cart.getProducts();
        ProductAdapter adapter = new ProductAdapter(this, cartProducts);  // Use appropriate adapter
        cartListView.setAdapter(adapter);

        updateTotalPrice();
        // Checkout Button
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this,  "Your order has been successfully processed!", Toast.LENGTH_LONG).show();
                // Clear the cart after successful checkout
                cart.clearCart();

                // Optionally, return a result to the previous activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("checkout_message", "Your order has been successfully processed!");
                setResult(RESULT_OK, resultIntent);

                // Finish the current activity
                finish();
            }
        });
    }
    private void updateTotalPrice () {
        double totalPrice = cart.getTotalPrice();
        totalPriceTextView.setText(String.format("Total: Kr%.2f", totalPrice));
    }
}