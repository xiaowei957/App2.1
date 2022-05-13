package com.example.app2;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Timer;
import java.util.TimerTask;

public class MusicActivity extends Activity{
    private EditText editText = null;
    private Button download,pause = null;
    private ProgressBar schedule = null;
    private ProgressBar schedule1 = null;
    private TextView times = null;
    private TextView result = null;
    boolean state = true;
    private Handler handler;
    private static MediaPlayer mediaPlayer = null;
    private int iTimes = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);
        editText = findViewById(R.id.url);
        download = findViewById(R.id.download);
        pause = findViewById(R.id.pause);
        schedule = findViewById(R.id.schedule);
        schedule1 = findViewById(R.id.schedule1);
        times = findViewById(R.id.times);
        result = findViewById(R.id.resultm);
        String url = editText.getText().toString();
        editText.setText("https://ssl.gstatic.com/dictionary/static/sounds/oxford/no--_gb_1.mp3");

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.length() != 0){
                    if (state){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                download();
                            }
                        }).start();
                        state = false;
                    }else {
                        Toast.makeText(MusicActivity.this, "正在下载", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MusicActivity.this, "请输入下载地址", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 0x101){
                    schedule.setProgress(msg.arg1);
                    result.setText("已下载" + String.valueOf(msg.arg1) + "%");
                    if (msg.arg1 == 100){
                        result.setText("下载完成！");
                    }
                }
                if (msg.what == 0x102){
                    schedule1.setProgress(msg.arg1);
                }
                super.handleMessage(msg);
            }
        };

    }

    private void download() {
        try {
            URL url = new URL(editText.getText().toString());
            URLConnection urlConnection = url.openConnection();
            int length = urlConnection.getContentLength();
            ReadableByteChannel c = Channels.newChannel(url.openStream());
            File file = new File("data/data/com.example.app2/test.mp3");
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileChannel channel = fileOutputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int len;
            int sum = 0;
            while ((len = c.read(byteBuffer)) != -1){
                sum += len;
                int progress = (sum*100)/length;
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()){
                    channel.write(byteBuffer);
                }
                byteBuffer.clear();
                Message m = handler.obtainMessage();
                m.what = 0x101;
                m.arg1 = progress;
                handler.sendMessage(m);
            }
            display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void display() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this,Uri.parse("data/data/com.example.app2/test.mp3"));
        mediaPlayer.start();
        schedule1.setMax(mediaPlayer.getDuration());
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {
                schedule1.setProgress(mediaPlayer.getCurrentPosition());
            }
        };
        timer.schedule(timerTask,0,10);

        iTimes++;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                times.setText(""+iTimes);
                display();
            }
        });
    }

}
