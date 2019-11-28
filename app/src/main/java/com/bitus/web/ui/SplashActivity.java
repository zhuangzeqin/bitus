package com.bitus.web.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.bitus.web.MainActivity;
import com.bitus.web.R;


/**
 * 描述：APP 启动页
 * 作者：zhuangzeqin
 * 时间: 2018/3/27-9:17
 * 邮箱：zzq@eeepay.cn
 */
public class SplashActivity extends BaseActivity {
    private Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void initWidget() {
        doJump();
    }

    @Override
    protected void eventOnClick() {

    }

    private void doJump() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shipPage();
            }
        }, 2000);
    }


    private void shipPage() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
