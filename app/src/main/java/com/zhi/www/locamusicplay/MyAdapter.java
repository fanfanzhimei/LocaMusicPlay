package com.zhi.www.locamusicplay;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<MusicInfo> data;
    private MediaPlayer mp;

    public MyAdapter(Context context, List<MusicInfo> data, MediaPlayer mp){
        this.context = context;
        this.data = data;
        this.mp = mp;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            vh = new ViewHolder();
            vh.mLlItem = (LinearLayout) convertView.findViewById(R.id.ll_item);
            vh.mTvMusicName = (TextView) convertView.findViewById(R.id.tv_music_name);
            vh.mTvAuthor = (TextView) convertView.findViewById(R.id.tv_author);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mTvMusicName.setText(data.get(position).getMusicName());
        vh.mTvAuthor.setText(data.get(position).getAuthor());
        vh.mLlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusic(data.get(position).getMusicPath());
                ((MainActivity)context).setCursorPositon(position);
            }
        });

        return convertView;
    }

    static class ViewHolder{
        LinearLayout mLlItem;
        TextView mTvMusicName;
        TextView mTvAuthor;
    }

    public void startMusic(String uri){
        mp.reset();
        try {
            mp.setDataSource(uri);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
