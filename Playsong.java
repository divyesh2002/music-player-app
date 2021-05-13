package com.example.djplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteController;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Playsong extends AppCompatActivity{
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updateseek.interrupt();
    }
    public String setdura(int seconds)
    {
        seconds=seconds/1000;
        int min=(seconds/60);
        int sec=(seconds%60);
        String s;
        if(sec>9)
        {
            s=String.format("%d:%d",min,sec);
        }
        else
        {
            s=String.format("%d:0%d",min,sec);
        }
        return s;
    }


    TextView textView;
    TextView duration;
    ImageView play;
    ImageView previous;
    ImageView next;
    ArrayList<File> songs;
    MediaPlayer mediaPlayer;
    String textcontent;
    int position;
    SeekBar seekBar;
    Thread updateseek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsong);
        textView=findViewById(R.id.textView2);
        play=findViewById(R.id.play);
        previous=findViewById(R.id.previous);
        next=findViewById(R.id.next);
        seekBar=findViewById(R.id.seekBar);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        songs=(ArrayList) bundle.getParcelableArrayList("songlist");
        textcontent=intent.getStringExtra("current");
        textView.setText(textcontent);
        textView.setSelected(true);
        duration=findViewById(R.id.textView3);
    //    position=Integer.valueOf(intent.getStringExtra("position"));
        position=intent.getIntExtra("position",0);
        Uri uri=Uri.parse(songs.get(position).toString());
        mediaPlayer=MediaPlayer.create(this,uri);
        duration.setText(setdura(mediaPlayer.getDuration()));
        mediaPlayer.start();
        complete();
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        updateseek=new Thread(){
            @Override
            public void run() {
                int currentpos=0;
                try {
                    while (currentpos<mediaPlayer.getDuration()) {
                        currentpos = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentpos);
                        sleep(800);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        updateseek.start();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    play.setImageResource(R.drawable.play1);
                    mediaPlayer.pause();
                }
                else
                {
                    play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    mediaPlayer.start();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
                if(position!=0){
                    position=position-1;
                }
                else
                {
                    position=songs.size() - 1;
                }
                textcontent=songs.get(position).getName().toString();
                textView.setText(textcontent);
                textView.setSelected(true);
                Uri uri=Uri.parse(songs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                duration.setText(setdura(mediaPlayer.getDuration()));
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
                seekBar.setActivated(true);
                mediaPlayer.start();
                complete();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
                if(position!=(songs.size()-1)){
                    position=position+1;
                }
                else
                {
                    position=0;
                }
                textcontent=songs.get(position).getName().toString();
                textView.setText(textcontent);
                textView.setSelected(true);
                Uri uri=Uri.parse(songs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                duration.setText(setdura(mediaPlayer.getDuration()));
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
                seekBar.setActivated(true);
                mediaPlayer.start();
                complete();
            }
        });
    }

    public void complete()
    {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=(songs.size()-1)){
                    position=position+1;
                }
                else
                {
                    position=0;
                }
                textcontent=songs.get(position).getName().toString();
                textView.setText(textcontent);
                textView.setSelected(true);
                Uri uri=Uri.parse(songs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                duration.setText(setdura(mediaPlayer.getDuration()));
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
                seekBar.setActivated(true);
                mediaPlayer.start();
                complete();
            }
        });
    }
}