package com.beecloud.beecloud.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.UserModel;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.presenter.LoginPresenter;
import com.beecloud.beecloud.view.ILoginView;
import com.quick.uilib.dialog.ProgressDialogUtil;
import com.quick.uilib.toast.ToastUtil;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public class LoginActivity extends FragmentActivity implements ILoginView {

    // presenter
    private LoginPresenter mLoginPresenter;

    // ui
    private EditText  mEtUserName;
    private EditText  mEtPassword;
    private Button    mBtnLogin;

    // for network request
    private List<Subscription> mSubscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(UserModel.getInstance(this).getLoggedInUser() == null){
            setContentView(R.layout.activity_login);
            init();
        }
        else{
            MainActivity.launch(this);
            finish();
        }

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

    private void init(){

        mLoginPresenter = new LoginPresenter(this,this);
        mSubscriptionList = new LinkedList<Subscription>();

        mEtUserName = (EditText)findViewById(R.id.et_user_name);
        mEtPassword = (EditText)findViewById(R.id.et_password);
        mBtnLogin = (Button)findViewById(R.id.bt_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialogUtil.show(LoginActivity.this,"",false);
               mSubscriptionList.add(mLoginPresenter.login());
            }
        });
    }
    @Override
    public String getUserName() {
        return mEtUserName.getEditableText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getEditableText().toString();
    }

    @Override
    public void loginSuccess(User user) {
        ProgressDialogUtil.dismiss();
        finish();
        MainActivity.launch(this);
    }

    @Override
    public void loginFail(Throwable error) {
        ProgressDialogUtil.dismiss();
        ToastUtil.showToast(this,error.toString());
    }
}