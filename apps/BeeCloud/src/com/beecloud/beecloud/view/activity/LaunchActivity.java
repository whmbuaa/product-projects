package com.beecloud.beecloud.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.avos.avoscloud.AVUser;

/**
 * Activity which starts an intent for either the logged in (MainActivity) or logged out
 * (SignUpOrLoginActivity) activity.
 */
public class LaunchActivity extends Activity {

  public LaunchActivity() {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Check if there is current user info
    AVUser user = AVUser.getCurrentUser();
    if (AVUser.getCurrentUser() != null) {
      // Start an intent for the logged in activity
      MainActivity.launch(this);

    } else {
      // Start and intent for the logged out activity
      LoginActivity.launch(this);
    }
    finish();
  }

}
