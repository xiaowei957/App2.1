package com.example.app2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleActivity extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int[] list_image = {0, R.drawable.wifi, R.drawable.bluetooth, R.drawable.network, R.drawable.terminal
                , R.drawable.link, R.drawable.desktop, R.drawable.display, R.drawable.sound, R.drawable.notice
                , R.drawable.password, R.drawable.application, R.drawable.battery, R.drawable.storage
                , R.drawable.security, R.drawable.privacy, R.drawable.user, R.drawable.about};
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("username");
        System.out.println(name);
        String[] list_title = {"      " + name, "WLAN", "蓝牙", "移动网络", "超级终端", "更多链接", "桌面和壁纸", "显示和亮度", "声音和震动"
                , "通知", "生物识别和密码", "应用和服务", "电池", "存储", "安全", "隐私", "用户和账户", "关于手机"};
        List<Map<String, Object>> item = new ArrayList<>();
        for (int i = 0; i < list_title.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("image", list_image[i]);
            map.put("title", list_title[i]);
            item.add(map);
        }
        SimpleAdapter ad = new SimpleAdapter(this, item, R.layout.item1, new String[]{"image", "title"}, new int[]{R.id.item_image, R.id.item_title});
        this.setListAdapter(ad);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(StyleActivity.this, l.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }
}
