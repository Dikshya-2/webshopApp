package dk.tec.webshopapp;

import dk.tec.webshopapp.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("/user/register")
    Call<Void> register(@Body User user);

    @POST("/user/login")
    Call<String> login(@Body User user);
}
