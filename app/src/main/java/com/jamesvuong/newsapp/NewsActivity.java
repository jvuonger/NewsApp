package com.jamesvuong.newsapp;

import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    public static final String GUARDIAN_API_URL = "http://content.guardianapis.com/search";
    private ListView newsListView;
    private NewsAdapter newsAdapter;
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsListView = (ListView) findViewById(R.id.news_list_view);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);
        newsListView.setEmptyView(findViewById(R.id.splash_text_view));

        if(NetworkUtils.isNetworkAvailable(this)) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            TextView tvSplash = (TextView) findViewById(R.id.splash_text_view);
            tvSplash.setText(R.string.no_internet);

            ProgressBar pb = (ProgressBar) findViewById(R.id.loading_spinner);
            pb.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(GUARDIAN_API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Set News Topic
        uriBuilder.appendQueryParameter("q", "Technology");
        // Set api key
        uriBuilder.appendQueryParameter("api-key", "test");

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        newsAdapter.clear();

        if(newsList != null && !newsList.isEmpty()) {
            newsAdapter.addAll(newsList);
        } else {
            TextView tvSplash = (TextView) findViewById(R.id.splash_text_view);
            tvSplash.setText(R.string.no_results);
        }
        ProgressBar pb = (ProgressBar) findViewById(R.id.loading_spinner);
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }
}
