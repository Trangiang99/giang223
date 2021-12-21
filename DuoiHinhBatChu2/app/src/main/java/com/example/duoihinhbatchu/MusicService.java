package com.example.duoihinhbatchu;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaSync;
import android.os.IBinder;
import android.os.Binder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MusicService extends Service implements MediaPlayer.OnErrorListener {

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private  int lenght =0;

    public MusicService() {
    }
    public class ServiceBinder extends Binder {
        MusicService getService(){
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this,R.raw.music);
        mPlayer.setOnErrorListener(this);
        if (mPlayer != null){
            mPlayer.setLooping(true);
            mPlayer.setVolume(100,100);

        }
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                onError(mPlayer,what,extra);
                return true;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mPlayer != null){
            mPlayer.start();
        }

        return START_STICKY;
    }

    public void pauseMussic(){
        if (mPlayer.isPlaying()){
            mPlayer.pause();
            lenght=mPlayer.getCurrentPosition();
        }
    }
    public void resumeMusic(){
        if (mPlayer.isPlaying() ==false){
            mPlayer.seekTo(lenght);
            mPlayer.start();
        }
    }
    public void stopMusic(){
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null){
            try {
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null){
            try {
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
        return false;
    }


}
