package eu.androidtraining.dashboard.aussenwelt;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import eu.androidtraining.dashboard.R;

public class Internet extends Activity {

	private EditText mEditText;
	private WebView mWebView;
	
	private WebViewClient mViewClient = new WebViewClient() {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aussenwelt_internet);
		mEditText = 
			(EditText) findViewById(R.id.ed_aussenwelt_internet_adresse);
		mWebView = 
			(WebView) findViewById(R.id.wv_aussenwelt_internet);
		mWebView.setWebViewClient(mViewClient);
	}

	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_aussenwelt_internet_go:
			String adresse = mEditText.getText().toString();
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.loadUrl(adresse);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
