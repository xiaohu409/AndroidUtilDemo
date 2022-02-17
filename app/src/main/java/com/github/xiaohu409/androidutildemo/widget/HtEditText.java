package com.github.xiaohu409.androidutildemo.widget;

import android.content.Context;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.xiaohu409.androidutil.LogUtil;
import com.github.xiaohu409.androidutildemo.R;


/**
 * 文件名称   ：HtEditText
 * 文件描述   ：带有删除按钮的文本框
 * 作者       ：胡涛
 * 日期       ：2019/4/17
 * 版本       ：1.0
 */
public class HtEditText extends LinearLayout implements TextWatcher, View.OnClickListener {

    private static final String TAG = "HtEditText";
    private EditText editText;
    private EditTextCallback editTextCallback;

    private boolean scan = false;

    public HtEditText(Context context) {
        this(context, null);
    }

    public HtEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HtEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        View.inflate(getContext(), R.layout.ht_edit_text, this);
        editText = findViewById(R.id.edit_et_id);
        editText.addTextChangedListener(this);
        ImageView delView = findViewById(R.id.del_iv_id);
        delView.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        LogUtil.logDebug(TAG + "  before", s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        LogUtil.logDebug(TAG  + " onText", " start:" + start + ", before:" + before + ", count:" + count);
        LogUtil.logDebug(TAG + " onText", s.toString());
        editText.removeTextChangedListener(this);
        CharSequence value = s.subSequence(start, start + count);
        editText.setText(value);
        if (!TextUtils.isEmpty(value) && editTextCallback != null) {
            editTextCallback.onText(value.toString());
        }
        editText.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {
//        if (editTextCallback != null) {
//            String value = s.toString();
//            if (!TextUtils.isEmpty(value)) {
//                editTextCallback.onText(value);
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.del_iv_id:
                //清空文本框
                editText.getText().clear();
                break;
        }
    }

    /**
     * 获取文本框
     * @return
     */
    public EditText getEditText() {
        return editText;
    }

    /**
     * 获取焦点
     */
    public void requesFocus() {
        editText.requestFocus();
    }

    /**
     * 清空内容
     */
    public void clear() {
        editText.getText().clear();
    }

    /**
     * 获取文本
     * @return
     */
    public String getText() {
        return editText.getText().toString().trim();
    }

    /**
     * 文本框回调
     */
    public interface EditTextCallback {
        void onText(String s);
    }

    /**
     * 设置回调
     * @param editTextCallback
     */
    public void setEditTextCallback(EditTextCallback editTextCallback) {
        this.editTextCallback = editTextCallback;
    }
}
