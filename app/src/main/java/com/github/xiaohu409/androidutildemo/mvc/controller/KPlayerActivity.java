package com.github.xiaohu409.androidutildemo.mvc.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.xiaohu409.androidutildemo.R;
import com.github.xiaohu409.androidutildemo.widget.VideoPlayerIJK;

public class KPlayerActivity extends AppCompatActivity {

    private VideoPlayerIJK mVideoPlayerIJK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kplayer);
        mVideoPlayerIJK = findViewById(R.id.mVideoPlayerIJK);
        String videoPath = "http://v-cdn.zjol.com.cn/280443.mp4";
        mVideoPlayerIJK.setVideoPath(videoPath);
        mVideoPlayerIJK.start();
    }
}