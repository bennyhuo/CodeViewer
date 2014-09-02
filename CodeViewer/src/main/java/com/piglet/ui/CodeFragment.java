package com.piglet.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.piglet.codeviewer.R;
import com.piglet.core.CodeGen;

public class CodeFragment extends Fragment {
	private WebView webView;
	private ProgressBar progressBar;
	private TextView title;
	private String titleText;
	private String data;

	public void initData(String title, String data) {
		this.data = data;
		this.titleText = title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.webmain, container, false);
		webView = (WebView) view.findViewById(R.id.webview);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		title = (TextView) view.findViewById(R.id.title);
		
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		// settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		settings.setBuiltInZoomControls(true);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(View.VISIBLE);
				System.out.println(url);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressBar.setVisibility(View.GONE);
			}
		});
		loadData();
		return view;
	}

	public void loadData() {
		title.setText(titleText);
		webView.loadDataWithBaseURL(CodeGen.BASE_URL, data, CodeGen.MIME_TYPE, CodeGen.DEFAULT_ENCODING, "");
	}
}
