package com.example.app2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

public class MusicSerActivity extends Activity{
    private Button select,play,stop;
    private ProgressBar schedule;
    private TextView result;
    private String path;
    private MusicService musicService;
    private MusicService.MyBinder binder;

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyBinder)iBinder).getService();
            binder = (MusicService.MyBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicser);
        play = findViewById(R.id.music_play);
        stop = findViewById(R.id.music_stop);
        select = (Button) this.findViewById(R.id.music_select);
        schedule = findViewById(R.id.music_schedule);
        result = (TextView) this.findViewById(R.id.music_result);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.play();
                schedule.setMax(MusicService.mp.getDuration());
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask(){
                    @Override
                    public void run() {
                        schedule.setProgress(MusicService.mp.getCurrentPosition());
                    }
                };
                timer.schedule(timerTask,0,10);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    musicService.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(sc);
        super.onDestroy();
    }

    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivityForResult
                    ( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private static final String TAG = "ChooseFile";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    path = UriUtils.getPath(this, uri);
                    Log.d(TAG, "File Path: " + path);
                    result.setText("歌曲路径为"+path);
                    setPath(path);
                    Intent intent = new Intent(MusicSerActivity.this, MusicService.class);
                    intent.putExtra("path",path);
                    bindService(intent, sc, MusicSerActivity.this.BIND_AUTO_CREATE);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}