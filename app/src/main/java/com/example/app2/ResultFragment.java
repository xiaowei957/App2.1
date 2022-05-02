package com.example.app2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class ResultFragment extends android.app.Fragment {
    String[] strings = new String[]{"用户设置详细内容", "亮度设置详细内容", "音量设置详细内容",
            "网络设置详细内容", "通用设置详细内容"};

    public static ResultFragment newInstance(int aIndex) {
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("aIndex", aIndex);
        resultFragment.setArguments(bundle);
        return resultFragment;
    }

    public int getShownIndex() {
        return getArguments().getInt("aIndex", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        ScrollView scrollView = new ScrollView(getActivity());
        TextView textView = new TextView(getActivity());
        textView.setPadding(10, 10, 10, 10);
        scrollView.addView(textView);
        textView.setText(strings[getShownIndex()]);
        return scrollView;
    }
}

