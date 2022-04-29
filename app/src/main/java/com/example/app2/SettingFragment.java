package com.example.app2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class SettingFragment extends android.app.ListFragment {

    int index = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] strings = new String[]{"用户设置","亮度设置","音量设置","网络设置","通用设置"};
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_checked,strings));
        if (savedInstanceState != null){
            index = savedInstanceState.getInt("curChoice",0);
        }
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        showDetails(index);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice",index);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }
    void showDetails(int aIndex){
        getListView().setItemChecked(aIndex,true);
        ResultFragment results = (ResultFragment) getFragmentManager().findFragmentById(R.id.result);
        if (results == null || results.getShownIndex() != aIndex){
            results = ResultFragment.newInstance(aIndex);
            FragmentTransaction f = getFragmentManager().beginTransaction();
            f.replace(R.id.result,results);
            f.setTransition(f.TRANSIT_FRAGMENT_FADE);
            f.commit();
        }
    }
}
