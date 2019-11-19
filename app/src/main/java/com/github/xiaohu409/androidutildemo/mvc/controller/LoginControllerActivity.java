package com.github.xiaohu409.androidutildemo.mvc.controller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.github.xiaohu409.androidutildemo.mvc.bean.LoginBean;
import com.github.xiaohu409.androidutildemo.mvc.model.LoginModel;
import com.github.xiaohu409.androidutildemo.mvc.model.LoginModelImp;
import com.github.xiaohu409.androidutildemo.mvc.view.LoginView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginControllerActivity extends BaseUIActivity {

    @BindView(R.id.username_et_id)
    EditText userEt;
    @BindView(R.id.pass_et_id)
    EditText passEt;
    @BindView(R.id.login_btn_id)
    Button loginBtn;
//    private EditText userEt;
//    private EditText passEt;
    private LoginModel<LoginBean> loginModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_controller;
    }

//    @Override
//    public void initUI() {
//        userEt = findViewById(R.id.username_et_id);
//        passEt = findViewById(R.id.pass_et_id);
//        Button loginBtn = findViewById(R.id.login_btn_id);
//        loginBtn.setOnClickListener(this);
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.login_btn_id:
//                //登录
//                login();
//                break;
//        }
//    }

    @OnClick(R.id.login_btn_id)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn_id:
                //登录
                login();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String username = userEt.getText().toString().trim();
        String pass = passEt.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showShort(userEt.getHint().toString());
            userEt.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            ToastUtil.showShort(passEt.getHint().toString());
            passEt.requestFocus();
            return;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("username", username);
        param.put("password", pass);
        loginModel.login(param, new LoginView<LoginBean>() {

            @Override
            public void onSuccess(LoginBean loginBean) {
                if (loginBean.getErrorCode() != 0) {
                    ToastUtil.showShort(loginBean.getErrorMsg());
                    return;
                }
                ToastUtil.showShort("登录成功");
            }

            @Override
            public void onFail(Throwable t) {
                ToastUtil.showShort(t.getMessage());
            }
        });
    }

    @Override
    public void bindData() {
        loginModel = new LoginModelImp();
    }

}
