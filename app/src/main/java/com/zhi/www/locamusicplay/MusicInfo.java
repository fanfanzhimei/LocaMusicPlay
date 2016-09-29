package com.zhi.www.locamusicplay;

/**
 * Created by Administrator on 2016/9/29.
 */
public class MusicInfo {
    private String musicName;
    private String author;
    private String musicPath;

    public MusicInfo(){

    }

    public MusicInfo(String musicName, String author, String musicPath){
        this.musicName = musicName;
        this.author = author;
        this.musicPath = musicPath;
    }

    public String getMusicName() {
        return musicName;
    }

    public String getAuthor() {
        return author;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }
}
