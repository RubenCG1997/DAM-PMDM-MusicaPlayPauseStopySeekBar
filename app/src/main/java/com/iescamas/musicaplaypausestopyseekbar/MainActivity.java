package com.iescamas.musicaplaypausestopyseekbar;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.vals_ruso);

        findViewById(R.id.btnPlay).setOnClickListener(v->play(mediaPlayer));
        findViewById(R.id.btnPause).setOnClickListener(v->pause(mediaPlayer));
        findViewById(R.id.btnStop).setOnClickListener(v->stop(mediaPlayer));

    }

    private void stop(MediaPlayer mediaPlayer) {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

    private void pause(MediaPlayer mediaPlayer) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void play(MediaPlayer mediaPlayer) {
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
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