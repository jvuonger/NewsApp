package com.jamesvuong.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by jvuonger on 9/29/16.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private List<News> mNewsList;

    private static class ViewHolder {
        TextView title;
        TextView publishedDate;
    }

    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);

        ViewHolder viewHolder;

        if( convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.news_list_item, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.publishedDate = (TextView) convertView.findViewById(R.id.published_date);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(news.getWebTitle());
        viewHolder.publishedDate.setText(news.getPublicationDate());

        return convertView;
    }
}
