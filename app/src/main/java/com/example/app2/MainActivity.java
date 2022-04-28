package com.example.app2;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        拿到数据
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

//        数据存放
        int[] num = new int[]{1,2,3,4,5};
        String[] names = new String[]{name,name,name,name,name};
        List<Map<String,Object>> item = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("num",num[i]);
            map.put("name",names[i]);
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

    //        点击事件
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        int index = position+1;
        if (index == 1){
            Intent intent = new Intent(MainActivity.this, ImageActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this,"您选择了第"+ index +"项",Toast.LENGTH_SHORT).show();


    }
}