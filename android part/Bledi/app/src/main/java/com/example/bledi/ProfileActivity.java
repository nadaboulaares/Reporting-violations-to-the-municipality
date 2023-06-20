package com.example.bledi;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.graphics.Color;
import android.net.Uri;

import android.os.Build;

import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.bledi.network.NetworkClient;
import com.example.bledi.network.RetrofitInterface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.bledi.fragments.ChangePasswordDialog;
import com.example.bledi.model.Response;
import com.example.bledi.model.User;
import com.example.bledi.network.NetworkUtil;
import com.example.bledi.utils.Constants;

import java.io.ByteArrayOutputStream;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.view.Menu;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;





public class ProfileActivity extends AppCompatActivity implements ChangePasswordDialog.Listener, LocationListener {





    private static final int PERMS_CALL_ID = 1234;

    private LocationManager lm;
    private GoogleMap googleMap;

    private MapFragment mapFragment;

    ImageView imageView;
    Button button;
    Button button2;
    private EditText commentaire;

    Integer REQUEST_CAMERA = 1;
    private static final int MY_PERMISSION_REQUEST_CODE =1;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    public static final int PERMISSIONS_REQUEST_CODE_CAMERA = 51;
    public static final int PERMISSIONS_REQUEST_WRITE_STORAGE = 52;
    public static final int PERMISSIONS_REQUEST_READ_STORAGE = 53;
    public static final int PERMISSIONS_REQUEST_LOCATION = 54;




    public static final String URL = "http://192.168.43.144:8080";


    public static final String TAG = ProfileActivity.class.getSimpleName();

    private TextView mTvName;
    private TextView mEtEmail;


    private Button mBtChangePassword;
    private Button mBtLogout;

    private ProgressBar mProgressbar;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;
    private String mImagePath;

    private CompositeSubscription mSubscriptions;
    private Object error;
    private String mSelectedAnomalie;

    private TextView positionLongitude;
    private TextView positionLatitude ;

    private static final int INTENT_REQUEST_CODE = 100;



    Bitmap bitmap;
    private static final int REQUEST_CODE_READ_EXTERNAL_PERMISSION = 2;
    String image_name;
    String imagePath;

private TextView date;
private String etat="en attente";



