package com.example.bledi.network;

import com.example.bledi.model.Reclamation;
import com.example.bledi.model.Response;
import com.example.bledi.model.Story;
import com.example.bledi.model.User;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Completable;
import rx.Observable;

public interface RetrofitInterface {

    public static final String BASE_URL = "http://192.168.43.96:8080";

    @POST("users")
    Observable<Response> register(@Body User user);

    @POST("authenticate")
    Observable<Response> login();

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @POST("getStory")
    Observable<Story> getStory(@Query("email") String email);


    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);



    @GET("reclamation/{commentaires}")
    Observable<Reclamation> getAjout(@Path("commentaire") String commentaire);


    @Multipart
    @POST("/api/v1/reclamation")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody, @Part MultipartBody.Part commentaire, @Part MultipartBody.Part anomalie, @Part MultipartBody.Part latitude, @Part MultipartBody.Part longitude, @Part MultipartBody.Part date, @Part MultipartBody.Part etat, @Part MultipartBody.Part email);






}
