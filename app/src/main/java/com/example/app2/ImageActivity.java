package com.example.app2;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ImageActivity extends Activity {
    int[] image = new int[]{R.drawable.image01,R.drawable.image02,R.drawable.image03,R.drawable.image04,R.drawable.image05};
    ArrayList<Map<String, Object>> list = new ArrayList<>();
    private ProgressBar progressBar;
    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.image);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
        progressBar = findViewById(R.id.bartest);
        gridView = findViewById(R.id.gridview);
        new MyTack().execute();
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);
                builder.setTitle("是否删除？");
                builder.setMessage("是否真的要删除吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        simpleAdapter.notifyDataSetChanged();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }
    class MyTack extends AsyncTask<Void,Integer,SimpleAdapter>{
        @Override
        protected SimpleAdapter doInBackground(Void... params) {
            for (int i = 0; i < image.length; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("imageview",image[i]);
                try {
                    list.add(map);
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            simpleAdapter = new SimpleAdapter(ImageActivity.this,
                    list,
                    R.layout.items,
                    new String[]{"titleview", "imageview"},
                    new int[]{R.id.titleview, R.id.imageview});
            return simpleAdapter;
        }
        //        publishProgress
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]*20);
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(SimpleAdapter result) {
            progressBar.setVisibility(View.GONE);
//            setProgressBarVisibility(false);
            gridView.setAdapter(result);
//            linearLayout.addView(result);
            super.onPostExecute(result);
        }
    }
}

