package com.example.app2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {
    String name = "";
    String pwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        pwd = intent.getStringExtra("pwd");
        String name = intent.getStringExtra("name");
        int[] num = new int[]{1, 2, 3, 4, 5};
        String[] names = new String[]{name, name, name, name, name};
        List<Map<String, Object>> item = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("num", num[i]);
            map.put("name", names[i]);
            item.add(map);
        }
        SimpleAdapter ad = new SimpleAdapter(
                this,
                item,
                R.layout.item,
                new String[]{"num", "name"},
                new int[]{R.id.item_num, R.id.item_user});
        this.setListAdapter(ad);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        int index = position + 1;
        if (index == 1) {
            Intent intent = new Intent(MainActivity.this, ImageActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this, "您选择了第" + index + "项", Toast.LENGTH_SHORT).show();
        if (index == 2) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if (index == 3) {
            User user = new User();
            user.setName(name);
            user.setPwd(pwd);
            Intent intent = new Intent();
            int aInt = 123;
            byte aByte = 0xf;
            intent.putExtra("int", aInt)
                    .putExtra("byte", aByte)
                    .putExtra("ser", user);
            intent.setClass(MainActivity.this, WebListenerActivity.class);
            startActivity(intent);
        }
        if (index == 4) {
            Intent intent = new Intent();
            intent.putExtra("username", name);
            intent.setClass(MainActivity.this, StyleActivity.class);
            startActivity(intent);
        }
        if (index == 5) {
            Intent intent = new Intent(MainActivity.this, BillActivity.class);
            startActivity(intent);
        }

    }
}