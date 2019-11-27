package com.bitus.web.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        doJump();
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

    /**
     * 设置状态栏问题颜色（黑/白）
     *
     * @param isLight true:白色 false:黑色
     */
    protected void setStatusBarTextLight(boolean isLight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | (isLight ? View.SYSTEM_UI_FLAG_LAYOUT_STABLE : View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
        }
    }
}
