package com.example.bledi;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bledi.fragments.LoginFragment;

public class ScreenActivity extends AppCompatActivity {

  public static final String TAG = ScreenActivity.class.getSimpleName();

  private LoginFragment mLoginFragment;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen);



      loadFragment();

  }

  private void loadFragment() {

    if (mLoginFragment == null) {

      mLoginFragment = new LoginFragment();
    }
    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, mLoginFragment, LoginFragment.TAG).commit();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    String data = intent.getData().getLastPathSegment();
    Log.d(TAG, "onNewIntent: " + data);


  }
}
