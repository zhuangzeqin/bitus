package com.bitus.web;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

public class APP extends Application {
    private static APP mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值  设置app字体不随系统字体大小而变化
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
    /**
     * 获取APP的实例
     *
     * @return
     */
    public static APP getApplicationInstance() {
        return mInstance;
    }
}
