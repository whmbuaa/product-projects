package com.beecloud.beecloud.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.presenter.SignupPresenter;
import com.beecloud.beecloud.view.ISignupView;
import com.quick.uilib.toast.ToastUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

/**
 * Activity which displays a login screen to the user.
 */
public class SignupActivity extends FragmentActivity implements ISignupView {
  // UI references.
  private EditText mEtUserName;
  private EditText mEtPassword;
  private EditText mEtPasswordAgain;
  

  private EditText mEtMobileNumber;
  private EditText mEtSms;

  //butons
    private Button mBtSendSms;
    private Button mBtVerifySms;
    private Button mBtSignup;

  private SignupPresenter mPresenter;

    // for network request
    private List<Subscription> mSubscriptionList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_signup);

      mPresenter = new SignupPresenter(this,this);
      mSubscriptionList = new LinkedList<Subscription>();
    // Set up the signup form.
      mEtMobileNumber = (EditText) findViewById(R.id.et_mobile_number);
      mEtSms = (EditText) findViewById(R.id.et_sms);

      mEtUserName = (EditText) findViewById(R.id.username_edit_text);
      mEtPassword = (EditText) findViewById(R.id.password_edit_text);
      mEtPasswordAgain = (EditText) findViewById(R.id.password_again_edit_text);
    

      mBtSendSms = (Button)findViewById(R.id.bt_send_sms);
      mBtVerifySms = (Button)findViewById(R.id.bt_verify_sms);
      mBtSignup = (Button)findViewById(R.id.bt_signup);


      // set button listener
      mBtSendSms.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
              mSubscriptionList.add(mPresenter.requestSmsCode(mEtMobileNumber.getText().toString()));
          }
      });

      mBtVerifySms.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
              mSubscriptionList.add( mPresenter.verifySmsCode(mEtMobileNumber.getText().toString(),mEtSms.getText().toString()));
          }
      });

      mBtSignup.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {

              HashMap<String, String> args = new HashMap<String, String>();
//              args.put(User.MOBILE_PHONE_NUMBER,mEtMobileNumber.getText().toString());
              mSubscriptionList.add(mPresenter.signup(mEtUserName.getText().toString(),mEtPassword.getText().toString(),args));
          }
      });
    

  }
  
  private void switchToSignup() {
		// TODO Auto-generated method stub
		findViewById(R.id.sms_verification_contaier).setVisibility(View.GONE);
		findViewById(R.id.signup_container).setVisibility(View.VISIBLE);
	}
    private void switchToSmsVerification() {
        // TODO Auto-generated method stub
        findViewById(R.id.sms_verification_contaier).setVisibility(View.VISIBLE);
        findViewById(R.id.signup_container).setVisibility(View.GONE);
    }


    @Override
    public void requestSmsCodeSuccess() {

    }

    @Override
    public void requestSmsCodeFail(Throwable error) {
        ToastUtil.showToast(this,error.toString());
    }

    @Override
    public void verifySmsCodeSuccess() {
        switchToSignup();
    }

    @Override
    public void verifySmsCodeFail(Throwable error) {
        ToastUtil.showToast(this,error.toString());
        if(error instanceof AVException){
            if(((AVException) error).getCode() == AVException.USER_MOBILE_PHONENUMBER_TAKEN ){
                switchToSmsVerification();
            }
        }
    }

    @Override
    public void signupSuccess(User user) {

        Intent intent = new Intent(SignupActivity.this, LaunchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void signupFail(Throwable error) {
        ToastUtil.showToast(this,error.toString());
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context,SignupActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSubscriptionList != null){
            for(Subscription subscription : mSubscriptionList){
                subscription.unsubscribe();
            }
            mSubscriptionList.clear();
        }
    }
}
