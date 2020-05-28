package com.github.xiaohu409.androidutildemo.mvp.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.xiaohu409.androidutil.ToastUtil;
import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.base.BaseUIActivity;
import com.github.xiaohu409.androidutildemo.mvp.bean.LoginBean;
import com.github.xiaohu409.androidutildemo.mvp.presenter.APresenter;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseUIActivity {

    private EditText userEt;
    private EditText passEt;
    private APresenter aPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void initUI() {
        userEt = findViewById(R.id.username_et_id);
        passEt = findViewById(R.id.pass_et_id);
        Button loginBtn = findViewById(R.id.login_btn_id);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void bindData() {
        aPresenter = new APresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_id:
                //
                login();
                break;
        }
    }

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
        param.put("pass", pass);
        aPresenter.login(param, new LoginView<LoginBean>() {
            @Override
            public void onSuccess(LoginBean loginBean) {

            }

            @Override
            public void onFail(Throwable throwable) {

            }

            @Override
            public void showLoad() {

            }

            @Override
            public void hideLoad() {

            }
        });
    }
}
