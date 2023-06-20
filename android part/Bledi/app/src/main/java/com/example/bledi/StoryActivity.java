package com.example.bledi;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bledi.fragments.ChangePasswordDialog;
import com.example.bledi.model.Reclamation;
import com.example.bledi.model.Response;

import com.example.bledi.model.Story;
import com.example.bledi.model.User;
import com.example.bledi.network.NetworkClient;
import com.example.bledi.network.NetworkUtil;
import com.example.bledi.network.RetrofitInterface;
import com.example.bledi.utils.Constants;
import com.example.bledi.utils.SimpleDividerItemDecoration;
import com.example.bledi.utils.SmoothScrollLinearLayoutManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class StoryActivity extends AppCompatActivity  {

    public static final String TAG = StoryActivity.class.getSimpleName();
    private TextView commentaire;
    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvDate;
    private Button mBtChangePassword;
    private Button mBtLogout;

    private ProgressBar mProgressbar;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private CompositeSubscription mSubscriptions;

    private RecyclerView mRecyclerView;
    private SmoothScrollLinearLayoutManager mLinearLayoutManager;
    private ReclamationsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        mSubscriptions = new CompositeSubscription();
        initViews();
        initSharedPreferences();

        loadProfile();
    }



    private void initViews() {
        commentaire = (TextView) findViewById(R.id.commentaire);

        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvEmail = (TextView) findViewById(R.id.tv_email);
        mTvDate = (TextView) findViewById(R.id.tv_date);

        mRecyclerView=   (RecyclerView) findViewById(R.id.recycler_view);
        mProgressbar = (ProgressBar) findViewById(R.id.progress);


    }

    private void loadStoryList(ArrayList<Reclamation> aReclamationList){

        mAdapter = new ReclamationsListAdapter(this, aReclamationList);


        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new SmoothScrollLinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
    }




    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(  User user) {

        mProgressbar.setVisibility(View.GONE);



        mTvEmail.setText(user.getEmail());
        mTvName.setText(user.getName());

        getStory(user.getEmail());

    }

    private void handleStoryResponse(  Story theStory) {

        ArrayList<Reclamation> theReclamationsList = theStory.getStory();

        loadStoryList(theReclamationsList);


    }


    private void getStory(String anEmail){

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getStory(anEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleStoryResponse, this::handleError));

    }



    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Erreur reseau !");
        }
    }

    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.activity_story),message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


}


