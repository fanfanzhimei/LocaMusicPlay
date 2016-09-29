package com.zhi.www.locamusicplay;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final int QUERY_SUCCESS = 0;

    private List<MusicInfo> data = new ArrayList<MusicInfo>();
    private MediaPlayer mp = new MediaPlayer();
    private Cursor cursor;
    private MyAdapter mAdapter;

    private ContentResolver resolver;
    private Uri uri;
    private String[] projection;
    private String selection;

    private int cursorPosition = 0;

    private ListView mLvMusic;
    private Button mBtnPre;
    private Button mBtnNext;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case QUERY_SUCCESS:
                    mAdapter = new MyAdapter(MainActivity.this, data, mp);
                    mLvMusic.setAdapter(mAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDatas();
    }

    private void initViews() {
        mLvMusic = (ListView) findViewById(R.id.lv_music);
        mBtnPre = (Button) findViewById(R.id.btn_pre);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnPre.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
    }

    private void initDatas() {
        resolver = MainActivity.this.getContentResolver();
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  // 音频地址
        projection = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.AudioColumns.DATA
        };
        selection = MediaStore.Audio.AudioColumns.DURATION + ">=30000";

        new Thread(new Runnable() {
            @Override
            public void run() {
                data.clear();
                cursor = resolver.query(uri, projection, selection, null, null);
                while (cursor.moveToNext()) {
                    data.add(new MusicInfo(
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA))
                    ));
                }
                mHandler.sendEmptyMessage(QUERY_SUCCESS);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pre:
                startPre();
                break;
            case R.id.btn_next:
                startNext();
                break;
        }
    }

    private void startPre() {
        if (cursor.isFirst()) {
            ToastUtils.showText("亲，已经是第一首了...");
        } else {
            cursorPosition--;
            if (cursor.moveToPosition(cursorPosition)) {
                mAdapter.startMusic(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
            }
        }
    }

    private void startNext() {
        if (cursor.isLast()) {
            ToastUtils.showText("亲，已经是最后一首了...");
        } else {
            cursorPosition++;
            if (cursor.moveToPosition(cursorPosition)) {
                mAdapter.startMusic(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
            }
        }
    }

    public void setCursorPositon(int positon) {
        cursorPosition = positon;
        cursor.moveToPosition(cursorPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        mp.stop();
        mp.release();
    }
}