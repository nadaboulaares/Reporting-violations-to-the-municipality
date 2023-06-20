
package com.example.bledi.network;

import com.example.bledi.model.Reclamation;
import com.example.bledi.model.Response;
import com.example.bledi.model.User;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface UserClient {



 @POST("/api/v1/reclamation")
 Call<User> createReclamation(@Body Reclamation reclamation);

 @GET("/api/v1/reclamation")
 Call<List<Reclamation>> reclamations();

}