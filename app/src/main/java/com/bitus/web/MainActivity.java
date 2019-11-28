package com.bitus.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bitus.web.ui.BaseActivity;
import com.bitus.web.utils.ConfigUrl;

/**
 * 描述：1、 编写安卓下包装H5的app外壳，打包签名；（https://web.bitus.com/）
 * 2、 当出现安卓兼容性问题，需要修改bug；
 * 3、 提供源码。
 * 作者：zzq
 * 时间：2019/11/27 20:13
 * 邮箱：1546374673@qq.com
 */
public class MainActivity extends BaseActivity {
    private WebView mWebView;//webview 加载

    /**
     * 初始化webview
     */
    private void initWebview() {
        mWebView = (WebView) findViewById(R.id.wv_web);
        //支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(false);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        mWebView.getSettings().setUseWideViewPort(false); //将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 加上这句话，支持html5的某些alert提示框的弹出
        mWebView.getSettings().setDomStorageEnabled(true);
        //加载https的URL时在5.0以上加载不了，5.0以下可以加载，这种情况可能是网页中存在非https得资源，在5.0以上是默认关闭，需要设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上视频加载需要加这个，提示“视频加载失败，请刷新页面重试（错误码：0_4）”
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        mWebView.loadUrl(ConfigUrl.WEB_URL);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        initWebview();
    }

    @Override
    protected void eventOnClick() {

    }

    /**
     * @author Administrator
     * @date 2014-7-2
     * @function Android Webview实现文件下载功能
     */
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            hideLoading();
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mWebView.clearCache(true);
        mWebView.clearFormData();
        mWebView.clearHistory();
        mWebView.clearSslPreferences();
        mWebView = null;
        super.onDestroy();
    }

}
