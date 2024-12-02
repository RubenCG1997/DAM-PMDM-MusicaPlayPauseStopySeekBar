package com.iescamas.musicaplaypausestopyseekbar;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Runnable runnable;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mediaPlayer = MediaPlayer.create(this,R.raw.vals_ruso);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(0);
        seekBar.setMax(mediaPlayer.getDuration());


        findViewById(R.id.btnPlay).setOnClickListener(v->play());
        findViewById(R.id.btnPause).setOnClickListener(v->pause());
        findViewById(R.id.btnStop).setOnClickListener(v->stop());

        ((TextView)findViewById(R.id.lbl_tActual)).setText(convertirASegundosTiempoActual(mediaPlayer));
        ((TextView)findViewById(R.id.lbl_tTotal)).setText(convertirASegundosTiempoTotal(mediaPlayer));

        startTimer();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setProgress(i);
                mediaPlayer.seekTo(i);
                ((TextView)findViewById(R.id.lbl_tActual)).setText(convertirASegundosTiempoActual(mediaPlayer));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void startTimer(){
        runnable = ()->{
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            new Handler().postDelayed(runnable,1000);
        };
        runnable.run();
    }

    private void stop() {
       mediaPlayer.stop();
       mediaPlayer.reset();
       mediaPlayer = MediaPlayer.create(this,R.raw.vals_ruso);

    }

    private void pause() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void play() {
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    private String convertirASegundosTiempoTotal(MediaPlayer mediaPlayer){
        String minutaje;
        long milisegundos = mediaPlayer.getDuration();
        minutaje = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milisegundos),
                TimeUnit.MILLISECONDS.toSeconds(milisegundos) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milisegundos)));
        return minutaje;
    }
    private String convertirASegundosTiempoActual(MediaPlayer mediaPlayer){
        String minutaje;
        long milisegundos = mediaPlayer.getCurrentPosition();
        minutaje = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milisegundos),
                TimeUnit.MILLISECONDS.toSeconds(milisegundos) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milisegundos)));
        return minutaje;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.opcSalir){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.titleAD)
                    .setMessage(R.string.msgAD)
                    .setNegativeButton(R.string.negAD,null)
                    .setPositiveButton(R.string.posAD, (dialogInterface, i) -> finishAffinity())
                    .create()
                    .show();
        }
        return false;
    }
}