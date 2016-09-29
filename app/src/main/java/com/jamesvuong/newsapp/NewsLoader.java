package com.jamesvuong.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by jvuonger on 9/29/16.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        Log.i(TAG, "NewsLoader: "+ url);
        mUrl = url;
    }

    @Override
    public List<News> loadInBackground() {
        if(mUrl == null) return null;
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<News> newsList = QueryUtils.fetchNewsData(mUrl);
        return newsList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
