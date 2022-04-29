package com.example.app2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        String name = getIntent().getExtras().getString("name");
        Toast.makeText(SettingActivity.this, "欢迎来到"+name+"用户的设置", Toast.LENGTH_LONG).show();
    }

}