    private Uri mImageUri;






    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private void checkCameraPermissions() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CODE_CAMERA);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        }else{
            checkWriteStoragePermissions();
        }
    }

    private void checkWriteStoragePermissions() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_WRITE_STORAGE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        }else{
            checkReadStoragePermissions();
        }
    }

    private void checkReadStoragePermissions() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_READ_STORAGE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        }else{
            checkLocationPermissions();
        }
    }

    private void checkLocationPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        }else{

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mSubscriptions = new CompositeSubscription();


        initViews();
        initSharedPreferences();
        loadProfile();
        checkCameraPermissions();



        button = (Button) findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String theCommentaire = commentaire.getText().toString();
                  String thePositionLatitude = positionLatitude.getText().toString();
                String thePositionLongitude = positionLongitude.getText().toString();

                     String theDate = date.getText().toString();


                if(mImagePath!=null && mImageUri !=null && imageView !=null && mSelectedAnomalie!=null && !mSelectedAnomalie.isEmpty() && !theCommentaire.isEmpty()&& !theDate.isEmpty() && thePositionLatitude!="TextView" && thePositionLongitude!="TextView" )
                    addReclamation(mImagePath,mImageUri, theCommentaire,mSelectedAnomalie ,thePositionLatitude ,thePositionLongitude  ,theDate, etat, mEmail );
                else{
                  Toast errorToast = Toast.makeText(ProfileActivity.this,"un ou plusieurs champs manquants !", Toast.LENGTH_LONG);
                 View view = errorToast.getView();
                 view.setBackgroundColor(Color.RED);

                  errorToast.show();
                }
            }
        });


        commentaire = (EditText) findViewById(R.id.commentaire);
        Spinner spin = (Spinner) findViewById(R.id.spinner);

        imageView = (ImageView) findViewById(R.id.imageView);
        button2 = (Button) findViewById(R.id.button2);

        final String[] anomalie = {"autre" ,"Depot Sauvage", "Eclairage Public", "Tags", "Trous sur Voirie", "voiture sur Territoire" };
        int images[] = { R.drawable.gpng ,R.drawable.a, R.drawable.b, R.drawable.cc, R.drawable.d, R.drawable.e};
        button2 = (Button) findViewById(R.id.button2);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                startActivityForResult(intent,0);
            }
        });













































        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ProfileActivity.this, "You Select Position: " + position + " " + anomalie[position], Toast.LENGTH_SHORT).show();
            mSelectedAnomalie =  anomalie[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), images, anomalie);


        positionLatitude = (TextView) findViewById(R.id . positionLatitude);
        positionLongitude = (TextView) findViewById(R.id . positionLongitude);

        date = (TextView) findViewById(R.id . date);
        spin.setAdapter(customAdapter);
        FragmentManager FragmentManager = getFragmentManager();
        mapFragment = (MapFragment) FragmentManager.findFragmentById(R.id.map);





    }











    private void initViews(View v) {

        commentaire = (EditText) v.findViewById(R.id.commentaire);

        button = (Button) v.findViewById(R.id.button);

        mProgressbar = (ProgressBar) v.findViewById(R.id.progress);


    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem historique = menu.findItem(R.id.historique);
        historique.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ProfileActivity.this, StoryActivity.class);
                startActivity(intent);
                return false;
            }
        });


        return true;
    }



    @Override

    public void onResume() {
        super.onResume();
        checkPermissions();


    }

    private void checkPermissions() {

        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION


                }, PERMS_CALL_ID);

                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        }
        if (lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 10000, 0, this);
        }
        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        }
        loadMap();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMS_CALL_ID:
                checkPermissions();
                return;
            case PERMISSIONS_REQUEST_CODE_CAMERA: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    checkCameraPermissions();

                }
                return;
            }
            case PERMISSIONS_REQUEST_WRITE_STORAGE:{

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                }

                return;

            }
            case PERMISSIONS_REQUEST_READ_STORAGE:{

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {


                }

                return;

            }


        }


    }

    @Override
    public void onPause() {
        super.onPause();
        if (lm != null) {
            lm.removeUpdates(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void loadMap() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {

                                        ProfileActivity.this.googleMap = googleMap;

                                        googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));
                                        googleMap.setMyLocationEnabled(true);
                                        googleMap.addMarker(new MarkerOptions().position(new LatLng(31.799345, 9.7254267))


                                                .title("Infini Software"));

                                    }
                                }

        );
    }


    @Override
    public void onLocationChanged(final Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        positionLatitude.setText(String.valueOf(latitude));
        positionLongitude.setText(String.valueOf(longitude));

        if (googleMap != null) {
            LatLng googleLocation = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));

        }
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {

    }

    @Override
    public void onProviderEnabled(final String provider) {
        if ("gps".equals(provider)) {

        }

    }

    @Override
    public void onProviderDisabled(final String provider) {
        if ("gps".equals(provider)) {

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
        String dates= sdf.format(new Date());
        date.setText(dates);

if(resultCode !=RESULT_CANCELED) {

  Bitmap photo = (Bitmap) data.getExtras().get("data");


  imageView.setImageBitmap(photo);


  Uri tempUri = getImageUri(getApplicationContext(), photo);



  mImagePath = getRealPathFromURI(tempUri);
  mImageUri = tempUri;

}
    }



    private void uploadToServer(byte[] imageBytes,String filePath, String aCommentaire, String anAnomalie, String aPositionLatitude , String aPositionLongitude   , String aDate ,String anEtat ,String anEmail) {

        Log.e("Upload_img","Path:"+filePath);
        Log.e("Upload_img","aCommentaire:"+aCommentaire);
        Log.e("Upload_img","anAnomalie:"+anAnomalie);
        Log.e("Upload_img","aPositionLatitude:"+aPositionLatitude);
        Log.e("Upload_img","aDate:"+aDate);
        Log.e("Upload_img","aetat:"+anEtat);
        Log.e("Upload_img","amEmail:"+anEmail);

        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        RetrofitInterface uploadAPIs = retrofit.create(RetrofitInterface.class);

        File file = new File(filePath);


        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);


        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        MultipartBody.Part theCommentaire = MultipartBody.Part.createFormData("commentaire", aCommentaire);
        MultipartBody.Part theAnomalie = MultipartBody.Part.createFormData("anomalie", anAnomalie);
        MultipartBody.Part theLatitude = MultipartBody.Part.createFormData("positionLatitude", aPositionLatitude);
        MultipartBody.Part theLongitude = MultipartBody.Part.createFormData("positionLongitude", aPositionLongitude);
        MultipartBody.Part theDate = MultipartBody.Part.createFormData("date", aDate);
        MultipartBody.Part theEtat = MultipartBody.Part.createFormData("etat", anEtat);
        MultipartBody.Part theEmail = MultipartBody.Part.createFormData("email", anEmail);


        Call call = uploadAPIs.uploadImage(body, description,theCommentaire,theAnomalie,theLatitude,theLongitude,theDate,theEtat,theEmail);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                Toast.makeText(getApplicationContext(), "Reclamation envoyee avec succés", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("Upload_img","onFailure:"+t.getMessage());
                Toast.makeText(getApplicationContext(), "une erreur se produite !", Toast.LENGTH_SHORT).show();
            }
        });
    }






    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    private void initViews() {

        mTvName = (TextView) findViewById(R.id.tv_name);
        mEtEmail = (TextView)findViewById(R.id.tv_email);
        mBtChangePassword = (Button) findViewById(R.id.btn_change_password);
        mBtLogout = (Button) findViewById(R.id.btn_logout);
        mProgressbar = (ProgressBar) findViewById(R.id.progress);

        mBtChangePassword.setOnClickListener(view -> showDialog());
        mBtLogout.setOnClickListener(view -> logout());
    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(Constants.TOKEN, "");
        mEmail = mSharedPreferences.getString(Constants.EMAIL, "");
    }

    private void logout() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.EMAIL, "");
        editor.putString(Constants.TOKEN, "");
        editor.apply();
        finish();
    }

    private void showDialog() {

        ChangePasswordDialog fragment = new ChangePasswordDialog();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN, mToken);
        fragment.setArguments(bundle);

        fragment.show(getFragmentManager(), ChangePasswordDialog.TAG);
    }

    private void loadProfile() {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void addReclamation(String anImagePath,Uri anImageUri, String aCommentaire, String anAnomalie, String aPositionLatitude , String aPositionLongitude   , String aDate ,String aetat ,String amEmail  ) {

        try {

            InputStream is = getContentResolver().openInputStream(anImageUri);


            uploadToServer(getBytes(is),anImagePath,aCommentaire,anAnomalie,aPositionLatitude,aPositionLongitude,aDate,aetat,amEmail);
commentaire.setText("");

          date.setText("");
imageView.setImageResource(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void handleResponse(User user) {

        mProgressbar.setVisibility(View.GONE);
        mTvName.setText(user.getName());


        mEtEmail.setText(user.getEmail());

    }

    private void handleReclamationResponse(Response aResponse) {

        Toast.makeText(this, "Response:" + aResponse.getMessage(), Toast.LENGTH_LONG).show();

    }

    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody, Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("erreur reseau!");
        }
    }

    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.activity_profile), message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }



    @Override
    public void onPasswordChanged() {

        showSnackBarMessage("le mot de passe a été changé avec succés !");
    }
}



