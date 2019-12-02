package com.bitus.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

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
    private ImageView mImageView;
    private AnimationDrawable mAnimation;
    /**
     * 初始化webview
     */
    private void initWebview() {
//        mWebView.setBackgroundColor(ContextCompat.getColor(mContext,android.R.color.transparent));
//        mWebView.setBackgroundResource(R.mipmap.splash);
        WebSettings webSettings = mWebView.getSettings();
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        /** 提高渲染的优先级**/
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
////        // 清缓存和记录，缓存引起的白屏
//        mWebView.clearCache(true);
//        mWebView.clearHistory();
//        mWebView.requestFocus();
        // 应用可以有缓存 true false 没有缓存
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);// 缓存白屏
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath() + "/webcache";
        // 设置 Application Caches 缓存目录
        webSettings.setAppCachePath(appCachePath);
        webSettings.setDatabasePath(appCachePath);
        //自适应屏幕
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(false); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 加上这句话，支持html5的某些alert提示框的弹出
        webSettings.setDomStorageEnabled(true);
        mWebView.setDrawingCacheEnabled(true);
        //加载https的URL时在5.0以上加载不了，5.0以下可以加载，这种情况可能是网页中存在非https得资源，在5.0以上是默认关闭，需要设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上视频加载需要加这个，提示“视频加载失败，请刷新页面重试（错误码：0_4）”
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    // 为了防止在onCreate方法中只显示第一帧的解决方案之一
                    mImageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setVisibility(View.GONE);
                            mWebView.setVisibility(View.VISIBLE);
                            mAnimation.stop();
                        }
                    },0);
                }
                else
                {
//                    // 为了防止在onCreate方法中只显示第一帧的解决方案之一
//                    mImageView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mImageView.setVisibility(View.VISIBLE);
//                            mWebView.setVisibility(View.GONE);
//                            mAnimation.start();
//                        }
//                    },0);

                }
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {

                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {

                callback.invoke(origin, true, false);

                super.onGeolocationPermissionsShowPrompt(origin, callback);

            }

        });
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不另跳浏览器
                // 在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
//                    mWebView.stopLoading();
                    return true;
                }
                return false;
            }

            /**
             * 载入页面开始的事件
             **/
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                showLoading();
//                super.onPageStarted(view, url, favicon);
            }

            /**
             * 载入页面完成的事件
             **/
            @Override
            public void onPageFinished(WebView webView, String url) {
//                // 为了防止在onCreate方法中只显示第一帧的解决方案之一
//                mImageView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mImageView.setVisibility(View.GONE);
//                        mWebView.setVisibility(View.VISIBLE);
//                        mAnimation.stop();
//                    }
//                },0);
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        hideLoading();
//                    }
//                }, 1500);
////                super.onPageFinished(webView, url);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();// 接受证书
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        mImageView = (ImageView) findViewById(R.id.loadingIv);
        mWebView = (WebView) findViewById(R.id.wv_web);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        initWebview();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mImageView.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                mAnimation.start();
                mWebView.loadUrl(ConfigUrl.WEB_URL);
            }
        },0);

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
//            hideLoading();
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    @Override
    protected void onDestroy() {
//        hideLoading();
        mWebView.freeMemory();
        mWebView.clearSslPreferences();
        mWebView.clearView();
        mWebView.clearFormData();
        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.clearMatches();
        mWebView.removeAllViews();
        mWebView.destroy();
        super.onDestroy();
    }

    // 回退键重写
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

}
