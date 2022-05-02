package com.example.app2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class WebListenerActivity extends Activity {

    private NetworkChange networkChange;
    private final static String ACTION_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        setContentView(linearLayout);

        TextView textView1 = new TextView(this);
        TextView textView2 = new TextView(this);
        TextView textView3 = new TextView(this);

        Intent intent = getIntent();
        int aInt = intent.getIntExtra("int", 0);
        byte aByte = intent.getByteExtra("byte", (byte) 0);
        User user = intent.getParcelableExtra("ser");

        textView1.setText("int数据为：" + aInt);
        textView2.setText("byte数据为：" + aByte);
        textView3.setText("Serializable数据为：name:" + user.getName() + "pwd:" + user.getPwd());

        textView1.setTextSize(20);
        textView2.setTextSize(20);
        textView3.setTextSize(20);

        linearLayout.addView(textView1);
        linearLayout.addView(textView2);
        linearLayout.addView(textView3);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CHANGE);
        networkChange = new NetworkChange();
        registerReceiver(networkChange, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChange);
    }

    class NetworkChange extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();//获取网络状态
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                Toast.makeText(WebListenerActivity.this, "已连网！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(WebListenerActivity.this, "已断网！", Toast.LENGTH_LONG).show();
            }
        }
    }
}

