package dk.tec.webshopapp;

import java.util.List;

import dk.tec.webshopapp.model.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductApi {
    @GET("product")
    Call<List<Product>> getAllProducts();

    @GET("product/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @POST("product")
    Call<Void> createProduct(@Body Product product);

    @PUT("product")
    Call<Void> updateProduct(@Body Product product);

    @DELETE("product/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

}
