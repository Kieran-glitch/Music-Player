package com.example.musicplayer;

import java.io.Serializable;

public class AudioModel implements Serializable {
    String path,duration,title;

    public AudioModel (String path, String duration, String title) {
        this.path = path;
        this.duration = duration;
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public String getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
