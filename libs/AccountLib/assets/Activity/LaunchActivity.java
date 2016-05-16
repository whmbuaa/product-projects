package com.quick.accountlib.view.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;

import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.model.bean.User;
import com.quick.framework.util.log.QLog;

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

    if (getCurrentUser() != null) {
      QLog.i("current user is:"+getCurrentUser().toString());
      // Start an intent for the logged in activity
      MainActivity.launch(this);

    } else {
      // Start and intent for the logged out activity
      LoginActivity.launch(this);
    }
    finish();
  }

  protected User getCurrentUser(){
    return null;
  }

  public static void launch(Context context){
    Intent intent = new Intent(context, LaunchActivity.class);
    context.startActivity(intent);
  }
}
