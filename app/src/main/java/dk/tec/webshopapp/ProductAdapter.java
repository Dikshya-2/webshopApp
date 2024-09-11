package dk.tec.webshopapp;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import dk.tec.webshopapp.model.Product;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }
        Product product = products.get(position);
        ImageView productImage = convertView.findViewById(R.id.product_image);
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productPrice = convertView.findViewById(R.id.product_price);
        TextView productdescription = convertView.findViewById(R.id.product_description);

        String imageUrl = product.getImageUrl();
        Glide.with(context)
                    .load(imageUrl)
                    .override(300,300)
                    .into(productImage);

        //productImage.setImageResource(product.getImageUrl());
        productName.setText(product.getName());
        productPrice.setText(String.format("Kr%.2f", product.getPrice()));
        productdescription.setText(product.getDescription());
        return convertView;
    }
}
