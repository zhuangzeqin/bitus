package com.bitus.web.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.bitus.web.R;
import com.bitus.web.view.CustomProgressDialog;


/**
 * 描述：APP 抽象的基类
 * 作者：zhuangzeqin
 * 时间: 2018/3/27-9:17
 * 邮箱：zzq@eeepay.cn
 */
public abstract class BaseActivity extends AppCompatActivity {

    /***等待加载的对话框**/
    private CustomProgressDialog mWaitDialog;
    /***上下文对象**/
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /** ------不可横屏幕-------- **/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        initWidget();
        eventOnClick();

    }
    /**
     * 显示等待对话框
     **/

    public void showLoading() {
//        showWaitDialog(getString(R.string.loading));
        showWaitDialog();
    }

    /**
     * 关闭显示等待对话框
     **/
    @UiThread
    public void hideLoading() {
        if (!isFinishing() && mWaitDialog != null) {
            try {
                mWaitDialog.dismiss();
                mWaitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 显示等待对话框
     **/
    @UiThread
    public ProgressDialog showWaitDialog() {
        if (!isFinishing()) {
            if (mWaitDialog == null) {
                mWaitDialog = new CustomProgressDialog(mContext, R.drawable.loadanmistyle);
//                mWaitDialog.setCanceledOnTouchOutside(false);
            }
            if (mWaitDialog != null) {
//                mWaitDialog.setMessage(message);
                mWaitDialog.show();
            }
            return mWaitDialog;
        }
        return null;
    }

    /**
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initWidget();

    /**
     * 事件绑定
     */
    protected abstract void eventOnClick();


}
