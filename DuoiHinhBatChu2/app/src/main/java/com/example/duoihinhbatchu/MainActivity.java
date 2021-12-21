package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.example.duoihinhbatchu.api.LayCauHoi;

public class MainActivity extends AppCompatActivity {
    Button turnon;
    Button turnoff;

    private boolean mIsBound =false;
    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.ServiceBinder binder = (MusicService.ServiceBinder)service;
            mServ =binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();

        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);

        onStart();

        setOnClick();
        new LayCauHoi().execute();
    }
    private void anhxa(){
         turnon =(Button) findViewById(R.id.bat_nhac);
         turnoff =(Button) findViewById(R.id.tat_nhac);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this,MusicService.class);
        this.bindService(intent,Scon, Context.BIND_AUTO_CREATE);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsBound){
            this.unbindService(Scon);
            mIsBound =false;
        }
    }

    public void choigame(View view) {
        if(DATA.getData().arrCauDo.size() > 0){
            startActivity(new Intent(this,ChoiGameMainActivity.class));
        }
    }
    public void setOnClick(){
        turnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServ.pauseMussic();
                turnoff.setVisibility(View.GONE);
                turnon.setVisibility(View.VISIBLE);
            }
        });
        turnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServ.resumeMusic();
                turnoff.setVisibility(View.VISIBLE);
                turnon.setVisibility(View.GONE);
            }
        });
    }

//    public void batnhac(View view) {
//        mServ.resumeMusic();
//        turnoff.setVisibility(View.GONE);
//        turnon.setVisibility(View.VISIBLE);
//    }
//
//    public void tatnhac(View view) {
//        mServ.pauseMussic();
//        turnoff.setVisibility(View.GONE);
//        turnon.setVisibility(View.VISIBLE);
//    }
}