package com.example.yunihafsari.fypversion3.social_media_api.authentication_insta;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.yunihafsari.fypversion3.utils.Constants;

/**
 * Created by yunihafsari on 08/06/2017.
 */

public class AuthenticationDialog extends Dialog {

    private static final String TAG = AuthenticationDialog.class.getSimpleName();
    private final AuthenticationListener mListener;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    private WebView webView;

    static final float[] DIMENSIONS_LANDSCAPE = {460, 260};
    static final float[] DIMENSIONS_PORTRAIT = {280, 420};

    private String url = "https://api.instagram.com/oauth/authorize/?client_id=" + Constants.CLIENT_ID + "&redirect_uri=" + Constants.REDIRECT_URL + "&response_type=code&display=touch&scope=follower_list+public_content";

    public AuthenticationDialog(@NonNull Context context, AuthenticationListener listener) {
        super(context);
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setMessage("Loading...");

        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setUpWebView();

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        float[] dimensions = (display.getWidth() < display.getHeight()) ? DIMENSIONS_PORTRAIT
                : DIMENSIONS_LANDSCAPE;

        addContentView(linearLayout, new FrameLayout.LayoutParams(
                (int) (dimensions[0] * scale + 0.5f), (int) (dimensions[1]
                * scale + 0.5f)));

    }

    private void setUpWebView() {
        webView = new WebView(getContext());
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new OAuthWebClient());
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(webView);
    }

    private class OAuthWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.d(TAG, "Redirecting URL "+url);

            if(url.startsWith(Constants.REDIRECT_URL)){
                String urls[] = url.split("=");
                Log.d(TAG, "split[0] "+urls[0]);
                Log.d(TAG, "split[1] "+urls[1]);
                mListener.onCodeReceived(urls[1]);
                return true;
            }

            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

            Log.d(TAG, "Page Error: "+error);

            super.onReceivedError(view, request, error);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            Log.e(TAG, "Loading URL: "+url);

            super.onPageStarted(view, url, favicon);
            progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            String title = webView.getTitle();
            Log.d(TAG, "onPageFinished URL: "+url);
            progressDialog.dismiss();
        }
    }
}
